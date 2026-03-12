package gentjanahani.registro_elettronico_backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


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


        return httpSecurity.build();
    }

    //creo il bean PasswordEncoder
    @Bean
    public PasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder(12);//numeri di round
    }
}