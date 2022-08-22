package tm.itit.e_coterie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tm.itit.e_coterie.daos.RoleRepository;
import tm.itit.e_coterie.daos.UserRepository;
import tm.itit.e_coterie.dtos.UserDTO;
import tm.itit.e_coterie.helper.FileUploadUtil;
import tm.itit.e_coterie.models.Role;
import tm.itit.e_coterie.models.User;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Value("${user.image.path}")
    private String imagePathUser;
    @Value("${default.image.name}")
    private String defaultNameImageUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void addUser(final User user, final MultipartFile image, final String roleName){

        if(isUserEmailExists(user.getEmail())){

            return;
        }
        if(user.getPatronymicName()!=null && !user.getPatronymicName().isEmpty()) {

            if (isUserNameSurnamePatronymicNameExists(user.getName(), user.getSurname(), user.getPatronymicName())){

                return;
            }
        }
        final Role role=roleRepository.findRoleByName(roleName);

        if(role==null){

            return;
        }
        if(Objects.equals(role.getName(), "ROLE_RECTOR")) {

            List<User> rectors = userRepository.findUsersByRole_Name(role.getName());

            if (rectors != null && !rectors.isEmpty()) {

                return;
            }
        }else if(Objects.equals(role.getName(), "ROLE_PRORECTOR")){

            List<User>prorectors=userRepository.findUsersByRole_Name("ROLE_PRORECTOR");

            if(prorectors!=null && prorectors.size()>1){

                return;
            }
        }
        User savedUser=User.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .patronymicName(user.getPatronymicName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(role)
                .build();
        userRepository.save(savedUser);
        if(image!=null && !image.isEmpty()){

            try {
                final String uuid= UUID.randomUUID().toString();

                FileUploadUtil.save(imagePathUser, uuid+image.getOriginalFilename(), image);
                savedUser.setImagePath(imagePathUser+"/"+uuid+image.getOriginalFilename());
                userRepository.save(savedUser);

            } catch (IOException exception) {

                exception.printStackTrace();

                return;
            }
        }else{

            try {

                final String uuid=UUID.randomUUID().toString();
                FileUploadUtil.saveDefaultImage(imagePathUser,defaultNameImageUser,uuid);
                savedUser.setImagePath(imagePathUser+"/"+uuid+"_"+defaultNameImageUser);
                userRepository.save(savedUser);

            } catch (IOException exception) {

                exception.printStackTrace();

                return;
            }
        }

        return;
    }

    private boolean isUserNameSurnamePatronymicNameExists(final String name, final String surname, final String patronymicName) {

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

    @Override
    public User getUserByEmail(final String email){

        User user=userRepository.findUserByEmail(email);

        return user;
    }

    @Override
    public boolean isUserExistsById(final int userId){

        if(userRepository.findUserById(userId)!=null){

            return true;
        }else{

            return false;
        }
    }

    @Override
    @Transactional
    public void removeUserById(final int userId){

        User user=userRepository.findUserById(userId);

        if(user!=null && !Objects.equals(user.getRole().getName(),"ROLE_ADMIN")){

            if(user.getImagePath()!=null && !user.getImagePath().isEmpty()) {

                File file = new File(user.getImagePath());

                if(file.exists()){

                    file.delete();
                }
            }
            userRepository.deleteById(userId);
        }

        return;
    }

    @Override
    @Transactional
    public void editUserById(final int userId, final UserDTO userDTO, final MultipartFile image){

        if(userDTO==null){

            return;
        }
        User editedUser=userRepository.findUserById(userId);

        if (editedUser == null) {

            return;
        }
        if(userDTO.getEmail()!=null && !userDTO.getEmail().isEmpty()) {

            User check = userRepository.findUserByEmail(userDTO.getEmail());
            if (check != null && check.getId() != editedUser.getId()) {

                return;
            }
        }
        if(userDTO.getName()!=null && !userDTO.getName().isEmpty()){

            editedUser.setName(userDTO.getName());
        }
        if(userDTO.getSurname()!=null && !userDTO.getSurname().isEmpty()){

            editedUser.setSurname(userDTO.getSurname());
        }
        if(userDTO.getPatronymicName()!=null && !userDTO.getPatronymicName().isEmpty()){

            editedUser.setPatronymicName(userDTO.getPatronymicName());
        }
        if(userDTO.getEmail()!=null && !userDTO.getEmail().isEmpty()){

            editedUser.setEmail(userDTO.getEmail());
        }
        if(userDTO.getPassword()!=null && !userDTO.getPassword().isEmpty()){

            editedUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        if(image!=null && !image.isEmpty()){

            String fileName=UUID.randomUUID().toString()+"_"+image.getOriginalFilename();

            try {

                FileUploadUtil.save(imagePathUser, fileName, image);
                File file=new File(editedUser.getImagePath());

                if(file.exists()){

                    file.delete();
                }
                editedUser.setImagePath(imagePathUser+"/"+fileName);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        userRepository.save(editedUser);

        return;
    }

}
