package tm.itit.e_coterie.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tm.itit.e_coterie.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT role FROM Role role WHERE role.name = :name")
    Role findRoleByName(@Param("name")String name);
}
