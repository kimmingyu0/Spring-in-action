package tacos.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import tacos.Ingredient;

// url -> /api/ingredients
// data-rest 종속성으로 rest api를 자동생성해줌.
// 이 때 /api/ 부분은 설정파일에서 변경할 수 있다.

@CrossOrigin(origins="*")
public interface IngredientRepository 
         extends CrudRepository<Ingredient, String> {

}
