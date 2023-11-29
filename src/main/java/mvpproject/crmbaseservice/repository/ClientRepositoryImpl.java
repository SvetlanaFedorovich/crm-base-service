package mvpproject.crmbaseservice.repository;

import mvpproject.crmbaseservice.entity.Client;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

    @Override
    public <S extends Client> S save(S entity) {
        return null;
    }

    public String create(Client client) {
        return client.toString();
    }

    @Override
    public <S extends Client> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Client> findById(Long aLong) {
        return Optional.empty();
    }

    public String updateById(Long id, String newBankDetails) {
        return "Client by id " + id + "is updated: " + newBankDetails;
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Client> findAll() {
        return null;
    }

    public String readAll() {
        return "List of clients is empty";
    }

    @Override
    public Iterable<Client> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {
        System.out.println("The client by id is deleted.");
    }

    @Override
    public void delete(Client entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Client> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
