package tacos.security;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import tacos.User;
import tacos.data.UserRepository;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegistrationController {
  
  private UserRepository userRepo;
  private PasswordEncoder passwordEncoder;

  public RegistrationController(
      UserRepository userRepo, PasswordEncoder passwordEncoder) {
    this.userRepo = userRepo;
    this.passwordEncoder = passwordEncoder;
  }
  
  @GetMapping
  public String registerForm() {
    return "registration";
  }
  
  @PostMapping(consumes="application/json")
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<User> processRegistration(@RequestBody RegistrationForm form) {
    return userRepo.save(form.toUser(passwordEncoder))
            .doOnSuccess(user -> log.info("user: " + user));
  }

}
