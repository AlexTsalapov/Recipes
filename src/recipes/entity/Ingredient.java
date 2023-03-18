package recipes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    int ingredient_id;
    @NotBlank
    String str;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    @JsonIgnore
    Recipe recipe;

    public Ingredient(String str) {
        this.str = str;
    }
}
