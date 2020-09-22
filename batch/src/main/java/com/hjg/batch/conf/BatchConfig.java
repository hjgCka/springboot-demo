package com.hjg.batch.conf;

import com.hjg.batch.dao.PersonDao;
import com.hjg.batch.model.Person;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/9/22
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    @Profile("test")
    public BatchConfigurer batchConfigurer() {
        return new DefaultBatchConfigurer() {

            @Override
            public PlatformTransactionManager getTransactionManager() {
                return new ResourcelessTransactionManager();
            }

            @SneakyThrows
            @Override
            public JobRepository getJobRepository() {
                MapJobRepositoryFactoryBean jobFactory = new MapJobRepositoryFactoryBean();
                jobFactory.setTransactionManager(this.getTransactionManager());
                return jobFactory.getObject();
            }

            @Override
            public JobLauncher getJobLauncher() {
                SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
                jobLauncher.setJobRepository(this.getJobRepository());
                //如果使用scheduler框架，则不用设置；如果在http调用，则需要设置
                jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
                return jobLauncher;
            }
        };
    }

    @Bean
    public Job myJob(@Qualifier("step1") Step step1, @Qualifier("step2") Step step2) {
        return jobBuilderFactory.get("myJob")
                .preventRestart()
                .start(step1).next(step2)
                .build();
    }

    @Bean
    Step step1(ItemReader<Person> personReader, ItemProcessor<Person, Person> personProcessor, ItemWriter<Person> personWriter) {
        //ItemStream，用于step执行的生命周期的回调。
        //如果ItemReader/ItemProcessor/ItemWriter也实现了ItemStream，那么ItemStream就自动注册了
        //除此之外，ItemStream都需要单独注册
        return stepBuilderFactory.get("step1")
                //事务提交前，被处理的条目数量。默认为1，没处理一条就会进行提交。
                .<Person, Person>chunk(10)
                .reader(personReader)
                .processor(personProcessor)
                .writer(personWriter)
                .build();
    }

    @Bean
    @StepScope
    FlatFileItemReader<Person> personReader(@Value("#{jobParameters[fileName]}") String fileName) {
        FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource(fileName));
        reader.setLineMapper((line, lineNumber) -> {
            String[] arrays = line.split(",");
            Person person = new Person();
            person.setName(arrays[0]);
            person.setAge(Integer.valueOf(arrays[1]));
            person.setAddress(arrays[2]);
            return person;
        });
        return reader;
    }
    @Bean
    ItemProcessor<Person, Person> personProcessor() {
        return item -> {
            item.setAge(item.getAge() + 1);
            return item;
        };
    }
    @Bean
    ItemWriter<Person> personWriter() {
        return items -> {
            System.out.println("item = " + items);
        };
    }

    @Bean
    Step step2(Tasklet myTasklet) {
        //面向Chunk的处理，不是step中唯一的方式。
        return stepBuilderFactory.get("step2")
                .tasklet(myTasklet)
                //允许启动次数为1，无论是否成功，再次启动会抛出异常
                .startLimit(1)
                .build();
    }

    /**
     * 不直接实现Tasklet接口，而是适配到已存在的类。
     *
     * 为了使用后绑定而加上@StepScope，因为这个bean不能在Step开始前初始化。
     * @param personDao
     * @return
     */
    @StepScope
    @Bean
    public MethodInvokingTaskletAdapter myTasklet(PersonDao personDao, @Value("#{jobParameters[personName]}") String name) {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(personDao);
        adapter.setTargetMethod("findPerson");
        adapter.setArguments(new Object[]{name});

        return adapter;
    }
}
