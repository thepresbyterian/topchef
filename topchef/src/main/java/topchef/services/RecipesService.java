package topchef.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import topchef.domain.Ingredient;
import topchef.domain.Recipe;
import topchef.domain.RecipeStep;
import topchef.domain.Unit;
import topchef.exceptions.DuplicateRecipeException;
import topchef.repositories.recipe.RecipeRepository;
import topchef.repositories.unit.UnitRepository;

@Service
public class RecipesService {
	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private UnitRepository unitRepository;

	public Recipe createRecipe(String userId, String title) throws DuplicateRecipeException {
		title = StringUtils.capitalize(title);
		Recipe test = recipeRepository.findByUserIdAndTitle(userId, title);
		if (test != null) {
			throw new DuplicateRecipeException("Recipe with title '" + title + "' already exists.");
		}

		Recipe r = new Recipe.Builder()
			.userId(userId)
			.title(title)
			.ingredients(new ArrayList<Ingredient>())
			.steps(new ArrayList<RecipeStep>())
			.build();

		this.recipeRepository.save(r);
		return this.recipeRepository.findOne(r.getId());
	}

	public Recipe getRecipe(String userid, String recipeid) {
		return this.recipeRepository.findOne(recipeid);
	}

	public List<Recipe> deleteRecipe(String userid, String recipeid) {
		recipeRepository.delete(recipeid);
		return getRecipes(userid);
	}

	public List<Recipe> getRecipes(String userid) {
		List<Recipe> results = this.recipeRepository.findByUserId(userid);
		for (Recipe r : results) {
			r.setIngredients(null);
			r.setSteps(null);
		}
		return results;
	}

	public Recipe addIngredient(String username, String recipeId, String description, Float quantity, String unitId) {
		Unit dbUnit = unitRepository.findOne(unitId);

		Ingredient i = new Ingredient.Builder()
			.description(description)
			.quantity(quantity)
			.units(dbUnit)
			.id(new ObjectId().toString())
			.build();
		Recipe recipe = this.recipeRepository.findOne(recipeId);
		recipe.getIngredients().add(i);
		this.recipeRepository.update(recipe);
		return recipe;
	}

	public Recipe deleteIngredient(String username, String rid, String iid) {
		Recipe recipe = this.recipeRepository.findOne(rid);
		for (Ingredient ingredient : recipe.getIngredients()) {
			if (iid.equals(ingredient.getId())) {
				recipe.getIngredients().remove(ingredient);
				break;
			}
		}
		this.recipeRepository.update(recipe);
		return this.recipeRepository.findOne(rid);
	}

	// Units are global - users can't change this list
	public Unit createUnit(String name) {
		Unit unit = unitRepository.findByName(name);
		if (unit != null) {
			unit = new Unit.Builder().name(name).build();
			unitRepository.save(unit);
			unit = unitRepository.findByName(name);
		}

		return unit;
	}

	public List<Unit> deleteUnit(String unitId) {
		unitRepository.delete(unitId);
		return getUnits();
	}

	public List<Unit> getUnits() {
		return unitRepository.findAll();
	}
		
	public List<RecipeStep> getSteps(String rid, String step) {
		Recipe r = this.recipeRepository.findOne(rid);		
		if(r == null) {	
			return new ArrayList<RecipeStep>();
		} else {
			return r.getSteps();
		}
	}
	
	public Recipe addStep(String uid, String rid, String step) {
		Recipe recipe = this.recipeRepository.findByUserIdAndId(uid, rid);
		RecipeStep rStep = new RecipeStep.Builder().description(step).number(recipe.getSteps().size()).build();		
		recipe.getSteps().add(rStep);		
		this.recipeRepository.update(recipe);
		return recipe;
	}
	
	public Recipe deleteStep(String uid, String rid, Integer stepNumber) {
		Recipe recipe = this.recipeRepository.findByUserIdAndId(uid, rid);
		if(stepNumber < 0 || recipe.getSteps().size() <= stepNumber) {
			throw new IllegalArgumentException("step number out of bounds: " + stepNumber); // stepNumber is out of bounds
		}
		
		Iterator<RecipeStep> steps = recipe.getSteps().iterator(); // no foreach to avoid concurrent modification exception
		while(steps.hasNext()) {
			RecipeStep step = steps.next();
			if(step.getNumber() == stepNumber) {
				steps.remove();
			} else if(step.getNumber() > stepNumber) {
				step.setNumber(step.getNumber() - 1);
			}
		}
		
		this.recipeRepository.update(recipe);
		return recipe;
	}	
}
