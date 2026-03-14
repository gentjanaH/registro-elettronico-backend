package gentjanahani.registro_elettronico_backend.repositories;

import gentjanahani.registro_elettronico_backend.entities.Presenza;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PresenzaRepository extends JpaRepository<Presenza, UUID> {

    @Query("SELECT p FROM Presenza p WHERE p.studente.id = :idStudente AND (p.stato= 'ASSENTE' OR p.stato = 'GIUSTIFICATO')")
    Page<Presenza> findAssenzeByIdStudente(@Param("idStudente") UUID idStudente, Pageable pageable);

    Optional<Presenza> findById(UUID idPresenza);
}
