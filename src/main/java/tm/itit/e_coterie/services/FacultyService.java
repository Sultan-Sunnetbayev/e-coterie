package tm.itit.e_coterie.services;

import tm.itit.e_coterie.dtos.FacultyDTO;
import tm.itit.e_coterie.models.Faculty;

import javax.transaction.Transactional;
import java.util.List;

public interface FacultyService {

    @Transactional
    void addFaculty(Faculty faculty);

    boolean isFacultyExists(Faculty faculty);

    @Transactional
    void editFacultyById(Faculty faculty);

    List<FacultyDTO> getAllFacultyDTO();

    @Transactional
    void removeFacultyById(int facultyId);

    boolean isFacultyExistsById(int facultyId);

    Faculty getFacultyById(int facultyId);

}
