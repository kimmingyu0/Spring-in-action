package tacos.data;

import org.springframework.data.repository.PagingAndSortingRepository;

import tacos.Taco;

import java.util.Optional;

//3.1.2
public interface TacoRepository 
         extends PagingAndSortingRepository<Taco, Long> {


    Taco save(Taco taco);

    Optional<Taco> findTacoById(Long id);
}
