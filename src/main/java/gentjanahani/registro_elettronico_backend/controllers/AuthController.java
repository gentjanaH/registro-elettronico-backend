package gentjanahani.registro_elettronico_backend.controllers;

import gentjanahani.registro_elettronico_backend.entities.User;
import gentjanahani.registro_elettronico_backend.exceptions.ValidationException;
import gentjanahani.registro_elettronico_backend.payloads.request.LoginDTO;
import gentjanahani.registro_elettronico_backend.payloads.request.RegisterDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.LoginResponseDTO;
import gentjanahani.registro_elettronico_backend.services.AuthorizationService;
import gentjanahani.registro_elettronico_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public LoginResponseDTO login(@Validated @RequestBody LoginDTO bodyLogin, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            List<String> errorList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorList);

        } else {
            return this.authorizationService.checkAndGenerate(bodyLogin);
        }


    }

    // http://localhost:8081/auth/register
    @PostMapping("/register")
    public User register(@Validated @RequestBody RegisterDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorList);

        } else {
            return this.userService.register(payload);
        }


    }

    // http://localhost:8081/auth/me
    @GetMapping("/me")
    public User me(@AuthenticationPrincipal User user) {
        return user;
    }


}
