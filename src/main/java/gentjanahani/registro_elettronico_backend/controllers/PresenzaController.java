package gentjanahani.registro_elettronico_backend.controllers;

import gentjanahani.registro_elettronico_backend.entities.Presenza;
import gentjanahani.registro_elettronico_backend.payloads.request.PresenzaDTO;
import gentjanahani.registro_elettronico_backend.payloads.request.UpDatePresenzaDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.PresenzaResponseDTO;
import gentjanahani.registro_elettronico_backend.services.PresenzaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/presenze")
public class PresenzaController {

    private final PresenzaService presenzaService;

    @Autowired
    public PresenzaController(PresenzaService presenzaService) {
        this.presenzaService = presenzaService;
    }

    //    POST le essenze di uno studente (ADMIN-PROFESSORE)
    @PostMapping("/studente/{idStudente}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSORE')")
    public PresenzaResponseDTO registraPresenza(
            @PathVariable UUID idStudente,
            @RequestBody @Valid PresenzaDTO payload
    ) {
        System.out.println("PAYLOAD: " + payload);

        return presenzaService.addPresenzaAssenza(idStudente, payload);
    }

    //    PATCH l' essenza di uno studente (ADMIN-PROFESSORE)
    @PatchMapping("/{idPresenza}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSORE')")
    public Presenza updatePresenza(
            @PathVariable UUID idPresenza,
            @RequestBody @Valid UpDatePresenzaDTO payload
    ) {
        return presenzaService.updateStatoPresenza(idPresenza, payload);
    }

    //    DELETE l' essenza di uno studente (ADMIN-PROFESSORE)
    @DeleteMapping("/{idPresenza}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSORE')")
    public void removePresenza(@PathVariable UUID idPresenza) {

        this.presenzaService.deletePresenza(idPresenza);
    }
}
