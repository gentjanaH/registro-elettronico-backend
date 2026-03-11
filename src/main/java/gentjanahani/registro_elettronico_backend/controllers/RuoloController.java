package gentjanahani.registro_elettronico_backend.controllers;

import gentjanahani.registro_elettronico_backend.entities.Ruolo;
import gentjanahani.registro_elettronico_backend.payloads.request.RuoloDTO;
import gentjanahani.registro_elettronico_backend.sevices.RuoloService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ruoli")
public class RuoloController {

    private final RuoloService ruoloService;

    @Autowired
    public RuoloController(RuoloService ruoloService) {
        this.ruoloService = ruoloService;
    }

    // http://localhost:8081/ruoli
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public RuoloDTO addRuolo(@Valid @RequestBody RuoloDTO payload) {
        Ruolo createdRuolo = ruoloService.addRuolo(payload);
    return this.ruoloService.toRuoloDTO(createdRuolo);
    }
}
