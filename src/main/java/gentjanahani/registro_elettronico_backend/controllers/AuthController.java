package gentjanahani.registro_elettronico_backend.controllers;

import gentjanahani.registro_elettronico_backend.entities.User;
import gentjanahani.registro_elettronico_backend.payloads.request.LoginDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.LoginResponseDTO;
import gentjanahani.registro_elettronico_backend.sevices.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final AuthorizationService authorizationService;

    @Autowired
    public AuthController( AuthorizationService authorizationService) {

        this.authorizationService = authorizationService;
    }

    // http://localhost:8081/auth/login
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO bodyLogin){
        return new LoginResponseDTO(this.authorizationService.checkAndGenerate(bodyLogin));
    }
}
