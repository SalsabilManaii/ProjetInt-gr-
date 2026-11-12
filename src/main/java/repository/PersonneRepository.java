package repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import entity.Personne;

public interface PersonneRepository
        extends JpaRepository<Personne, Integer> {

    Optional<Personne> findByEmail(String email);
}