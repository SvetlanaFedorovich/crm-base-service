package mvpproject.crmbaseservice.repository;

import mvpproject.crmbaseservice.model.entity.SalesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDate;

@Repository
public interface SalesRepository extends JpaRepository<SalesEntity, Long> {
    List<SalesEntity> findBySalesDateAfter(LocalDate start);
}