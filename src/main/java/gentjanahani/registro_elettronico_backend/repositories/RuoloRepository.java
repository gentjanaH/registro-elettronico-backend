package gentjanahani.registro_elettronico_backend.repositories;

import gentjanahani.registro_elettronico_backend.entities.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, UUID> {
    Optional<Ruolo> findByRuolo(String ruolo);

    boolean existsByRuolo(String ruolo);
}
