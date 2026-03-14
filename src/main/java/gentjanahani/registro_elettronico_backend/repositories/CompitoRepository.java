package gentjanahani.registro_elettronico_backend.repositories;

import gentjanahani.registro_elettronico_backend.entities.Compito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompitoRepository extends JpaRepository<Compito, UUID> {

    Optional<Compito> findById(UUID idCompito);

    Page<Compito> findByClasse_IdClasse(UUID idClasse, Pageable pageable);

}
