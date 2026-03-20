package gentjanahani.registro_elettronico_backend.services;

import gentjanahani.registro_elettronico_backend.entities.Genitore;
import gentjanahani.registro_elettronico_backend.entities.User;
import gentjanahani.registro_elettronico_backend.exceptions.NotFoundException;
import gentjanahani.registro_elettronico_backend.repositories.GenitoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GenitoreService {

    private final GenitoreRepository genitoreRepository;

    @Autowired
    public GenitoreService(GenitoreRepository genitoreRepository) {
        this.genitoreRepository = genitoreRepository;
    }

    public Genitore saveGenitore(Genitore g) {
        return genitoreRepository.save(g);
    }

    public Genitore findUser(User user) {
        Genitore g = genitoreRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException("User non trovato"));
        return g;
    }

    public Genitore findById(UUID id) {
        return genitoreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genitore non trovato"));
    }
}
