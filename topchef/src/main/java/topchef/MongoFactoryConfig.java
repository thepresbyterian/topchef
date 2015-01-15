package topchef;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;

@Configuration
@EnableMongoRepositories
public class MongoFactoryConfig extends AbstractMongoConfiguration {
    @Value("topchef") 
    private String dbName;

    @Autowired
    private Mongo mongo;

    @Bean
    protected MongoDbFactory defaultMongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(mongo, dbName);
    }

    @Bean
    protected MongoTemplate defaultMongoTemplate() throws Exception {
        return new MongoTemplate(defaultMongoDbFactory());
    }

    @Override
    protected String getDatabaseName() {
        return dbName;
    }

    @Override
    public Mongo mongo() throws Exception {
        return mongo;
    }

    @Override
    protected String getMappingBasePackage() {
        return "repositories";
    }

    @Override
    public MongoTemplate mongoTemplate() throws Exception {
        return defaultMongoTemplate();
    }    
}