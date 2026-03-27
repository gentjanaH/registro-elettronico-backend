package gentjanahani.registro_elettronico_backend.controllers;

import gentjanahani.registro_elettronico_backend.payloads.response.GenitoreResponseDTO;
import gentjanahani.registro_elettronico_backend.services.GenitoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/genitori")
public class GenitoreController {

    private final GenitoreService genitoreService;

    @Autowired
    public GenitoreController(GenitoreService genitoreService) {
        this.genitoreService = genitoreService;
    }

    @PatchMapping("/{idGenitore}/figli/{idStudente}")
    @PreAuthorize("hasRole('ADMIN')")
    public GenitoreResponseDTO addFiglio(
            @PathVariable UUID idGenitore,
            @PathVariable UUID idStudente
    ) {
        return genitoreService.addFiglio(idGenitore, idStudente);
    }

    @DeleteMapping("/{idGenitore}/figli/{idStudente}")
    @PreAuthorize("hasRole('ADMIN')")
    public GenitoreResponseDTO removeFiglio(
            @PathVariable UUID idGenitore,
            @PathVariable UUID idStudente
    ) {
        return genitoreService.removeFiglio(idGenitore, idStudente);
    }
}
