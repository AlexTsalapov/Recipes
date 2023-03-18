package recipes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import recipes.dto.RecipeDto;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recipes")
public class Recipe {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @NotBlank
    private String name;
    @NotBlank
    String description;
    @NotBlank
    String category;
    LocalDateTime date;
    @NotEmpty
    @OneToMany(cascade = CascadeType.ALL)
    List<Ingredient> ingredients;
    @NotEmpty
    @OneToMany(cascade = CascadeType.ALL)
    List<Direction> directions;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    User user;

    public RecipeDto toRecipeDto(Recipe recipe) {

        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName(recipe.getName());
        recipeDto.setCategory(recipe.getCategory());
        recipeDto.setDate(recipe.getDate());
        recipeDto.setDescription(recipe.getDescription());
        List<String> directions = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();
        for (int i = 0; i < recipe.getDirections().size(); i++) {
            directions.add(recipe.getDirections().get(i).getStr());
        }
        recipeDto.setDirections(directions);
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            ingredients.add(recipe.getIngredients().get(i).getStr());
        }
        recipeDto.setIngredients(ingredients);
        return recipeDto;
    }
}
