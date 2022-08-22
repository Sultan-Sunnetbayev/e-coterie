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
import java.util.stream.Collectors;

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

        if(deanFacultyRepository.findDeanFacultyByUser_IdAndFaculty_Id(deanFaculty.getId(), faculty.getId())!=null){

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
    public boolean checkDeanFacultyByUserIdAndFacultyId(final int userId, final int facultyId){

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

    @Override
    public List<UserDTO>getAllDeanFacultyDTO(){

        return deanFacultyRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private UserDTO toDTO(DeanFaculty deanFaculty) {

        UserDTO userDTO= UserDTO.builder()
                .id(deanFaculty.getUser().getId())
                .name(deanFaculty.getUser().getName())
                .surname(deanFaculty.getUser().getSurname())
                .patronymicName(deanFaculty.getUser().getPatronymicName())
                .email(deanFaculty.getUser().getEmail())
                .role(deanFaculty.getUser().getRole().getName())
                .imagePath(deanFaculty.getUser().getImagePath())
                .build();

        return userDTO;
    }

}
