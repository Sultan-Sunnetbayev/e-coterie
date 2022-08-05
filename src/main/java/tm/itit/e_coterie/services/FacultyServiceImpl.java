package tm.itit.e_coterie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tm.itit.e_coterie.daos.FacultyRepository;
import tm.itit.e_coterie.dtos.FacultyDTO;
import tm.itit.e_coterie.models.Faculty;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService{

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    @Transactional
    public void addFaculty(final Faculty faculty){

        if(fullNameFacultyExists(faculty.getFullName())){

            return;
        }
        if(shortNameFacultyExists(faculty.getShortName())){

            return;
        }
        Faculty savedFaculty=Faculty.builder()
                .fullName(faculty.getFullName())
                .shortName(faculty.getShortName())
                .build();
        facultyRepository.save(savedFaculty);

        return;
    }

    private boolean shortNameFacultyExists(String shortName) {

        Faculty check = facultyRepository.findFacultyByShortName(shortName);

        if(check == null){

            return false;
        }else{

            return true;
        }
    }

    private boolean fullNameFacultyExists(String fullName) {

        Faculty check=facultyRepository.findFacultyByFullName(fullName);

        if(check == null){

            return false;
        }else{

            return true;
        }
    }

    @Override
    public boolean isFacultyExists(final Faculty faculty){

        Faculty check=facultyRepository.findFacultyByFullNameAndShortName(faculty.getFullName(), faculty.getShortName());

        if(check==null){

            return false;
        }else{

            return true;
        }
    }

    @Override
    @Transactional
    public void editFacultyById(final Faculty faculty){

        Faculty check=facultyRepository.findFacultyByFullName(faculty.getFullName());

        if(check!=null && check.getId()!=faculty.getId()){

            return;
        }
        check=facultyRepository.findFacultyByShortName(faculty.getShortName());
        if(check!=null && check.getId()!=faculty.getId()){

            return;
        }
        Faculty editedFaculty=facultyRepository.findFacultyById(faculty.getId());

        if(editedFaculty==null){

            return;
        }
        if(faculty.getFullName()!=null && !faculty.getFullName().isEmpty()){

            editedFaculty.setFullName(faculty.getFullName());
        }
        if(faculty.getShortName()!=null && !faculty.getShortName().isEmpty()){

            editedFaculty.setShortName(faculty.getShortName());
        }
        facultyRepository.save(editedFaculty);

        return;
    }

    @Override
    public List<FacultyDTO>getAllFacultyDTO(){

        return facultyRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private FacultyDTO toDTO(Faculty faculty) {

        FacultyDTO facultyDTO=FacultyDTO.builder()
                .id(faculty.getId())
                .fullName(faculty.getFullName())
                .shortName(faculty.getShortName())
                .build();

        return facultyDTO;
    }

    @Override
    @Transactional
    public void removeFacultyById(final Integer facultyId){

        if(facultyRepository.findFacultyById(facultyId)!=null){

            facultyRepository.deleteById(facultyId);
        }

        return;
    }

    @Override
    public boolean isFacultyExistsById(final Integer facultyId){

        if(facultyRepository.findFacultyById(facultyId)!=null){

            return true;
        }else{

            return false;
        }
    }

    @Override
    public Faculty getFacultyById(final Integer facultyId){

        Faculty faculty=facultyRepository.findFacultyById(facultyId);

        return faculty;
    }

}
