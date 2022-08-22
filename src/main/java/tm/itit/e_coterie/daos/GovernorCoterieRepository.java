package tm.itit.e_coterie.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tm.itit.e_coterie.models.GovernorCoterie;

import java.util.List;

@Repository
public interface GovernorCoterieRepository extends JpaRepository<GovernorCoterie, Integer> {

    @Query("SELECT governorCoterie FROM GovernorCoterie governorCoterie WHERE governorCoterie.user.id = :userId AND " +
            "governorCoterie.coterie.id = :coterieId")
    GovernorCoterie findGovernorCoterieByUser_IdAndCoterie_Id(@Param("userId")int userId,
                                                              @Param("coterieId")int coterieId);

    @Query("SELECT governorCoterie FROM GovernorCoterie governorCoterie WHERE governorCoterie.coterie.id IN :coterieIds")
    List<GovernorCoterie>findGovernorCoteriesByCoterie_Id(@Param("coterieIds")List<Integer>coterieIds);
}
