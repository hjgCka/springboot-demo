package com.hjg.spring.redis.config;

import com.hjg.spring.redis.config.model.ClusterRedisMultiProperties;
import com.hjg.spring.redis.config.model.ClusterRedisProperties;
import com.hjg.spring.redis.config.model.SingleRedisMultiProperties;
import com.hjg.spring.redis.config.model.SingleRedisProperties;
import com.hjg.spring.redis.config.util.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.Set;

/**
 * 实现ImportBeanDefinitionRegistrar时，无法使用Environment。
 * 可能运行时机过早导致。
 */
public class RedisConfigPostProcessor implements BeanFactoryPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfigPostProcessor.class);

    private Environment environment;

    public RedisConfigPostProcessor(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        boolean prodProfile = environment.acceptsProfiles("prod");

        if(prodProfile) {
            Binder binder = Binder.get(environment);
            ClusterRedisMultiProperties clusterRedisMultiProperties = binder.bind("spring.redis",
                    Bindable.of(ClusterRedisMultiProperties.class)).get();
            initProdSecondaries(beanFactory, clusterRedisMultiProperties.getSecondaryMap());
        } else {
            Binder binder = Binder.get(environment);
            SingleRedisMultiProperties singleRedisMultiProperties = binder.bind("spring.redis",
                    Bindable.of(SingleRedisMultiProperties.class)).get();
            this.initNonProdSecondaries(beanFactory, singleRedisMultiProperties.getSecondaryMap());
        }
    }

    /**
     * 初始化非prod环境下的，其余redis数据源。
     * @param beanFactory
     * @param secondaryMap
     */
    private void initNonProdSecondaries(ConfigurableListableBeanFactory beanFactory, Map<String, SingleRedisProperties> secondaryMap) {
        if(secondaryMap == null || secondaryMap.size() == 0) {
            logger.info("initNonProdSecondaries(),secondaryMap为null，不需要初始化");
            return;
        }

        DefaultListableBeanFactory df = (DefaultListableBeanFactory) beanFactory;

        Set<Map.Entry<String, SingleRedisProperties>> entrySet = secondaryMap.entrySet();
        for(Map.Entry<String, SingleRedisProperties> entry : entrySet) {
            SingleRedisProperties singleRedisProperties = entry.getValue();

            LettuceConnectionFactory lettuceConnectionFactory = RedisUtils.createSingleConnectionFactory(singleRedisProperties);
            RedisTemplate redisTemplate = RedisUtils.createRedisTemplate(lettuceConnectionFactory);

            df.registerSingleton(entry.getKey(), redisTemplate);
        }
    }

    /**
     * 初始化prod环境下的，其余redis数据源。
     * @param beanFactory
     * @param secondaryMap
     */
    private void initProdSecondaries(ConfigurableListableBeanFactory beanFactory, Map<String, ClusterRedisProperties> secondaryMap) {
        if(secondaryMap == null || secondaryMap.size() == 0) {
            logger.info("initProdSecondaries(),secondaryMap为null，不需要初始化");
            return;
        }

        DefaultListableBeanFactory df = (DefaultListableBeanFactory) beanFactory;

        Set<Map.Entry<String, ClusterRedisProperties>> entrySet = secondaryMap.entrySet();
        for(Map.Entry<String, ClusterRedisProperties> entry : entrySet) {
            ClusterRedisProperties clusterRedisProperties = entry.getValue();

            LettuceConnectionFactory lettuceConnectionFactory = RedisUtils.createClusterConnectionFactory(clusterRedisProperties);
            RedisTemplate redisTemplate = RedisUtils.createRedisTemplate(lettuceConnectionFactory);

            df.registerSingleton(entry.getKey(), redisTemplate);
        }
    }

}
