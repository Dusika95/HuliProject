package huli.example.huliwebshop.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf() // stands for "cross-site request forgery" - is a web security vulnerability that allows an attack that forces an end user to execute unwanted actions on a web application in which they are currently authenticated
                .disable() // to simplify interactions between a client and the server
                .authorizeHttpRequests()
                .antMatchers(HttpMethod.GET,"/all-product") // here we can pass a list of strings - this will represent our application's patterns
                .permitAll() // permit all the requests from the matchers list (whitelist)
                //.antMatchers("/admin/buildings/**").hasAuthority("admin")
                .anyRequest() // any other request must be authenticated
                .authenticated()
                .and()
                .sessionManagement() //using "once per request filter" means the session state should not be stored - the session should be "stateless" - this ensures that each request will be authenticated
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider); // tell spring which authentication provider we want to use

        http.cors();
        return http.build();
    }
}
