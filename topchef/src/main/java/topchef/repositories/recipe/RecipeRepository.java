package topchef.repositories.recipe;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import topchef.domain.Recipe;

public interface RecipeRepository extends PagingAndSortingRepository<Recipe, String>, UpdateableRecipeRepository {
	public List<Recipe> findByUserId(String userId);
	public Recipe findByUserIdAndTitle(String userId, String title);
	public Recipe findByUserIdAndId(String userId, String id);
}
