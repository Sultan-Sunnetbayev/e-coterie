package tm.itit.e_coterie.services;

import tm.itit.e_coterie.models.DeanFaculty;
import tm.itit.e_coterie.models.Faculty;
import tm.itit.e_coterie.models.User;

import javax.transaction.Transactional;
import java.util.List;

public interface DeanFacultyService {

    @Transactional
    void addDeanFaculty(User deanFaculty, Faculty faculty);

    boolean checkDeanFacultyByUserIdAndFacultyId(Integer userId, Integer facultyId);

    List<DeanFaculty> getDeanFacultiesByFacultyId(int facultyId);
}
