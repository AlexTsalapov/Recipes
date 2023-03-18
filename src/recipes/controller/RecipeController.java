package recipes.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import recipes.dto.NewRecipeDto;
import recipes.dto.RecipeDto;
import recipes.entity.Recipe;
import recipes.entity.User;
import recipes.service.RecipeService;
import recipes.service.UserService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipe")
@AllArgsConstructor
public class RecipeController {
    @Autowired
    private RecipeService recipeService;
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity getRecipe(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(recipeService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping("/new")
    public ResponseEntity<NewRecipeDto> postRecipe(@Valid @RequestBody RecipeDto recipeDto) {
        try {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetails details = (UserDetails) auth.getPrincipal();
            User user = userService.findByUsername(details.getUsername());
            Recipe recipe = recipeDto.toRecipe(recipeDto);
            recipe.setUser(user);
            Integer id = recipeService.save(recipe);
            NewRecipeDto dto = new NewRecipeDto(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRecipe(@PathVariable Integer id) {
        Recipe recipe = new Recipe();
        try {
            recipe = recipeService.findByIdEntity(id);
        } catch (Exception ex) {
            return ResponseEntity.status(404).build();
        }
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetails details = (UserDetails) auth.getPrincipal();
            User user = userService.findByUsername(details.getUsername());
            if (!user.equals(recipe.getUser())) {
                return ResponseEntity.status(403).build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(403).build();
        }
        try {

            recipeService.delete(id);
            return ResponseEntity.status(204).build();

        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity updateRecipe(@PathVariable Integer id, @Valid @RequestBody RecipeDto recipeDto) {
        try {
            Recipe recipe;
            try {
                recipe = recipeService.findByIdEntity(id);
            } catch (Exception ex) {
                return ResponseEntity.status(404).build();
            }
            try {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                UserDetails details = (UserDetails) auth.getPrincipal();
                User user = userService.findByUsername(details.getUsername());
                if (!user.equals(recipe.getUser())) {
                    return ResponseEntity.status(403).build();
                }
            } catch (Exception ex) {
                return ResponseEntity.status(403).build();
            }
            recipeService.update(recipeDto, id);
            return ResponseEntity.status(204).build();

        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }

    }

    @GetMapping("/search")
    public ResponseEntity getRecipesWithName(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "category", required = false) String category) {

        try {
            if (category != null) {
                return ResponseEntity.ok(recipeService.findWithCategory(category));
            } else if (name != null) {
                return ResponseEntity.ok(recipeService.findWithName(name));
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.status(400).build();
    }
}












