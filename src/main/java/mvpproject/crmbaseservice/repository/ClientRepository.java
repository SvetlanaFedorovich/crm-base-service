package mvpproject.crmbaseservice.repository;

import mvpproject.crmbaseservice.entity.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
