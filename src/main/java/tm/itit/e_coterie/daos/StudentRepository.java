package tm.itit.e_coterie.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tm.itit.e_coterie.models.Coterie;
import tm.itit.e_coterie.models.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("SELECT student FROM Student student WHERE student.user.id = :userId")
    Student findStudentByUser_Id(@Param("userId")int userId);

    @Query("SELECT student FROM Student student WHERE student.user.id = :userId AND " +
            "student.studentSpeciality.id = :studentSpecialityId")
    Student findStudentsByUser_IdAndStudentSpeciality_Id(@Param("userId")int userId,
                                                         @Param("studentSpecialityId")int studentSpecialityId);

    @Query("SELECT student FROM Student student WHERE student.id = :studentId")
    Student findStudentById(@Param("studentId")int studentId);

    @Query("SELECT student FROM Student student WHERE student.studentSpeciality.id = :studentSpecialityId")
    List<Student>findStudentsByStudentSpeciality_Id(@Param("studentSpecialityId")int studentSpecialityId);

}
