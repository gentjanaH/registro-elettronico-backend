package gentjanahani.registro_elettronico_backend.controllers;

import gentjanahani.registro_elettronico_backend.entities.Materia;
import gentjanahani.registro_elettronico_backend.exceptions.ValidationException;
import gentjanahani.registro_elettronico_backend.payloads.request.MateriaDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.ProfessoreMateriaResponseDTO;
import gentjanahani.registro_elettronico_backend.services.MateriaService;
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
@RequestMapping("/materie")
public class MateriaController {

    private final MateriaService materiaService;

    @Autowired
    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    // http://localhost:8081/materie
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public MateriaDTO addMateria(@Validated @RequestBody MateriaDTO payload, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            List<String> errorList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorList);

        } else {
            Materia created = materiaService.addMateria(payload);
            return this.materiaService.toMateriaDTO(created);
        }

    }

    // http://localhost:8081/materie
    @GetMapping
    public Page<Materia> getAllMaterie(Pageable pageable) {
        return materiaService.getAllMaterie(pageable);

    }

    // http://localhost:8081/materie/{idMateria}
    @GetMapping("/{idMateria}")
    @PreAuthorize("hasAnyRole('ADMIN','PROFESSORE','GENITORE','STUDENTE')")
    public Materia getMateria(@PathVariable UUID idMateria) {
        return materiaService.getById(idMateria);

    }

    // http://localhost:8081/materie/{id}/professori
    // http://localhost:8081/materie/{id}
    @GetMapping("/{idMateria}/professori")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ProfessoreMateriaResponseDTO> getProfessoriByMateria(@PathVariable UUID idMateria) {

        return materiaService.getProfessoriByMateria(idMateria);
    }

    @DeleteMapping("/{idMateria}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteMateria(@PathVariable UUID idMateria) {
        this.materiaService.deleteMateria(idMateria);
    }
}
