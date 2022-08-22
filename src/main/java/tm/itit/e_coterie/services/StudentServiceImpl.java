package tm.itit.e_coterie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tm.itit.e_coterie.daos.StudentRepository;
import tm.itit.e_coterie.models.*;

import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

//    @Override
//    public List<UserDTO> getStudentsByParameter(final String fullName, final Coterie coterie,
//                                                final Integer studentSpecialityId,
//                                                final Boolean hostel, final Gender gender){
//        List<Student>students;
//        if(coterie==null){
//
//            return new ArrayList<>();
//        }else{
//            students=studentRepository.findStudentsByCoteries(new ArrayList<Coterie>(
//                    Arrays.asList(coterie)));
//        }
////        if(fullName!=null && !fullName.isEmpty()){
////            students
////        }
//        List<UserDTO>userDTOS=new ArrayList<>();
//
//        if(students!=null){
//
//            students.forEach(
//                    student -> {
//                        userDTOS.add(UserDTO.builder()
//                                .name(student.getUser().getName())
//                                .surname(student.getUser().getSurname())
//                                .patronymicName(student.getUser().getPatronymicName())
//                                .gender(student.getUser().getGender())
//                                .imagePath(student.getUser().getImagePath())
//                                .role(student.getUser().getRole().getName())
//                                .build()
//                        );
//                    }
//            );
//        }
//
//
//        if(userDTOS==null){
//
//            userDTOS=new ArrayList<>();
//        }
//
//        return userDTOS;
//    }

    @Override
    @Transactional
    public void addStudent(final User user, final Coterie coterie, final StudentSpeciality studentSpeciality,
                           final Boolean hostel, final Gender gender){

        Student student=studentRepository.findStudentByUser_Id(user.getId());
        List<Coterie> coteries = null;

        if(student==null) {

            coteries=new ArrayList<>();
            coteries.add(coterie);
            student = Student.builder()
                    .user(user)
                    .studentSpeciality(studentSpeciality)
                    .coteries(coteries)
                    .gender(gender)
                    .hostel(hostel)
                    .build();
        }else{
            coteries=student.getCoteries();
            if(coterie==null){

                coteries=new ArrayList<>();
            }
            coteries.add(coterie);
            student.setCoteries(coteries);
        }

        studentRepository.save(student);

        return;
    }

    @Override
    public boolean isStudentExists(final Integer userId, final Integer studentSpecialityId, final Integer coterieId){

        Student student=studentRepository.findStudentsByUser_IdAndStudentSpeciality_Id(userId,studentSpecialityId);

        if(student==null) {

            return false;
        }
        List<Coterie>coteries=student.getCoteries();

        if(coteries==null){

            return false;
        }
        for(Coterie coterie:coteries){

            if(coterie.getId()==coterieId){

                return true;
            }
        }
            return false;
    }

    @Override
    public boolean isStudentExistsById(final Integer studentId){

        if(studentRepository.findStudentById(studentId)!=null){

            return true;
        }else{

            return false;
        }
    }

    @Override
    public void removeStudentById(final Integer studentId){

        Student student=studentRepository.findStudentById(studentId);

        if(student==null){

            return;
        }
        if(student.getUser().getImagePath()!=null && !student.getUser().getImagePath().isEmpty()) {

            File image = new File(student.getUser().getImagePath());

            if(image.exists()){

                image.delete();
            }
            studentRepository.deleteById(studentId);
        }

        return;
    }

    @Override
    public List<Student>getStudentsByStudentSpecialityId(final int studentSpecialityId){

        List<Student>students=studentRepository.findStudentsByStudentSpeciality_Id(studentSpecialityId);

        return students;
    }

}
