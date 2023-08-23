package tacos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor(access= AccessLevel.PRIVATE, force=true)
public class Ingredient {

  private final String name;
  private final Type type;
  
  public static enum Type {
    WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
  }
  
}
