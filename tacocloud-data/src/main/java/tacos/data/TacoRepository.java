package tacos.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import tacos.Taco;

//3.1.2
public interface TacoRepository 
         extends ReactiveCrudRepository<Taco, String> {

}
