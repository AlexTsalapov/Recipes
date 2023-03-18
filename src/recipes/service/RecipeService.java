package recipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.dto.RecipeDto;
import recipes.entity.Direction;
import recipes.entity.Ingredient;
import recipes.entity.Recipe;
import recipes.repo.RecipeRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepo recipeRepo;


    public List<RecipeDto> findWithName(String name) {
        List<Recipe> recipe;
        recipe = recipeRepo.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
        List<RecipeDto> recipeDtos = new ArrayList<>();
        for (int i = 0; i < recipe.size(); i++) {
            RecipeDto temp = new RecipeDto();
            temp = recipe.get(i).toRecipeDto(recipe.get(i));
            recipeDtos.add(temp);
        }
        return recipeDtos;
    }

    public List<RecipeDto> findWithCategory(String category) {
        List<Recipe> recipe;
        recipe = recipeRepo.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
        List<RecipeDto> recipeDtos = new ArrayList<>();
        for (int i = 0; i < recipe.size(); i++) {
            RecipeDto temp = new RecipeDto();
            temp = recipe.get(i).toRecipeDto(recipe.get(i));
            recipeDtos.add(temp);
        }
        return recipeDtos;
    }

    public boolean existById(int id) {
        return recipeRepo.existsById(id);
    }

    public RecipeDto findById(Integer id) {
        Recipe recipe = recipeRepo.findById(id).get();
        recipe.setDate(LocalDateTime.now());
        return recipe.toRecipeDto(recipe);
    }
    public Recipe findByIdEntity(int id){
       return recipeRepo.findById(id).get();
    }

    public void update(RecipeDto recipe, int id) {
        Recipe recipeNew = recipeRepo.findById(id).get();
        recipeNew.setName(recipe.getName());
        recipeNew.setCategory(recipe.getCategory());

        for (int i = 0; i < recipeNew.getIngredients().size(); i++) {
            recipeNew.getIngredients().get(i).setRecipe(null);
        }
        for (int i = 0; i < recipeNew.getDirections().size(); i++) {
            recipeNew.getDirections().get(i).setRecipe(null);
        }

        List<Direction> directions = new ArrayList<>();
        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < recipe.getDirections().size(); i++) {
            Direction direction = new Direction();
            direction.setStr(recipe.getDirections().get(i));
            direction.setRecipe(recipeNew);
            directions.add(direction);
        }
        recipeNew.setDirections(directions);
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            Ingredient ingredient = new Ingredient();
            ingredient.setStr(recipe.getIngredients().get(i));
            ingredient.setRecipe(recipeNew);
            ingredients.add(ingredient);
        }
        recipeNew.setIngredients(ingredients);


        recipeNew.setDescription(recipe.getDescription());
        recipeNew.setDate(recipe.getDate());
        recipeRepo.save(recipeNew);
    }

    public Integer save(Recipe recipe) {

        return recipeRepo.save(recipe).getId();
    }

    public Integer delete(Integer id) {
        recipeRepo.deleteById(id);
        return id;

    }
}