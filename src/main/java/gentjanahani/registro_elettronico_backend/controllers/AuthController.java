package gentjanahani.registro_elettronico_backend.controllers;

import gentjanahani.registro_elettronico_backend.entities.User;
import gentjanahani.registro_elettronico_backend.payloads.request.LoginDTO;
import gentjanahani.registro_elettronico_backend.payloads.request.RegisterDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.LoginResponseDTO;
import gentjanahani.registro_elettronico_backend.sevices.AuthorizationService;
import gentjanahani.registro_elettronico_backend.sevices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final AuthorizationService authorizationService;
    private final UserService userService;

    @Autowired
    public AuthController(AuthorizationService authorizationService, UserService userService) {

        this.authorizationService = authorizationService;
        this.userService = userService;
    }

    // http://localhost:8081/auth/login
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO bodyLogin){
        return new LoginResponseDTO(this.authorizationService.checkAndGenerate(bodyLogin));
    }

    // http://localhost:8081/auth/register
    @PostMapping("/register")
    public User register(@RequestBody RegisterDTO payload){
        return this.userService.register(payload);
    }
}
