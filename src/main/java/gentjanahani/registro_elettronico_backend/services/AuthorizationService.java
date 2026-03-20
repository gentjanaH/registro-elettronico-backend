package gentjanahani.registro_elettronico_backend.services;

import gentjanahani.registro_elettronico_backend.entities.Genitore;
import gentjanahani.registro_elettronico_backend.entities.Professore;
import gentjanahani.registro_elettronico_backend.entities.Studente;
import gentjanahani.registro_elettronico_backend.entities.User;
import gentjanahani.registro_elettronico_backend.exceptions.UnauthorizedException;
import gentjanahani.registro_elettronico_backend.payloads.request.LoginDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.LoginResponseDTO;
import gentjanahani.registro_elettronico_backend.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final UserService userService;
    private final JWTTools jwtTools;
    private final PasswordEncoder passwordEncoder;
    private final StudenteService studenteService;
    private final GenitoreService genitoreService;
    private final ProfessoreService professoreService;

    @Autowired
    public AuthorizationService(UserService userService, JWTTools jwtTools, PasswordEncoder passwordEncoder, StudenteService studenteService, GenitoreService genitoreService, ProfessoreService professoreService) {
        this.userService = userService;
        this.jwtTools = jwtTools;
        this.passwordEncoder = passwordEncoder;
        this.studenteService = studenteService;
        this.genitoreService = genitoreService;
        this.professoreService = professoreService;
    }

    public LoginResponseDTO checkAndGenerate(LoginDTO bodyLogin) {

        User user = this.userService.findByEmail(bodyLogin.email());

        if (!passwordEncoder.matches(bodyLogin.password(), user.getPassword())) {
            throw new UnauthorizedException("Credenziali non valide");
        }

        String accessToken = jwtTools.generateToken(user);

        Studente studente = null;
        Professore professore = null;
        Genitore genitore = null;

        switch (user.getRuolo().getRuolo()) {

            case "STUDENTE" -> {
                studente = studenteService.findByUser(user);
            }

            case "GENITORE" -> {
                genitore = genitoreService.findUser(user);
            }

            case "PROFESSORE" -> {
                professore = professoreService.findByUser(user);
            }
        }

        return new LoginResponseDTO(accessToken, user, studente, professore, genitore);
    }
}
