package tm.itit.e_coterie.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tm.itit.e_coterie.models.DeanFaculty;

import java.util.List;

@Repository
public interface DeanFacultyRepository extends JpaRepository<DeanFaculty, Integer> {

    @Query("SELECT deanFaculty FROM DeanFaculty deanFaculty WHERE deanFaculty.user.email = :email " +
            "AND deanFaculty.user.name = :name AND deanFaculty.user.surname = :surname AND " +
            "deanFaculty.faculty.fullName = :fullName AND deanFaculty.faculty.shortName = :shortName")
    DeanFaculty findDeanFacultyByUser_EmailAndUser_NameAndUser_SurnameAndFaculty_FullNameAndFaculty_ShortName(
            @Param("email")String email, @Param("name")String name, @Param("surname")String surname,
            @Param("fullName")String fullName, @Param("shortName")String shortName
    );

    @Query("SELECT deanFaculty FROM DeanFaculty deanFaculty WHERE deanFaculty.user.id = :userId" +
            " AND deanFaculty.faculty.id = :facultyId")
    DeanFaculty findDeanFacultyByUser_IdAndFaculty_Id(@Param("userId")int userId,
                                                      @Param("facultyId")int facultyId);

    @Query("SELECT deanFaculty FROM DeanFaculty deanFaculty WHERE deanFaculty.faculty.id = :facultyId")
    List<DeanFaculty> findDeanFacultiesByFaculty_Id(@Param("facultyId")int facultyId);

}
