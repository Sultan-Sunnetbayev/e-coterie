package tm.itit.e_coterie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tm.itit.e_coterie.daos.RoleRepository;
import tm.itit.e_coterie.daos.UserRepository;
import tm.itit.e_coterie.helper.FileUploadUtil;
import tm.itit.e_coterie.models.Role;
import tm.itit.e_coterie.models.User;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Value("${user.image.path}")
    private String imagePath;
    @Value("${default.image.name}")
    private String defaultImageNameUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void addRector(final User user, final MultipartFile image){

        if(isUserEmailExists(user.getEmail())){

            return;
        }
        if(user.getPatronymicName()!=null && !user.getPatronymicName().isEmpty()) {

            if (isUserNameSurnamePatronymicNameExists(user.getName(), user.getSurname(), user.getPatronymicName())){

                return;
            }
        }
        Role role=roleRepository.findRoleByName("ROLE_RECTOR");

        if(role==null){

            return;
        }
        User savedUser=User.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .patronymicName(user.getPatronymicName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(role)
                .gender(user.getGender())
                .build();
        userRepository.save(savedUser);
        if(image!=null && !image.isEmpty()){

            try {
                String uuid= UUID.randomUUID().toString();

                FileUploadUtil.save(imagePath, uuid+image.getOriginalFilename(), image);
                savedUser.setImagePath(imagePath+"/"+uuid+image.getOriginalFilename());
                userRepository.save(savedUser);

            } catch (IOException exception) {

                exception.printStackTrace();

                return;
            }
        }else{

            try {

                String uuid=UUID.randomUUID().toString();
                FileUploadUtil.saveDefaultImageUser(imagePath,defaultImageNameUser,uuid);
                savedUser.setImagePath(imagePath+"/"+uuid+"_"+defaultImageNameUser);
                userRepository.save(savedUser);

            } catch (IOException exception) {

                exception.printStackTrace();
                return;
            }
        }
    }

    private boolean isUserNameSurnamePatronymicNameExists(String name, String surname, String patronymicName) {

        if(userRepository.findUserByNameAndSurnameAndPatronymicName(name, surname, patronymicName)!=null){

            return true;
        }else{

            return false;
        }
    }

    private boolean isUserEmailExists(final String email){

        if(userRepository.findUserByEmail(email)!=null){

            return true;
        }else{

            return false;
        }
    }

    @Override
    public boolean isUserExists(final User user){

        if(userRepository.findUserByNameAndSurnameAndEmail(user.getName(), user.getSurname(), user.getEmail())!=null){

            return true;
        }else{

            return false;
        }
    }
}
