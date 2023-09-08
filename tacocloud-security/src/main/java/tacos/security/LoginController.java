package tacos.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import tacos.User;
import tacos.data.UserRepository;

@Slf4j
@RestController
@RequestMapping("/customLogin")
@CrossOrigin("*")
public class LoginController {

    private final UserRepository userRepo;

    @Autowired
    public LoginController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping(consumes = "application/json")
    public Mono<ResponseEntity<User>> processLogin(@RequestBody User user) {
        return Mono.just(user)
                .flatMap(this::loginUser) // null
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    private Mono<User> loginUser(User user) {
        return userRepo.findByUsername(user.getUsername())
                .filter(loginUser -> loginUser.getPassword().equals(user.getPassword()))
                .switchIfEmpty(Mono.empty());
    }
}