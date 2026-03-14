package gentjanahani.registro_elettronico_backend.controllers;


import gentjanahani.registro_elettronico_backend.entities.Professore;
import gentjanahani.registro_elettronico_backend.entities.User;
import gentjanahani.registro_elettronico_backend.exceptions.ValidationException;
import gentjanahani.registro_elettronico_backend.payloads.request.LezioneDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.LezioneResponseDTO;
import gentjanahani.registro_elettronico_backend.sevices.LezioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/lezioni")
public class LezioneController {

    private final LezioneService lezioneService;

    @Autowired
    public LezioneController(LezioneService lezioneService) {
        this.lezioneService = lezioneService;
    }

    // http://localhost:8081/lezioni
    @PostMapping("/classi/{idClasse}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('PROFESSORE')")
    public LezioneResponseDTO addLezione(@Validated @RequestBody LezioneDTO payload,
                                         @AuthenticationPrincipal User prof,
                                         @PathVariable UUID idClasse,
                                         BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            List<String> errorList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorList);

        } else {
            LezioneResponseDTO created = lezioneService.addLezione(payload, prof, idClasse);
            return created;
        }

    }

    //endpoint per:
    //modificare data-ora-e-materia lezione
    @PatchMapping("/{idLezione}")
    @PreAuthorize("hasRole('PROFESSORE')")
    public LezioneResponseDTO updateLezione(
            @PathVariable UUID idLezione,
            @Validated @RequestBody LezioneDTO payload,
            @AuthenticationPrincipal User prof,
            BindingResult validationResult
    ) {
        if (validationResult.hasErrors()) {
            List<String> errorList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorList);
        }

        return lezioneService.updateLezione(idLezione, payload, prof);
    }

    //eliminare una lezione
    @DeleteMapping("/{idLezione}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('PROFESSORE')")
    public void deleteLezione(
            @PathVariable UUID idLezione,
            @AuthenticationPrincipal User prof
    ) {
        lezioneService.deleteLezione(idLezione, prof);
    }

    //visualizzare tutte le lezioni
    @GetMapping
    @PreAuthorize("hasAnyRole('PROFESSORE', 'GENITORE', 'STUDENTE')")
    public List<LezioneResponseDTO> getAllLezioni() {
        return lezioneService.getAllLezioni();
    }


    //visualizzare una lezione
    @GetMapping("/{idLezione}")
    @PreAuthorize("hasAnyRole('PROFESSORE', 'GENITORE', 'STUDENTE')")
    public LezioneResponseDTO getLezioneById(@PathVariable UUID idLezione) {
        return lezioneService.getLezioneById(idLezione);
    }

}
