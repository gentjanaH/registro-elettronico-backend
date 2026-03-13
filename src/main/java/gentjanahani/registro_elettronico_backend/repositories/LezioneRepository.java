package gentjanahani.registro_elettronico_backend.repositories;

import gentjanahani.registro_elettronico_backend.entities.Lezione;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LezioneRepository extends JpaRepository<Lezione, UUID> {

    Optional<Lezione> findById(UUID id);

    Page<Lezione> findByMateriaId(UUID idMateria, Pageable pageable);


}
