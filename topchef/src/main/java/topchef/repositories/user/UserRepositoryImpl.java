package topchef.repositories.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import topchef.domain.User;

public class UserRepositoryImpl implements UpdateableUserRepository {
	@Autowired
	@Qualifier("defaultMongoTemplate")
	private MongoOperations mongo;
		
	private Update getUpdate(User x, User y) {
		Update update = new Update();
		update.set("firstName", y.getFirstName());
		update.set("lastName", y.getLastName());
		update.set("email", y.getEmail());
		update.set("status", y.getStatus());
		update.set("isEnabled", y.getIsEnabled());		
		update.set("roles", y.getRoles());
		return update;
	}
	
	@Override
	public void update(User user) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(user.getId()));
		User old = mongo.findOne(query,  User.class);		
		mongo.updateFirst(query, getUpdate(old, user), User.class);
	}
}
