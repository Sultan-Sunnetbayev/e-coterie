package tm.itit.e_coterie.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tm.itit.e_coterie.models.StudentSpeciality;

@Repository
public interface StudentSpecialityRepository extends JpaRepository<StudentSpeciality, Integer> {

}