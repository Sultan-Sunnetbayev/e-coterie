package tm.itit.e_coterie.services;

import tm.itit.e_coterie.dtos.StudentSpecialityDTO;
import tm.itit.e_coterie.models.Faculty;
import tm.itit.e_coterie.models.StudentSpeciality;

import javax.transaction.Transactional;
import java.util.List;

public interface StudentSpecialityService {

    @Transactional
    void addStudentSpeciality(StudentSpeciality studentSpeciality, Faculty faculty);

    boolean isStudentSpecialityExists(StudentSpeciality studentSpeciality);

    StudentSpeciality getStudentSpecialityById(Integer studentSpecialityId);

    List<StudentSpecialityDTO> getAllStudentSpecialityDTOS();

    boolean isStudentSpecialityExistsById(Integer studentSpecialityId);

    void removeStudentSpecialityById(Integer studentSpecialityId);

    List<StudentSpecialityDTO>getStudentSpecialityDTOByFacultyId(int facultyId);
}
