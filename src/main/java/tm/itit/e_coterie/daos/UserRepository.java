package tm.itit.e_coterie.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tm.itit.e_coterie.models.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT user FROM User user WHERE user.email = :email")
    User findUserByEmail(@Param("email")String email);

    @Query("SELECT user FROM User user WHERE user.name = :name AND user.surname = :surname AND " +
            "user.patronymicName = :patronymicName")
    User findUserByNameAndSurnameAndPatronymicName(@Param("name")String name,
                                                   @Param("surname")String surname,
                                                   @Param("patronymicName")String patronymicName);

    @Query("SELECT user FROM User user WHERE user.name = :name AND user.surname = :surname AND user.email = :email")
    User findUserByNameAndSurnameAndEmail(@Param("name")String name,
                                          @Param("surname")String surname,
                                          @Param("email")String email);

    @Query("SELECT user FROM User user WHERE user.role.name = :name")
    List<User>findUsersByRole_Name(@Param("name")String name);

    @Query("SELECT user FROM User user WHERE user.id = :userId")
    User findUserById(@Param("userId")Integer userId);

}
