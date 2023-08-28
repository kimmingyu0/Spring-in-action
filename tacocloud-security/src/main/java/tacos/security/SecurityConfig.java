package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation
             .authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web
             .builders.HttpSecurity;
import org.springframework.security.config.annotation.web
                        .configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig {
  
  @Autowired
  private UserDetailsService userDetailsService;

  // HttpSecurity, WebSecurity를 원래는 WebSecurityConfigurerAdapter 상속받아 Override 하였지만,
  // Security 6 이후로 Bean을 직접 등록하여 설정하게 바뀌었다.

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .authorizeRequests(
                    request ->
                            request.requestMatchers(HttpMethod.OPTIONS).permitAll()
                                    .requestMatchers("/design", "/orders/**")
                                    .permitAll()
                                    .requestMatchers(HttpMethod.PATCH, "/ingredients").permitAll()
                                    .requestMatchers("/**").access("permitAll")
            )
//            .formLogin(login -> login.loginPage("/login").permitAll())
            .httpBasic(basic -> basic.realmName("Taco Cloud"))
            .logout(logout -> logout.logoutSuccessUrl("/"))
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**", "/ingredients/**", "/design", "/orders/**", "/api/**", "/tacos/**", "/register/**", "/customLogin"))
            .headers(header->header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
    return httpSecurity.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowCredentials(true);
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
    configuration.setAllowedMethods(Arrays.asList("GET","POST"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setExposedHeaders(Arrays.asList("*"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//
//    http
//      .authorizeRequests()
//        .antMatchers(HttpMethod.OPTIONS).permitAll() // needed for Angular/CORS
//        .antMatchers("/design", "/orders/**")
//            .permitAll()
//            //.access("hasRole('ROLE_USER')")
//        .antMatchers(HttpMethod.PATCH, "/ingredients").permitAll()
//        .antMatchers("/**").access("permitAll")
//
//      .and()
//        .formLogin()
//          .loginPage("/login")
//
//      .and()
//        .httpBasic()
//          .realmName("Taco Cloud")
//
//      .and()
//        .logout()
//          .logoutSuccessUrl("/")
//
//      .and()
//        .csrf()
//          .ignoringAntMatchers("/h2-console/**", "/ingredients/**", "/design", "/orders/**")
//
//      // Allow pages to be loaded in frames from the same origin; needed for H2-Console
//      .and()
//        .headers()
//          .frameOptions()
//            .sameOrigin()
//      ;
//  }

  @Bean
  public PasswordEncoder encoder() {
//    return new StandardPasswordEncoder("53cr3t");
    return NoOpPasswordEncoder.getInstance();
  }

  //encoder , userDetailsService 둘 다 컨테이너에 등록되어있어서 자동 설정해줌.
  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
//    auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    return auth.getAuthenticationManager();
  }
//  @Override
//  protected void configure(AuthenticationManagerBuilder auth)
//      throws Exception {
//
//    auth
//      .userDetailsService(userDetailsService)
//      .passwordEncoder(encoder());
//
//  }

}
