package gentjanahani.registro_elettronico_backend.controllers;

import gentjanahani.registro_elettronico_backend.entities.User;
import gentjanahani.registro_elettronico_backend.exceptions.ValidationException;
import gentjanahani.registro_elettronico_backend.payloads.request.CompitoDTO;
import gentjanahani.registro_elettronico_backend.payloads.request.CorsiExtraDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.CompitoResponseDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.CorsiExtraResponseDTO;
import gentjanahani.registro_elettronico_backend.services.CorsiExtraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/corsi-extra-curricolari")
public class CorsiExtraController {

    private final CorsiExtraService corsiExtraService;

    @Autowired
    public CorsiExtraController(CorsiExtraService corsiExtraService) {
        this.corsiExtraService = corsiExtraService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public CorsiExtraResponseDTO ADDcORSO(
            @RequestBody @Validated CorsiExtraDTO payload,
            @AuthenticationPrincipal User admin,
            BindingResult validationResult
    ) {

        if (validationResult.hasErrors()) {
            throw new ValidationException(
                    validationResult.getFieldErrors()
                            .stream()
                            .map(err -> err.getDefaultMessage())
                            .toList()
            );
        }

        return corsiExtraService.addCorsoExtra(payload);
    }

    @GetMapping()
    public Page<CorsiExtraResponseDTO> getCorsiExtra(
            Pageable pageable
    ) {
        return corsiExtraService.getAll(pageable);
    }

    @GetMapping("/{idCorso}")
    @PreAuthorize("hasAnyRole('ADMIN','PROFESSORE','GENITORE','STUDENTE')")
    public CorsiExtraResponseDTO getById(@PathVariable UUID idCorso) {
        return corsiExtraService.getById(idCorso);
    }

    @PatchMapping("/{idCorso}/studenti/{idStudente}")
    @PreAuthorize("hasRole('GENITORE')")
    public CorsiExtraResponseDTO iscriviStudente(
            @PathVariable UUID idCorso,
            @PathVariable UUID idStudente
    ) {
        return corsiExtraService.iscriviStudente(idCorso, idStudente);
    }

    @DeleteMapping("/{idCorso}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCompito(
            @PathVariable UUID idCorso,
            @AuthenticationPrincipal User admin
    ) {
        corsiExtraService.deleteCorso(idCorso);
    }

    @DeleteMapping("/{idCorso}/studenti/{idStudente}")
    @PreAuthorize("hasRole('GENITORE')")
    public CorsiExtraResponseDTO rimuoviStudente(
            @PathVariable UUID idCorso,
            @PathVariable UUID idStudente
    ) {
        return corsiExtraService.rimuoviStudente(idCorso, idStudente);
    }
}
