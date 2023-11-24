package microservie_testJWT.Security.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Habilita la seguridad en los metodos de la app
@RequiredArgsConstructor
@EnableMethodSecurity // Seguridad a nivel de metodos
public class SecurityConfig {
    private final FiltroJWT filtroJWT;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf( csrf -> csrf.disable())
                .authorizeHttpRequests(  //defino endpoints publicos o privados
                        auth -> auth.requestMatchers("/api/admin/login","/api/admin/register").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(filtroJWT, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }


}
