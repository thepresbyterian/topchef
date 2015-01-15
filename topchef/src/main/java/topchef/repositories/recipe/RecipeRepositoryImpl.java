package topchef.repositories.recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import topchef.domain.Recipe;

@Repository
public class RecipeRepositoryImpl implements UpdateableRecipeRepository {
	@Autowired
	@Qualifier("defaultMongoTemplate")
	private MongoOperations mongo;
		
	private Update getUpdate(Recipe x, Recipe y) {
		Update update = new Update();
		update.set("ingredients", y.getIngredients());
		update.set("steps", y.getSteps());
		update.set("userId", y.getUserId());		
		return update;
	}
	
	@Override
	public void update(Recipe recipe) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(recipe.getId()));
		Recipe old = mongo.findOne(query,  Recipe.class);		
		mongo.updateFirst(query, getUpdate(old, recipe), Recipe.class);
	}
}