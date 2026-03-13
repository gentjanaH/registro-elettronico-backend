package gentjanahani.registro_elettronico_backend.controllers;

import gentjanahani.registro_elettronico_backend.entities.Classe;
import gentjanahani.registro_elettronico_backend.exceptions.ValidationException;
import gentjanahani.registro_elettronico_backend.payloads.request.ClasseDTO;
import gentjanahani.registro_elettronico_backend.payloads.response.ClasseResponseDTO;
import gentjanahani.registro_elettronico_backend.sevices.ClasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/classi")
public class ClasseController {

    private final ClasseService classeService;

    @Autowired
    public ClasseController(ClasseService classeService) {
        this.classeService = classeService;
    }

    //  http://localhost:8081/classi
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ClasseResponseDTO addClasse(@Validated @RequestBody ClasseDTO payload, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            List<String> errorList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorList);

        } else {

            Classe classe = classeService.addClasse(payload);

            return classeService.toDTO(classe);
        }
    }

    //  http://localhost:8081/classi
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSORE')")
    public Page<ClasseResponseDTO> getAll(Pageable pageable) {

        return classeService.getAll(pageable);
    }

    //  http://localhost:8081/classi/{idClasse}
    @GetMapping("/{idClasse}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSORE')")
    public ClasseResponseDTO getClasseById(@PathVariable UUID idClasse) {

        return classeService.getById(idClasse);
    }
}
