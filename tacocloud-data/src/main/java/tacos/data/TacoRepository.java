package tacos.data;

import org.springframework.data.repository.PagingAndSortingRepository;

import tacos.Taco;

import java.util.Optional;

//3.1.2
public interface TacoRepository 
         extends PagingAndSortingRepository<Taco, Long> {


    // 1) 새로 추가
    /*
    * INSERT INTO taco (column1, column2, column3, ...)
    * VALUES (value1, value2, value3, ...);
    * */
    // 2) 이미 존재
    /*
    * UPDATE taco
    * SET column1 = new_value1, column2 = new_value2, ...
    * WHERE id = taco_id;
    * */
    Taco save(Taco taco);

    // SELECT * FROM taco WHERE id = :id;
    // => :id -> arguments
    Optional<Taco> findTacoById(Long id);
}
