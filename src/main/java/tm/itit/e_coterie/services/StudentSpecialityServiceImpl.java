package tm.itit.e_coterie.services;

import org.springframework.stereotype.Service;
import tm.itit.e_coterie.daos.StudentSpecialityRepository;
import tm.itit.e_coterie.dtos.StudentSpecialityDTO;
import tm.itit.e_coterie.models.Faculty;
import tm.itit.e_coterie.models.StudentSpeciality;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentSpecialityServiceImpl implements StudentSpecialityService {

    private final StudentSpecialityRepository studentSpecialityRepository;

    public StudentSpecialityServiceImpl(StudentSpecialityRepository studentSpecialityRepository) {
        this.studentSpecialityRepository = studentSpecialityRepository;
    }

    @Override
    @Transactional
    public void addStudentSpeciality(final StudentSpeciality studentSpeciality, final Faculty faculty){

        if(studentSpeciality==null){

            return;
        }
        if(isFullNameStudentSpecialityExists(studentSpeciality.getFullName())){

            return;
        }
        if(isShortNameStudentSpecialityExists(studentSpeciality.getShortName())){

            return;
        }
        StudentSpeciality savedStudentSpeciality=StudentSpeciality.builder()
                .fullName(studentSpeciality.getFullName())
                .shortName(studentSpeciality.getShortName())
                .faculty(faculty)
                .build();

        studentSpecialityRepository.save(savedStudentSpeciality);

        return;
    }

    private boolean isShortNameStudentSpecialityExists(final String shortName) {

        if(studentSpecialityRepository.findStudentSpecialityByShortName(shortName)!=null){

            return true;
        }else{

            return false;
        }
    }

    private boolean isFullNameStudentSpecialityExists(final String fullName) {

        if(studentSpecialityRepository.findStudentSpecialityByFullName(fullName)!=null){

            return true;
        }else{

            return false;
        }
    }

    @Override
    public boolean isStudentSpecialityExists(final StudentSpeciality studentSpeciality){

        if(studentSpecialityRepository.findStudentSpecialityByFullNameAndShortName(studentSpeciality.getFullName(),
                                                                    studentSpeciality.getShortName())!=null){

            return true;
        }else{

            return false;
        }
    }

    @Override
    public StudentSpeciality getStudentSpecialityById(final Integer studentSpecialityId){

        StudentSpeciality studentSpeciality=studentSpecialityRepository.findStudentSpecialityById(studentSpecialityId);

        return studentSpeciality;
    }

    @Override
    public List<StudentSpecialityDTO>getAllStudentSpecialityDTOS(){

        return studentSpecialityRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private StudentSpecialityDTO toDTO(final StudentSpeciality studentSpeciality){

        StudentSpecialityDTO studentSpecialityDTO=StudentSpecialityDTO.builder()
                .id(studentSpeciality.getId())
                .fullName(studentSpeciality.getFullName())
                .shortName(studentSpeciality.getShortName())
                .build();

        return studentSpecialityDTO;
    }

    @Override
    public boolean isStudentSpecialityExistsById(final Integer studentSpecialityId){

        if(studentSpecialityRepository.findStudentSpecialityById(studentSpecialityId)!=null){

            return true;
        }else{

            return false;
        }
    }

    @Override
    public void removeStudentSpecialityById(final Integer studentSpecialityId){

        if(studentSpecialityRepository.findStudentSpecialityById(studentSpecialityId)!=null){

            studentSpecialityRepository.deleteById(studentSpecialityId);
        }

        return;
    }

}
