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

    StudentSpeciality getStudentSpecialityById(int studentSpecialityId);

    List<StudentSpecialityDTO> getAllStudentSpecialityDTOS();

    boolean isStudentSpecialityExistsById(int studentSpecialityId);

    void removeStudentSpecialityById(int studentSpecialityId);

    List<StudentSpecialityDTO>getStudentSpecialityDTOByFacultyId(int facultyId);

    void editStudentSpecialityById(StudentSpeciality studentSpeciality);
}
