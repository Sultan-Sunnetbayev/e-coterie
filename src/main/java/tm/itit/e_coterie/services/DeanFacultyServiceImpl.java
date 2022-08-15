package tm.itit.e_coterie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tm.itit.e_coterie.daos.DeanFacultyRepository;
import tm.itit.e_coterie.dtos.UserDTO;
import tm.itit.e_coterie.models.DeanFaculty;
import tm.itit.e_coterie.models.Faculty;
import tm.itit.e_coterie.models.User;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DeanFacultyServiceImpl implements DeanFacultyService{

    private final DeanFacultyRepository deanFacultyRepository;

    @Autowired
    public DeanFacultyServiceImpl(DeanFacultyRepository deanFacultyRepository) {
        this.deanFacultyRepository = deanFacultyRepository;
    }

    @Override
    @Transactional
    public void addDeanFaculty(final User deanFaculty, final Faculty faculty){

        if(deanFacultyRepository.
                findDeanFacultyByUser_EmailAndUser_NameAndUser_SurnameAndFaculty_FullNameAndFaculty_ShortName(
                        deanFaculty.getEmail(), deanFaculty.getName(), deanFaculty.getSurname(),
                        faculty.getFullName(), faculty.getShortName()
                )!=null){

            return;
        }
        DeanFaculty savedDeanFaculty=DeanFaculty.builder()
                .user(deanFaculty)
                .faculty(faculty)
                .build();
        deanFacultyRepository.save(savedDeanFaculty);

        return;
    }

    @Override
    public boolean checkDeanFacultyByUserIdAndFacultyId(final Integer userId, final Integer facultyId){

        if(deanFacultyRepository.findDeanFacultyByUser_IdAndFaculty_Id(userId, facultyId)!=null){

            return true;
        }else{

            return false;
        }
    }

    @Override
    public List<DeanFaculty>getDeanFacultiesByFacultyId(final int facultyId){

        List<DeanFaculty>deanFaculties=deanFacultyRepository.findDeanFacultiesByFaculty_Id(facultyId);

        return deanFaculties;
    }

}
