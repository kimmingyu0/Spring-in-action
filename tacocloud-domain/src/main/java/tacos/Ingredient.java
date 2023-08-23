package tacos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Entity
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
