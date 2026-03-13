package gentjanahani.registro_elettronico_backend.controllers;

import gentjanahani.registro_elettronico_backend.entities.User;
import gentjanahani.registro_elettronico_backend.exceptions.ValidationException;
import gentjanahani.registro_elettronico_backend.payloads.request.UpdateValutazioneDTO;
import gentjanahani.registro_elettronico_backend.payloads.request.ValutazioneDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.ValutazioneResponseDTO;
import gentjanahani.registro_elettronico_backend.sevices.ValutazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/valutazioni")
public class ValutazioneController {

    private final ValutazioneService valutazioneService;

    @Autowired
    public ValutazioneController(ValutazioneService valutazioneService) {
        this.valutazioneService = valutazioneService;
    }

    //    POST i voti di uno studente (ADMIN-PROFESSORE)
    // http://localhost:8081/valutazioni
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSORE')")
    public ValutazioneResponseDTO addVoto(@Validated @RequestBody ValutazioneDTO payload, @AuthenticationPrincipal User user, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            List<String> errorList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorList);

        } else {

            return valutazioneService.addVoto(payload, user);
        }
    }

    //    PATCH il voto di uno studente (ADMIN-PROFESSORE)
    // http://localhost:8081/valutazioni/{idValutazione}
    @PatchMapping("/{idValutazione}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSORE')")
    public ValutazioneResponseDTO updateVoto(@PathVariable UUID idValutazione, @RequestBody UpdateValutazioneDTO payload, @AuthenticationPrincipal User user) {
        return valutazioneService.updateVoto(idValutazione, payload, user);
    }

    //    DELETE il voto di uno studente (ADMIN-PROFESSORE)
    // http://localhost:8081/valutazioni/{idValutazione}
    @DeleteMapping("/{idValutazione}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSORE')")
    public void deleteVoto(@PathVariable UUID idValutazione, @AuthenticationPrincipal User user) {
        valutazioneService.deleteVoto(idValutazione, user);
    }
}
