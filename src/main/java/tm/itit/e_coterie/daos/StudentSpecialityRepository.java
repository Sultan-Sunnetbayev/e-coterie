package tm.itit.e_coterie.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tm.itit.e_coterie.models.StudentSpeciality;

import java.util.List;

@Repository
public interface StudentSpecialityRepository extends JpaRepository<StudentSpeciality, Integer> {

    @Query("SELECT studentSpeciality FROM StudentSpeciality studentSpeciality WHERE " +
            "studentSpeciality.fullName = :fullName AND studentSpeciality.shortName = :shortName")
    StudentSpeciality findStudentSpecialityByFullNameAndShortName(@Param("fullName")String fullName,
                                                                    @Param("shortName")String shortName);

    @Query("SELECT studentSpeciality FROM StudentSpeciality studentSpeciality WHERE " +
            "studentSpeciality.fullName = :fullName")
    StudentSpeciality findStudentSpecialityByFullName(@Param("fullName")String fullName);

    @Query("SELECT studentSpeciality FROM StudentSpeciality studentSpeciality WHERE " +
            "studentSpeciality.shortName = :shortName")
    StudentSpeciality findStudentSpecialityByShortName(@Param("shortName")String shortName);

    @Query("SELECT studentSpeciality FROM StudentSpeciality studentSpeciality WHERE " +
            "studentSpeciality.id = :id")
    StudentSpeciality findStudentSpecialityById(@Param("id")Integer id);

    @Query("SELECT studentSpeciality FROM StudentSpeciality studentSpeciality WHERE studentSpeciality.faculty.id " +
            "= :facultyId")
    List<StudentSpeciality> findStudentSpecialitiesByFaculty_Id(@Param("facultyId")int facultyId);

}
