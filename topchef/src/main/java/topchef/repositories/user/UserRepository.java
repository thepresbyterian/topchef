package topchef.repositories.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import topchef.domain.User;

public interface UserRepository extends MongoRepository<User, String>, UpdateableUserRepository {
	User findByUserName(String username);
}