package gentjanahani.registro_elettronico_backend.sevices;

import gentjanahani.registro_elettronico_backend.entities.Genitore;
import gentjanahani.registro_elettronico_backend.repositories.GenitoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenitoreService {

    private final GenitoreRepository genitoreRepository;

    @Autowired
    public GenitoreService(GenitoreRepository genitoreRepository) {
        this.genitoreRepository = genitoreRepository;
    }

    public Genitore save(Genitore g) {
        return genitoreRepository.save(g);
    }
}
