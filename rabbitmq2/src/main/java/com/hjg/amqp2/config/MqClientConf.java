package com.hjg.amqp2.config;

import com.hjg.amqp2.model.Phone;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/12/21
 */
@Configuration
@EnableRabbit
public class MqClientConf {
    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private RabbitProperties rabbitProperties;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitProperties.getHost(), rabbitProperties.getPort());
        connectionFactory.setUsername(rabbitProperties.getUsername());
        connectionFactory.setPassword(rabbitProperties.getPassword());
        connectionFactory.setVirtualHost(rabbitProperties.getVirtualHost());

        //默认缓存模式为channel
        //设置channel的缓存大小，防止频繁创建和关闭channel。默认值为25。
        int channelSize = rabbitProperties.getCache().getChannel().getSize();
        connectionFactory.setChannelCacheSize(channelSize >= 25 ? channelSize : 25);

        //这个线程池用于从内部队列将消息发送到MessageListenerContainer。如果使用DMLC同时还会用来接收消息。
        //如果不设置线程池，就使用内部线程池，大小默认为5。
        /*ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(20, 50,
                3000, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(50),
                new ThreadFactory() {
                    private AtomicInteger counter = new AtomicInteger(-1);

                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setName("conn-factory-thread-" + counter.incrementAndGet());
                        return thread;
                    }
                },
                new ThreadPoolExecutor.AbortPolicy());
        connectionFactory.setExecutor(poolExecutor);*/

        connectionFactory.setConnectionThreadFactory(new ThreadFactory() {
            private AtomicInteger counter = new AtomicInteger(-1);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("mq-demo-" + counter.incrementAndGet());
                return thread;
            }
        });

        //设置connection的名称，值不用唯一
        connectionFactory.setConnectionNameStrategy(connFactory -> applicationName + "-" + "conn");
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        //生产者和消费者使用不同的连接，防止生产者被阻塞之后，消费者也跟着被阻塞
        //设置后内部会使用2个CachingConnectionFactory，
        // 为publisher创建的Connection会与消费者使用相同的命名策略，但是后面会加上.publisher
        rabbitTemplate.setUsePublisherConnection(true);

        //默认的retryPolicy是SimpleRetryPolicy，且最大重试次数为3。
        RetryTemplate retryTemplate = new RetryTemplate();

        //使用指数的回退策略。第一次重试等1000*2的0次方，第二次等1000*2，第三次为1000*2的2次方
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000);
        backOffPolicy.setMultiplier(2);
        //最大等待时间为10秒，到达10秒后的重试会一直保持10秒??
        backOffPolicy.setMaxInterval(10000);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        //默认重试策略重试3次，回退策略为指数回退，每次重试等1秒。
        //这个重试用于处理与boker的连接性问题
        rabbitTemplate.setRetryTemplate(retryTemplate);

        //发送消息时会用到这里设置的消息转换器
        rabbitTemplate.setMessageConverter(jsonMessageConverter());

        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter jsonConverter  = new Jackson2JsonMessageConverter();

        //如果设置了ClassMapper，反序列化时会默认信任添加的类信息。
        jsonConverter.setClassMapper(classMapper());

        //接收方接收消息时，只使用消息的_TypeId_头信息来指导转换，而不会进行任何类型推断
        jsonConverter .setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);

        return jsonConverter ;
    }

    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        //发送消息时，如果对象是Phone的实例，则_TypeId_的值为Phone类的名称;
        //消费消息时，会从_TypeId_中提取出值来匹配对应类信息
        idClassMapping.put(Phone.class.getName(), Phone.class);
        idClassMapping.put(Book.class.getName(), Book.class);
        classMapper.setIdClassMapping(idClassMapping);

        //设置为*标识信任所有的反序列化包
        classMapper.setTrustedPackages("com.hjg.amqp2.model");
        return classMapper;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("queue_consumer");
        //核心池大小必须大于等于，所有的MessageListenerContainer的ConcurrencyConsumer属性总和。
        executor.setCorePoolSize(100);
        executor.setMaxPoolSize(200);
        executor.setQueueCapacity(60);
        return executor;
    }

    /**
     * 查看RabbitListenerAnnotationBeanPostProcessor类源码，默认bean名称是这个，否则需要@RabbitListener指定容器工厂的beanName
     * @param connectionFactory
     * @return
     */
    @Bean(name = "rabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory containerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(connectionFactory);

        //用于调用MessageListener的线程池，如果不配置使用SimpleAsyncTaskExecutor
        containerFactory.setTaskExecutor(taskExecutor());

        //设置并发，默认20个消费者(线程)，最大50个消费者(线程)
        //会被@RabbitListener设置的值覆盖
        //这也最好为每个队列设置单独的属性值
        containerFactory.setConcurrentConsumers(20);
        containerFactory.setMaxConcurrentConsumers(50);


        //consumerTag相当于消费者名称，这个值应设置的不一样
        containerFactory.setConsumerTagStrategy(queue -> {
            String random = UUID.randomUUID().toString();
            return queue + "-" + random;
        });

        //容器工厂类可以设置RetryTemplate 和RecoveryCallback ，前者在发送reply时使用。
        //后者在重试用尽之后使用。

        //在spring-messaging Message与Rabbit客户端库要求的格式之间进行转换
        containerFactory.setMessageConverter(jsonMessageConverter());

        //消费消息时使用自动ack，这也是默认值。
        //监听器容器在处理完成之后执行ack
        //containerFactory.setAcknowledgeMode(rabbitProperties.getListener().getSimple().getAcknowledgeMode());

        return containerFactory;
    }
}
