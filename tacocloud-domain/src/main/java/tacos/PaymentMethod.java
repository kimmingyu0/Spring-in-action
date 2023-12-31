package tacos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//@Entity
@Data
@NoArgsConstructor(force=true, access=AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Document
public class PaymentMethod {

  @Id
  private String id;
  
  private final User user;
  private final String ccNumber;
  private final String ccCVV;
  private final String ccExpiration;
  
}
