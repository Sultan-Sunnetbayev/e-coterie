package tm.itit.e_coterie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tm.itit.e_coterie.daos.TeacherRepository;
import tm.itit.e_coterie.dtos.UserDTO;
import tm.itit.e_coterie.models.Coterie;
import tm.itit.e_coterie.models.Teacher;
import tm.itit.e_coterie.models.User;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService{

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    @Transactional
    public void addTeacher(final User user, final Coterie coterie){

        Teacher savedTeacher=teacherRepository.findTeacherByUser_Id(user.getId());

        if(savedTeacher==null){

            List<Coterie>coteries=new ArrayList<>();

            coteries.add(coterie);
            savedTeacher=Teacher.builder()
                    .user(user)
                    .coteries(coteries)
                    .build();
        }else{

            List<Coterie>coteries=savedTeacher.getCoteries();

            coteries.add(coterie);
            savedTeacher.setCoteries(coteries);
        }

        teacherRepository.save(savedTeacher);
    }

    @Override
    public boolean isTeacherExistsByUserIdAndCoterieId(final int userId, final int coterieId) {

        Teacher teacher=teacherRepository.findTeacherByUser_IdAndCoteries(userId,coterieId);

        if(teacher==null){

            return false;
        }else{

            return true;
        }
    }

    @Override
    @Transactional
    public void removeTeacherById(final int teacherId){

        if(teacherRepository.findTeacherById(teacherId)!=null){

            teacherRepository.deleteById(teacherId);
        }

        return;
    }

    @Override
    public List<UserDTO>getAllTeacherDTO(final List<Integer> coterieId){

        if(coterieId==null) {

            return teacherRepository.findAll().stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        }else{

            return teacherRepository.findTeachersByCoterieIds(coterieId).stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        }

    }

    private UserDTO toDTO(Teacher teacher) {

        UserDTO teacherDTO= UserDTO.builder()
                .id(teacher.getId())
                .name(teacher.getUser().getName())
                .surname(teacher.getUser().getSurname())
                .patronymicName(teacher.getUser().getPatronymicName())
                .imagePath(teacher.getUser().getImagePath())
                .email(teacher.getUser().getEmail())
                .role(teacher.getUser().getRole().getName())
                .build();

        return teacherDTO;
    }

}
