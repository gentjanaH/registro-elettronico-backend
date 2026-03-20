package gentjanahani.registro_elettronico_backend.controllers;

import gentjanahani.registro_elettronico_backend.entities.Ruolo;
import gentjanahani.registro_elettronico_backend.exceptions.ValidationException;
import gentjanahani.registro_elettronico_backend.payloads.request.RuoloDTO;
import gentjanahani.registro_elettronico_backend.services.RuoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public RuoloDTO addRuolo(@Validated @RequestBody RuoloDTO payload, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            List<String> errorList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorList);

        } else {
            Ruolo createdRuolo = ruoloService.addRuolo(payload);
            return this.ruoloService.toRuoloDTO(createdRuolo);
        }

    }

    // http://localhost:8081/ruoli
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Ruolo> getAllRuoli(Pageable pageable) {
        return ruoloService.getAllRuoli(pageable);

    }

    // http://localhost:8081/ruoli/{id}
    @DeleteMapping("/{idRuolo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void removeRuolo(@PathVariable UUID idRuolo) {

        this.ruoloService.deleteRuolo(idRuolo);
    }
}
