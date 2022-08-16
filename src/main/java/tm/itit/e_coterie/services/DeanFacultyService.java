package tm.itit.e_coterie.services;

import tm.itit.e_coterie.dtos.UserDTO;
import tm.itit.e_coterie.models.DeanFaculty;
import tm.itit.e_coterie.models.Faculty;
import tm.itit.e_coterie.models.User;

import javax.transaction.Transactional;
import java.util.List;

public interface DeanFacultyService {

    @Transactional
    void addDeanFaculty(User deanFaculty, Faculty faculty);

    boolean checkDeanFacultyByUserIdAndFacultyId(int userId, int facultyId);

    List<DeanFaculty> getDeanFacultiesByFacultyId(int facultyId);

    List<UserDTO>getAllDeanFacultyDTO();
}
