package recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

@Value
@ConstructorBinding
@AllArgsConstructor
public class NewRecipeDto {
    Integer id;

}