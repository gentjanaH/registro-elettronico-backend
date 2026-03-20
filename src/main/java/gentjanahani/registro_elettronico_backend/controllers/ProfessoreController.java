package gentjanahani.registro_elettronico_backend.controllers;

import gentjanahani.registro_elettronico_backend.entities.Professore;
import gentjanahani.registro_elettronico_backend.payloads.response.ProfessoreResponseDTO;
import gentjanahani.registro_elettronico_backend.services.ProfessoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/professori")
public class ProfessoreController {

    private final ProfessoreService professoreService;

    @Autowired
    public ProfessoreController(ProfessoreService professoreService) {
        this.professoreService = professoreService;
    }

    //    GET tutti i professori (ADMIN)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<ProfessoreResponseDTO> getAll(Pageable pageable) {


        return professoreService.getAll(pageable);
    }


    //    GET un professore tramite id (ADMIN)
    @GetMapping("/{idProfessore}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProfessoreResponseDTO getProfById(@PathVariable UUID idProfessore) {
        Professore prof = professoreService.findById(idProfessore);

        return professoreService.toDTO(prof);
    }

    //    PATCH addMaterie ad un professore
    @PostMapping("/{idProfessore}/materia/{idMateria}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProfessoreResponseDTO addMateria(@PathVariable UUID idProfessore, @PathVariable UUID idMateria) {

        Professore updated = professoreService.addMateriaToProf(idProfessore, idMateria);
        return professoreService.toDTO(updated);
    }

    //    POST adClasse a professore
    @PostMapping("/{idProfessore}/classe/{idClasse}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProfessoreResponseDTO addClasse(
            @PathVariable UUID idProfessore,
            @PathVariable UUID idClasse
    ) {
        Professore updated = professoreService.addClasseToProf(idProfessore, idClasse);
        return professoreService.toDTO(updated);
    }

}
