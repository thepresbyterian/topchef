package topchef.repositories.unit;

import org.springframework.data.mongodb.repository.MongoRepository;

import topchef.domain.Unit;

public interface UnitRepository extends MongoRepository<Unit, String>, UpdateableUnitRepository {
	Unit findByName(String key);
}
