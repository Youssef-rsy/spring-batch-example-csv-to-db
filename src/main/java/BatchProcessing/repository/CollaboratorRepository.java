package BatchProcessing.repository;

import BatchProcessing.models.Collabotor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collabotor,Long> {
}
