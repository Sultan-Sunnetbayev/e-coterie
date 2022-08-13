//package tm.itit.e_coterie.daos;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import tm.itit.e_coterie.models.Pulpit;
//
//@Repository
//public interface PulpitRepository extends JpaRepository<Pulpit, Integer> {
//
//    @Query("SELECT pulpit FROM Pulpit pulpit WHERE pulpit.fullName = :fullName AND pulpit.shortName = :shortName")
//    Pulpit findPulpitByFullNameAndShortName(@Param("fullName")String fullName,
//                                            @Param("shortName")String shortName);
//
//    @Query("SELECT pulpit FROM Pulpit pulpit WHERE pulpit.fullName = :fullName")
//    Pulpit findPulpitByFullName(@Param("fullName")String fullName);
//
//    @Query("SELECT pulpit FROM Pulpit pulpit WHERE pulpit.shortName = :shortName")
//    Pulpit findPulpitByShortName(@Param("shortName")String shortName);
//
//    @Query("SELECT pulpit FROM Pulpit pulpit WHERE pulpit.id = :id")
//    Pulpit findPulpitById(@Param("id")Integer id);
//
//}
