package gentjanahani.registro_elettronico_backend.services;

import gentjanahani.registro_elettronico_backend.entities.Ruolo;
import gentjanahani.registro_elettronico_backend.exceptions.BadRequestException;
import gentjanahani.registro_elettronico_backend.exceptions.NotFoundException;
import gentjanahani.registro_elettronico_backend.payloads.request.RuoloDTO;
import gentjanahani.registro_elettronico_backend.repositories.RuoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RuoloService {

    private final RuoloRepository ruoloRepository;

    @Autowired
    public RuoloService(RuoloRepository ruoloRepository) {
        this.ruoloRepository = ruoloRepository;
    }

    public RuoloDTO toRuoloDTO(Ruolo ruolo) {
        return new RuoloDTO(ruolo.getRuolo());
    }


    //metodo per creare un nuovo ruolo
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

    //Metodo per trovare un ruolo dato l'id
    public Ruolo findById(UUID id) {
        return ruoloRepository.findById(id).orElseThrow(() -> new NotFoundException("Ruolo non trovato"));
    }

    //Metodo per trovare un ruolo data una stringa
    public Ruolo findByRuolo(String ruolo) {
        return ruoloRepository.findByRuolo(ruolo)
                .orElseThrow(() -> new NotFoundException("Ruolo non trovato"));
    }


    public boolean existsByRuolo(String ruolo) {
        return ruoloRepository.existsByRuolo(ruolo);
    }

    //metodo per visualizzare tutti i ruoli
    public Page<Ruolo> getAllRuoli(Pageable pageable) {
        return ruoloRepository.findAll(pageable);
    }


    //metodo per aggiornare un ruolo
    public Ruolo udateRuolo(UUID id, RuoloDTO payload) {
        Ruolo ruolo = findById(id);

        if (!ruolo.getRuolo().equals(payload.ruolo()) &&
                ruoloRepository.existsByRuolo(payload.ruolo())) {
            throw new BadRequestException("Ruolo " + payload.ruolo() + " gia presente.");
        }
        ruolo.setRuolo(payload.ruolo());
        return ruoloRepository.save(ruolo);
    }

    //metodo per eliminare un ruolo
    public void deleteRuolo(UUID idRuolo) {
        Ruolo ruolo = findById(idRuolo);
        ruoloRepository.delete(ruolo);
        System.out.println("Ruolo " + ruolo + " eliminato correttamente");
    }

}
