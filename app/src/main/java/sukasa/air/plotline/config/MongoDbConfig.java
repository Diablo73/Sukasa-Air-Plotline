package sukasa.air.plotline.config;

import com.mongodb.client.MongoClients;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoDbConfig {

	@Value("${mongodb.uri}")
	private String mongoDbUri;

	@Value("${mongodb.database}")
	private String mongoDbDatabase;

	@Getter
	private static String mongoDbAdmin;

	@Value("${mongodb.admin}")
	private void setMongoDbAdmin(String mongoDbAdmin) {
		MongoDbConfig.mongoDbAdmin = mongoDbAdmin;
	}


	@Bean
	public MongoDatabaseFactory mongoDbFactory() {
		return new SimpleMongoClientDatabaseFactory(MongoClients.create(mongoDbUri), mongoDbDatabase);
	}

	@Bean
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoDbFactory());
	}
}
