package gentjanahani.registro_elettronico_backend.sevices;

import gentjanahani.registro_elettronico_backend.entities.User;
import gentjanahani.registro_elettronico_backend.exceptions.UnauthorizedException;
import gentjanahani.registro_elettronico_backend.payloads.request.LoginDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.LoginResponseDTO;
import gentjanahani.registro_elettronico_backend.security.JWTTools;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final UserService userService;
    private final JWTTools jwtTools;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthorizationService(UserService userService, JWTTools jwtTools, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtTools = jwtTools;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO checkAndGenerate(LoginDTO bodyLogin) {

        User user = this.userService.findByEmail(bodyLogin.email());

        if (passwordEncoder.matches(bodyLogin.password(), user.getPassword())) {
            String accessToken = jwtTools.generateToken(user);

            return new LoginResponseDTO(accessToken, user);
        } else {
            throw new UnauthorizedException("Credenziali non valide");
        }
    }
}
