package tm.itit.e_coterie.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tm.itit.e_coterie.models.Faculty;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
}
