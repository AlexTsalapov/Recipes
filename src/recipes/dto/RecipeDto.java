package recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import recipes.entity.Direction;
import recipes.entity.Ingredient;
import recipes.entity.Recipe;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto {
   private String name;
    String description;
    String category;
    LocalDateTime date;
    List<String> ingredients;
    List<String> directions;
    public Recipe toRecipe(RecipeDto recipeDto){
        Recipe recipe=new Recipe();
        recipe.setName(recipeDto.getName());
        recipe.setCategory(recipeDto.getCategory());
        recipe.setDate(LocalDateTime.now());
        recipe.setDescription(recipeDto.getDescription());
        List<Direction> directions = new ArrayList<>();
        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < recipeDto.getDirections().size(); i++) {
            Direction direction=new Direction();
            direction.setStr(recipeDto.getDirections().get(i));
            direction.setRecipe(recipe);
            directions.add(direction);
        }
        recipe.setDirections(directions);
        for (int i = 0; i < recipeDto.getIngredients().size(); i++) {
            Ingredient ingredient=new Ingredient();
            ingredient.setStr(recipeDto.getIngredients().get(i));
            ingredient.setRecipe(recipe);
            ingredients.add(ingredient);
        }
        recipe.setIngredients(ingredients);
        return recipe;
    }
}