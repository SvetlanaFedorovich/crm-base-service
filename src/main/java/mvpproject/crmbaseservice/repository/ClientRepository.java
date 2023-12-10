package mvpproject.crmbaseservice.repository;

import mvpproject.crmbaseservice.model.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
}
