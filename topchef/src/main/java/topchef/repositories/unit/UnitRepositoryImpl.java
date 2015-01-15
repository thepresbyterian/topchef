package topchef.repositories.unit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import topchef.domain.Unit;

public class UnitRepositoryImpl implements UpdateableUnitRepository {
	@Autowired
	@Qualifier("defaultMongoTemplate")
	private MongoOperations mongo;
		
	private Update getUpdate(Unit x, Unit y) {
		Update update = new Update();
		update.set("name", y.getName());		
		return update;
	}
	
	@Override
	public void update(Unit unit) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(unit.getId()));
		Unit old = mongo.findOne(query,  Unit.class);		
		mongo.updateFirst(query, getUpdate(old, unit), Unit.class);
	}
	
	public List<Unit> findAll() {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.ASC, "name"));
		return mongo.find(query,  Unit.class);
	}
}
