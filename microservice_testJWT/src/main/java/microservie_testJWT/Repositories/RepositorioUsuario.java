package microservie_testJWT.Repositories;

import microservie_testJWT.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByUsername(String username);
}
