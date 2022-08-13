//package tm.itit.e_coterie.daos;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import tm.itit.e_coterie.models.GovernorPulpit;
//import tm.itit.e_coterie.models.Pulpit;
//
//@Repository
//public interface GovernorPulpitRepository extends JpaRepository<GovernorPulpit, Integer> {
//
//    @Query("SELECT governorPulpit FROM GovernorPulpit governorPulpit WHERE governorPulpit.user.id = :userId " +
//            "AND governorPulpit.pulpit.id = :pulpitId")
//    GovernorPulpit findGovernorPulpitByUser_IdAndPulpit_Id(@Param("userId")Integer userId,
//                                                           @Param("pulpitId")Integer pulpitId);
//
//}
