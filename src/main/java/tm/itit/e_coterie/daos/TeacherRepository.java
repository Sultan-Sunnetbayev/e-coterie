package tm.itit.e_coterie.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tm.itit.e_coterie.models.Teacher;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    @Query("SELECT teacher FROM Teacher teacher WHERE teacher.user.id = :userId")
    Teacher findTeacherByUser_Id(@Param("userId")int userId);

    @Query("SELECT teacher FROM Teacher teacher WHERE teacher.id = :teacherId")
    Teacher findTeacherById(@Param("teacherId")int teacherId);

    @Query("SELECT teacher FROM Teacher teacher INNER JOIN teacher.coteries coteries ON coteries.id IN :coterieIds")
    List<Teacher> findTeachersByCoterieIds(@Param("coterieIds")List<Integer> coterieIds);

}
