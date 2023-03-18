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
@Table(name = "direction")
@AllArgsConstructor
@NoArgsConstructor
public class Direction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    int direction_id;
    @NotBlank
    String str;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    @JsonIgnore
    Recipe recipe;
    public Direction(String str) {
        this.str=str;
    }
}
