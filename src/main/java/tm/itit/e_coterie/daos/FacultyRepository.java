package tm.itit.e_coterie.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tm.itit.e_coterie.models.Faculty;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

    @Query("SELECT faculty FROM Faculty faculty WHERE faculty.fullName = :fullName " +
            "AND faculty.shortName = :shortName")
    Faculty findFacultyByFullNameAndShortName(@Param("fullName")String fullName,
                                              @Param("shortName")String shortName);

    @Query("SELECT faculty FROM Faculty faculty WHERE faculty.fullName = :fullName")
    Faculty findFacultyByFullName(@Param("fullName")String fullName);

    @Query("SELECT faculty FROM Faculty faculty WHERE faculty.shortName = :shortName")
    Faculty findFacultyByShortName(@Param("shortName")String shortName);

    @Query("SELECT faculty FROM Faculty faculty WHERE faculty.id = :id")
    Faculty findFacultyById(@Param("id")Integer id);

}
