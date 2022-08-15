package tm.itit.e_coterie.services;

import tm.itit.e_coterie.dtos.UserDTO;
import tm.itit.e_coterie.models.Coterie;
import tm.itit.e_coterie.models.User;

import javax.transaction.Transactional;
import java.util.List;

public interface TeacherService {

    @Transactional
    void addTeacher(User user, Coterie coterie);

    boolean isTeacherExistsByUserIdAndCoterieId(int userId, int coterieId);

    @Transactional
    void removeTeacherById(int teacherId);

    List<UserDTO> getAllTeacherDTO(List<Integer> coterieId);
}
