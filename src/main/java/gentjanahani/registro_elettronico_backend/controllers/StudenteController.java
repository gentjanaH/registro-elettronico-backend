package gentjanahani.registro_elettronico_backend.controllers;

import gentjanahani.registro_elettronico_backend.entities.Presenza;
import gentjanahani.registro_elettronico_backend.entities.Studente;
import gentjanahani.registro_elettronico_backend.entities.Valutazione;
import gentjanahani.registro_elettronico_backend.sevices.PresenzaService;
import gentjanahani.registro_elettronico_backend.sevices.StudenteService;
import gentjanahani.registro_elettronico_backend.sevices.ValutazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/studenti")
public class StudenteController {
    private final StudenteService studenteService;
    private final PresenzaService presenzaService;
    private final ValutazioneService valutazioneService;

    @Autowired
    public StudenteController(StudenteService studenteService, PresenzaService presenzaService, ValutazioneService valutazioneService) {
        this.studenteService = studenteService;
        this.presenzaService = presenzaService;
        this.valutazioneService = valutazioneService;
    }

    //    questa classe conterrà i seguenti endopoint:
//    GET tutti gli studenti (ADMIN)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSORE')")
    public Page<Studente> getAllStudenti(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "cognome") String order
    ) {
        return this.studenteService.findAll(page, size, order);


    }

    //    GET uno studente tramite id (ADMIN-PROFESSORE-GENITORE)
    @GetMapping("/{idStudente}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSORE', 'GENITORE')")
    public Studente getStudente(@PathVariable UUID idStudente) {
        return this.studenteService.findById(idStudente);
    }

    //    GET le assenze di uno studente (ADMIN-PROFESSORE-GENITORE-STUDENTE)
    @GetMapping("/{idStudente}/assenze")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSORE', 'GENITORE', 'STUDENTE')")
    public Page<Presenza> getAssenzeStudente(@PathVariable UUID idStudente, @RequestParam Pageable pageable) {

        return presenzaService.getAssenzeByStudente(idStudente, pageable);

    }

    //    GET i voti di uno studente (ADMIN-PROFESSORE-GENITORE-STUDENTE)
    @GetMapping("/{idStudente}/voti")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSORE', 'GENITORE', 'STUDENTE')")
    public Page<Valutazione> getValutazioniStudente(@PathVariable UUID idStudente, @RequestParam Pageable pageable) {

        return valutazioneService.getValutazioniStudente(idStudente, pageable);

    }


//    POST le essenze di uno studente (ADMIN-PROFESSORE)
//    PATCH l' essenza di uno studente (ADMIN-PROFESSORE)
//    DELETE l' essenza di uno studente (ADMIN-PROFESSORE)
}
