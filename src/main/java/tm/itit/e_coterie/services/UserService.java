package tm.itit.e_coterie.services;

import org.springframework.web.multipart.MultipartFile;
import tm.itit.e_coterie.dtos.UserDTO;
import tm.itit.e_coterie.models.User;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {

    @Transactional
    void addUser(User user, MultipartFile image, String roleName);

    boolean isUserExists(User user);

    User getUserByEmail(String email);

    boolean isUserExistsById(int userId);

    @Transactional
    void removeUserById(int userId);

    @Transactional
    void editUserById(int userId, UserDTO userDTO, MultipartFile image);
}
