package tm.itit.e_coterie.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tm.itit.e_coterie.models.Coterie;

@Repository
public interface CoterieRepository extends JpaRepository<Coterie, Integer> {

    @Query("SELECT coterie FROM Coterie coterie WHERE coterie.name = :name")
    Coterie findCoterieByName(@Param("name")String name);

    @Query("SELECT coterie FROM Coterie coterie WHERE coterie.id = :coterieId")
    Coterie findCoterieById(@Param("coterieId")Integer coterieId);

}
