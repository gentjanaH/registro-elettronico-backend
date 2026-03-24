package gentjanahani.registro_elettronico_backend.controllers;

import gentjanahani.registro_elettronico_backend.payloads.request.UtentiPerRuoloDTO;
import gentjanahani.registro_elettronico_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utenti")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/divisi-per-ruolo")
    @PreAuthorize("hasRole('ADMIN')")
    public UtentiPerRuoloDTO getAllDivisiPerRuolo() {
        return userService.findAllDivisiPerRuolo();
    }
}
