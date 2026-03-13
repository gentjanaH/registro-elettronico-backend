package gentjanahani.registro_elettronico_backend.sevices;

import gentjanahani.registro_elettronico_backend.entities.Lezione;
import gentjanahani.registro_elettronico_backend.exceptions.NotFoundException;
import gentjanahani.registro_elettronico_backend.repositories.LezioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LezioneService {
    private final LezioneRepository lezioneRepository;

    @Autowired
    public LezioneService(LezioneRepository lezioneRepository) {
        this.lezioneRepository = lezioneRepository;
    }

    public Optional<Lezione> findLezioneById(UUID id) {
        Lezione found = this.lezioneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lezione non trovata."));
        return Optional.ofNullable(found);
    }


}

