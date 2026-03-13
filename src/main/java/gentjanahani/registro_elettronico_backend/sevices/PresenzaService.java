package gentjanahani.registro_elettronico_backend.sevices;

import gentjanahani.registro_elettronico_backend.entities.Presenza;
import gentjanahani.registro_elettronico_backend.repositories.PresenzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PresenzaService {

    private final PresenzaRepository presenzaRepository;

    @Autowired
    public PresenzaService(PresenzaRepository presenzaRepository) {
        this.presenzaRepository = presenzaRepository;
    }

    public Page<Presenza> getAssenzeByStudente(UUID idStudente, Pageable pageable) {

        return presenzaRepository.findAssenzeByIdStudente(idStudente, pageable);
    }


}
