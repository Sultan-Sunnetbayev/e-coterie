package tm.itit.e_coterie.services;

import org.springframework.web.multipart.MultipartFile;
import tm.itit.e_coterie.models.User;

import javax.transaction.Transactional;

public interface UserService {

    @Transactional
    void addUser(User user, MultipartFile image, String roleName);

    boolean isUserExists(User user);

    User getUserByEmail(String email);
}
