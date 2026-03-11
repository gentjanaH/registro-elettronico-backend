package gentjanahani.registro_elettronico_backend.sevices;

import gentjanahani.registro_elettronico_backend.entities.Ruolo;
import gentjanahani.registro_elettronico_backend.exceptions.BadRequestException;
import gentjanahani.registro_elettronico_backend.exceptions.NotFoundException;
import gentjanahani.registro_elettronico_backend.payloads.request.RuoloDTO;
import gentjanahani.registro_elettronico_backend.repositories.RuoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuoloService {

    private final RuoloRepository ruoloRepository;

    @Autowired
    public RuoloService(RuoloRepository ruoloRepository) {
        this.ruoloRepository = ruoloRepository;
    }

    public Ruolo addRuolo(RuoloDTO payload) {
        // Verifica che il ruolo non esista già
        if (ruoloRepository.existsByRuolo(payload.ruolo())) {
            throw new BadRequestException("Ruolo '" + payload.ruolo() + "' già esiste");
        }

        Ruolo newRuolo = new Ruolo(payload.ruolo());
        Ruolo saved = this.ruoloRepository.save(newRuolo);
        System.out.println("Ruolo creato: " + saved.getRuolo());
        return saved;
    }


    public Ruolo findByRuolo(String ruolo) {
        return ruoloRepository.findByRuolo(ruolo)
                .orElseThrow(() -> new NotFoundException("Ruolo non trovato"));
    }


    public boolean existsByRuolo(String ruolo) {
        return ruoloRepository.existsByRuolo(ruolo);
    }
}
