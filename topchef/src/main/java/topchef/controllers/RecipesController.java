package topchef.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import topchef.annotations.CacheControl;
import topchef.annotations.CachePolicy;
import topchef.domain.Recipe;
import topchef.domain.RecipeStep;
import topchef.exceptions.DuplicateRecipeException;
import topchef.services.RecipesService;

import com.fasterxml.jackson.annotation.JsonFormat;

@RestController
@RequestMapping("/api")
@JsonFormat
@CacheControl(maxAge=300, policy={CachePolicy.PUBLIC})
public class RecipesController {
	@Autowired
	private RecipesService recipesService;
	
			
	@RequestMapping(method = RequestMethod.GET, value = "/users/{userid}/recipes")
	@PostAuthorize("@methodSecurity.principalIsUser(principal,#userid)")	
	public List<Recipe> getRecipes(@PathVariable String userid){
		return recipesService.getRecipes(userid);		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/users/{userid}/recipes")
	@PreAuthorize("@methodSecurity.principalIsUser(principal,#userid)")	
	public Recipe createRecipe(@PathVariable String userid, @RequestParam String title) throws DuplicateRecipeException {
		return recipesService.createRecipe(userid, title);		
	}
		
	@RequestMapping(method = RequestMethod.GET, value = "/users/{userid}/recipes/{recipeid}")
	@PreAuthorize("@methodSecurity.principalIsUser(principal,#userid)")	
	public Recipe getRecipe(@PathVariable String userid, @PathVariable  String recipeid) {
		return recipesService.getRecipe(userid, recipeid);
	}	
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/users/{userid}/recipes/{recipeid}")
	@PreAuthorize("@methodSecurity.principalIsUser(principal,#userid)")	
	public void deleteRecipe(@PathVariable String userid, @PathVariable  String recipeid) {
		recipesService.deleteRecipe(userid, recipeid);
	}		
		
	@RequestMapping(method = RequestMethod.POST, value = "/users/{userid}/recipes/{recipeId}/ingredients")
	@PreAuthorize("@methodSecurity.principalIsUser(principal,#userid)")	
	public Recipe addIngredient(@PathVariable String userid, 
									   @PathVariable String recipeId,
									   @RequestParam String description,
									   @RequestParam Float quantity,
									   @RequestParam String unitId) {
		return recipesService.addIngredient(userid, recipeId, description, quantity, unitId);		
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/users/{userid}/recipes/{recipeId}/ingredients/{ingredientId}")
	@PreAuthorize("@methodSecurity.principalIsUser(principal,#userid)")	
	public Recipe deleteIngredient(@PathVariable String userid, 
									@PathVariable String recipeId,
									@PathVariable String ingredientId) {
		return recipesService.deleteIngredient(userid, recipeId, ingredientId);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/users/{userid}/recipes/{recipeId}/steps")
	@PreAuthorize("@methodSecurity.principalIsUser(principal,#userid)")	
	public List<RecipeStep> getSteps(@PathVariable String userid, 
									   @PathVariable String recipeId,
									   @RequestParam String step) {
		Recipe r = recipesService.getRecipe(userid, recipeId);
		if(r == null) {
			return new ArrayList<RecipeStep>();
		} else {
			return r.getSteps();
		}
	}	
	
	@RequestMapping(method = RequestMethod.POST, value = "/users/{userid}/recipes/{recipeId}/steps")
	@PreAuthorize("@methodSecurity.principalIsUser(principal,#userid)")	
	public Recipe addStep(@PathVariable String userid,
							@PathVariable String recipeId,
							@RequestParam String step) {
		return recipesService.addStep(userid, recipeId, step);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/users/{userid}/recipes/{recipeId}/steps/{stepNumber}")
	@PreAuthorize("@methodSecurity.principalIsUser(principal,#userid)")	
	public Recipe deleteStep(@PathVariable String userid,
								@PathVariable String recipeId,
								@PathVariable Integer stepNumber) {	
		return recipesService.deleteStep(userid, recipeId, stepNumber);
	}	
}
