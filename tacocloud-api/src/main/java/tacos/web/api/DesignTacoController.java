package tacos.web.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tacos.Taco;
import tacos.data.TacoRepository;

@Slf4j
@RestController
@RequestMapping(path="/design", produces="application/json")
@CrossOrigin(origins="*")
public class DesignTacoController {
  private TacoRepository tacoRepo;

  public DesignTacoController(TacoRepository tacoRepo) {
    this.tacoRepo = tacoRepo;
  }

  @GetMapping("/recent")
  public Flux<Taco> recentTacos() {
    // 프레임워크가 자동 호출 해주기에 .subscribe()를 호출 하지 않아도 된다.
      return tacoRepo.findAll().take(12);
  }

  //tag::postTaco[]
  @PostMapping(consumes="application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Taco> postTaco(@RequestBody Taco taco) {
    return tacoRepo.save(taco);
  }
  //end::postTaco[]
  
  
  @GetMapping("/{id}")
  public Mono<Taco> tacoById(@PathVariable("id") String id) { // @PathVariable에 의해 {id} 플레이스 홀더와
                                                            // 대응되는 id 매개변수에 해당 요청의 실제값 지정
    return tacoRepo.findById(id);
  }
}

