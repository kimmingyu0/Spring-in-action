package tacos;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
//@Entity
@Document

public class Ingredient {
  
  @Id
  private String id;
  private String name;
  private Type type;

  public Ingredient(){}
  public Ingredient(String id, String name, Type type) {
    this.id = id;
    this.name =name;
    this.type = type;
  }
  public static enum Type {
    WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
  }

}
