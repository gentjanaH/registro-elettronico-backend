package gentjanahani.registro_elettronico_backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


//questa classe mi serve per configurare la Security Filter Chain (sequenza di filtri che vengono eseguiti
// ognuno  con un ruolo specifico nella gestione della sicurezza).
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {

        //all'interno di questo bean disabilitare o modificare comportamenti di default che non vanno sempre bene
        httpSecurity.formLogin(formLogin -> formLogin.disable());//disabilito l'autenticaione di Spring Security

        httpSecurity.csrf(csrf -> csrf.disable());//disabilito questa ulteriore protezione, perchè complica le cose lato frontend

        httpSecurity.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers("/**").permitAll());

        httpSecurity.cors(Customizer.withDefaults());
        return httpSecurity.build();
    }

    //creo il bean PasswordEncoder
    @Bean
    public PasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder(12);//numeri di round
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:5173", "https://mywonderfulfe.com"));
        // Qua definisco una WHITELIST di uno o più indirizzi FRONTEND che voglio possano accedere a questo backend senza i problemi di CORS
        // Volendo ma rischioso si potrebbe anche mettere '*' però questo permetterebbe l'accesso a tutti (utile solo nel caso di API pubbliche)
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}