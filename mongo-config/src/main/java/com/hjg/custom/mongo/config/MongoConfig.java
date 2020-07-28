package com.hjg.custom.mongo.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties(MongoMultiSettingProperties.class)
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class MongoConfig {

    private static final Logger logger = LoggerFactory.getLogger(MongoConfig.class);

    @Bean
    @Primary
    MongoTemplate primaryMongoTemplate(MongoCustomConversions customConversions, MongoDbFactory primaryMongoDbFactory) {
        return this.createMongoTemplate(customConversions, primaryMongoDbFactory);
    }

    @Bean
    @Primary
    MongoDbFactory primaryMongoDbFactory(MongoMultiSettingProperties mongoMultiSettingProperties) {
        return this.createMongoDbFactory(mongoMultiSettingProperties.getPrimary());
    }

    @ConditionalOnProperty(prefix = "spring.data.mongodb.secondary", name = {"hosts[0]", "ports[0]", "database"})
    @Bean
    MongoTemplate secondaryMongoTemplate(MongoCustomConversions customConversions, @Qualifier("secondaryMongoDbFactory") MongoDbFactory secondaryMongoDbFactory) {
        return this.createMongoTemplate(customConversions, secondaryMongoDbFactory);
    }

    @ConditionalOnProperty(prefix = "spring.data.mongodb.secondary", name = {"hosts[0]", "ports[0]", "database"})
    @Bean
    MongoDbFactory secondaryMongoDbFactory(MongoMultiSettingProperties mongoMultiSettingProperties) {
        return this.createMongoDbFactory(mongoMultiSettingProperties.getSecondary());
    }

    private MongoTemplate createMongoTemplate(MongoCustomConversions customConversions, MongoDbFactory mongoDbFactory){
        //保存时不再保存_class字段
        MongoMappingContext mongoMappingContext = new MongoMappingContext();
        MappingMongoConverter mappingMongoConverter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), mongoMappingContext);
        mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));

        mappingMongoConverter.setCustomConversions(customConversions);

        mappingMongoConverter.afterPropertiesSet();

        return new MongoTemplate(mongoDbFactory, mappingMongoConverter);
    }

    //TODO 使用ImportBeanDefinitionRegistrar来完成包的扫描
    @Bean
    public MongoCustomConversions customConversions(MongoMultiSettingProperties mongoMultiSettingProperties){

        String[] convertPackages = mongoMultiSettingProperties.getConvertPackages();

        List converterList = new ArrayList();

        if(convertPackages == null || convertPackages.length == 0) {
            return new MongoCustomConversions(converterList);
        }

        ClassLoader cls = Thread.currentThread().getContextClassLoader();

        for(String convertPackage : convertPackages) {
            try {
                ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                String packageLocation = convertPackage.replace(".", "/");
                Resource[] resources = resolver.getResources(packageLocation + "/**/*.class");

                for(Resource resource : resources) {
                    //支持搜索子包里面的类
                    String path = resource.getURL().getPath();
                    path = path.replace("/", ".");

                    int startIndex = path.indexOf(convertPackage);
                    //6就是.class文件后缀的长度
                    int lastIndex = path.length() - 6;
                    String fullPathClassName = path.substring(startIndex, lastIndex);

                    Class classObj = cls.loadClass(fullPathClassName);
                    converterList.add(classObj.newInstance());
                }
            } catch (Exception e) {
                logger.error("customConversions(),导入包下的类时异常,INPUT:convertPackage={}", convertPackage, e);
            }
        }

        return new MongoCustomConversions(converterList);
    }

    private MongoDbFactory createMongoDbFactory(MongoSettingProperties mongoSettingProperties) {

        List<ServerAddress> serverAddresses = new ArrayList<>();
        String[] hosts = mongoSettingProperties.getHosts();
        int[] ports = mongoSettingProperties.getPorts();
        for(int i=0; i<hosts.length; i++) {
            serverAddresses.add(new ServerAddress(hosts[i], ports[i]));
        }

        MongoClientOptions mongoClientOptions = new MongoClientOptions.Builder()
                .connectionsPerHost(mongoSettingProperties.getConnectionsPerHost())
                .minConnectionsPerHost(mongoSettingProperties.getMinConnectionsPerHost())
                .build();

        MongoClient mongoClient = null;

        // 连接认证
        String username = mongoSettingProperties.getUsername();
        String password = mongoSettingProperties.getPassword();
        String authenticationDatabase = mongoSettingProperties.getAuthenticationDatabase();
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(authenticationDatabase)) {
            mongoClient = new MongoClient(serverAddresses, mongoClientOptions);
        } else {
            MongoCredential mongoCredential = MongoCredential.createCredential(
                    username,
                    authenticationDatabase,
                    password.toCharArray());
            mongoClient = new MongoClient(serverAddresses, mongoCredential, mongoClientOptions);
        }

        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, mongoSettingProperties.getDatabase());

        return mongoDbFactory;
    }
}
