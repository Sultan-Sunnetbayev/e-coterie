package tm.itit.e_coterie.services;


import tm.itit.e_coterie.models.Coterie;
import tm.itit.e_coterie.models.Student;
import tm.itit.e_coterie.models.StudentSpeciality;
import tm.itit.e_coterie.models.User;

import javax.transaction.Transactional;

public interface StudentService {

    @Transactional
    void addStudent(User user, Coterie coterie, StudentSpeciality studentSpeciality, Boolean hostel);

    boolean isStudentExists(Integer userId, Integer studentSpecialityId, Integer coterieId);

    boolean isStudentExistsById(Integer studentId);

    void removeStudentById(Integer studentId);
}
