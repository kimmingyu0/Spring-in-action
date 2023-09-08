package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter.*;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig implements WebFluxConfigurer {

    /*WebFlux 에서 SpringSecurity config*/
    private final UserRepositoryUserDetailsService userDetailsService;

    public SecurityConfig(UserRepositoryUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

  @Bean
  public SecurityWebFilterChain filterChain(ServerHttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .authorizeExchange(
                    exchange ->
                            exchange.pathMatchers(HttpMethod.OPTIONS).permitAll()
                                    .pathMatchers("/design", "/orders/**", "login")
                                    .permitAll()
                                    .pathMatchers(HttpMethod.PATCH, "/ingredients").permitAll()
                                    .anyExchange().permitAll()
            )
            .httpBasic(withDefaults())
            .logout(logout->logout.requiresLogout(new PathPatternParserServerWebExchangeMatcher("/logout")))
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .headers(header->header.frameOptions(frameOption->frameOption.mode(Mode.SAMEORIGIN)));
    return httpSecurity.build();
  }

      @Override
      public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST")
                .allowedHeaders("*")
                .exposedHeaders("*");
      }
  @Bean
  public PasswordEncoder encoder() {
//    return new StandardPasswordEncoder("53cr3t");
    return NoOpPasswordEncoder.getInstance();
  }

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService() {
        return userDetailsService;
    }

    /*WebMVC 에서 SpringSecurity Config*/
//
//  HttpSecurity, WebSecurity를 원래는 WebSecurityConfigurerAdapter 상속받아 Override 하였지만,
//  Security 6 이후로 Bean을 직접 등록하여 설정하게 바뀌었다.
//  @Bean
//  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//    httpSecurity
//            .authorizeRequests(
//                    request ->
//                            request.requestMatchers(HttpMethod.OPTIONS).permitAll()
//                                    .requestMatchers("/design", "/orders/**")
//                                    .permitAll()
//                                    .requestMatchers(HttpMethod.PATCH, "/ingredients").permitAll()
//                                    .requestMatchers("/**").access("permitAll")
//            )
//            .httpBasic(basic -> basic.realmName("Taco Cloud"))
//            .logout(logout -> logout.logoutSuccessUrl("/"))
//            .cors(Customizer.withDefaults())
//            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**", "/ingredients/**", "/design", "/orders/**", "/api/**", "/tacos/**", "/register/**", "/customLogin/**"))
//            .headers(header->header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
//    return httpSecurity.build();
//  }

//  @Bean
//  CorsConfigurationSource corsConfigurationSource() {
//    CorsConfiguration configuration = new CorsConfiguration();
//    configuration.setAllowCredentials(true);
//    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//    configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//    configuration.setAllowedHeaders(Arrays.asList("*"));
//    configuration.setExposedHeaders(Arrays.asList("*"));
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/**", configuration);
//    return source;
//  }

//  encoder , userDetailsService 둘 다 컨테이너에 등록되어있어서 자동 설정해줌.
//  @Bean
//  AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
//    return auth.getAuthenticationManager();
//  }

}
