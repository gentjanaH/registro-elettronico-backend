package gentjanahani.registro_elettronico_backend.controllers;

import gentjanahani.registro_elettronico_backend.entities.User;
import gentjanahani.registro_elettronico_backend.exceptions.ValidationException;
import gentjanahani.registro_elettronico_backend.payloads.request.CompitoDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.CompitoResponseDTO;
import gentjanahani.registro_elettronico_backend.sevices.CompitoService;
import jakarta.validation.Valid;
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
@RequestMapping("/compiti")
public class CompitoController {

    private final CompitoService compitoService;

    @Autowired
    public CompitoController(CompitoService compitoService) {
        this.compitoService = compitoService;
    }

    @PostMapping("/classi/{idClasse}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('PROFESSORE')")
    public CompitoResponseDTO addCompito(
            @RequestBody @Validated CompitoDTO payload,
            @AuthenticationPrincipal User prof,
            @PathVariable UUID idClasse,
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

        return compitoService.addCompito(payload, prof, idClasse);
    }


    @GetMapping("/{idCompito}")
    @PreAuthorize("hasAnyRole('PROFESSORE','GENITORE','STUDENTE')")
    public CompitoResponseDTO getCompitoById(@PathVariable UUID idCompito) {
        return compitoService.compitoById(idCompito);
    }


    @GetMapping("/classi/{idClasse}")
    @PreAuthorize("hasAnyRole('PROFESSORE','GENITORE','STUDENTE')")
    public Page<CompitoResponseDTO> getCompitiByClasse(
            @PathVariable UUID idClasse,
            Pageable pageable
    ) {
        return compitoService.getCompitiByClasse(idClasse, pageable);
    }


    public void deleteCompito(
            @PathVariable UUID idCompito,
            @AuthenticationPrincipal User prof
    ) {
        compitoService.deleteCompito(idCompito, prof);
    }
}
