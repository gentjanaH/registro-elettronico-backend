package gentjanahani.registro_elettronico_backend.controllers;

import gentjanahani.registro_elettronico_backend.entities.Giustificazione;
import gentjanahani.registro_elettronico_backend.entities.User;
import gentjanahani.registro_elettronico_backend.exceptions.ValidationException;
import gentjanahani.registro_elettronico_backend.payloads.request.GiustificazioneDTO;
import gentjanahani.registro_elettronico_backend.services.GiustificazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/giustificazioni")
public class GiustificazioneController {

    private final GiustificazioneService giustificazioneService;

    @Autowired
    public GiustificazioneController(GiustificazioneService giustificazioneService) {
        this.giustificazioneService = giustificazioneService;
    }

    @PostMapping("/presenze/{idPresenza}")
    @PreAuthorize("hasRole('GENITORE')")
    public Giustificazione giustificaAssenza(
            @PathVariable UUID idPresenza,
            @RequestBody @Validated GiustificazioneDTO payload,
            @AuthenticationPrincipal User user,
            BindingResult validationResult

    ) {

        if (validationResult.hasErrors()) {
            List<String> errorList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorList);

        } else {

            return giustificazioneService.giustifica(idPresenza, payload, user);
        }
    }
}
