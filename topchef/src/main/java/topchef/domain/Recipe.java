package topchef.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

//@JsonAutoDetect(getterVisibility = Visibility.NONE, fieldVisibility = Visibility.ANY)
//@JsonFilter(value="tag")
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document
public class Recipe {
	private List<Ingredient> ingredients;
	private List<RecipeStep> steps;
	private String title;
	private String userId;
	
	@Id
	private String id;
		
	public Recipe() {	
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;		
	}
	
	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public List<RecipeStep> getSteps() {
		return steps;
	}

	public void setSteps(List<RecipeStep> steps) {
		this.steps = steps;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private Recipe(Recipe.Builder builder) {
		this.userId = builder.userId;
		this.steps = builder.steps;
		this.title = builder.title;
		this.ingredients = builder.ingredients;
		this.id = builder.id;
	}
	
	public static class Builder {
		private List<Ingredient> ingredients;
		private List<RecipeStep> steps;
		private String title;
		private String userId;
		private String id;
		
		public Builder() { }
		
		public Builder id(String id) {
			this.id = id;
			return this;
		}
		
		public Builder userId(String userId) {
			this.userId = userId;
			return this;
		}
		
		public Builder ingredients(List<Ingredient> ingredients) {
			this.ingredients = ingredients;
			return this;
		}
		
		public Builder steps(List<RecipeStep> steps) {
			this.steps = steps;
			return this;
		}
		
		public Builder title(String title) {
			this.title = title;
			return this;
		}
		
		public Recipe build() {
			return new Recipe(this);
		}
	}
}
