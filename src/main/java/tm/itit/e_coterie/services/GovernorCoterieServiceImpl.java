package tm.itit.e_coterie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tm.itit.e_coterie.daos.GovernorCoterieRepository;
import tm.itit.e_coterie.dtos.UserDTO;
import tm.itit.e_coterie.models.Coterie;
import tm.itit.e_coterie.models.GovernorCoterie;
import tm.itit.e_coterie.models.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GovernorCoterieServiceImpl implements GovernorCoterieService{

    private final GovernorCoterieRepository governorCoterieRepository;

    @Autowired
    public GovernorCoterieServiceImpl(GovernorCoterieRepository governorCoterieRepository) {
        this.governorCoterieRepository = governorCoterieRepository;
    }

    @Override
    @Transactional
    public void addGovernorCoterie(final User user, final Coterie coterie){

        if(governorCoterieRepository.findGovernorCoterieByUser_IdAndCoterie_Id(user.getId(), coterie.getId())!=null){

            return;
        }
        GovernorCoterie governorCoterie= GovernorCoterie.builder()
                .user(user)
                .coterie(coterie)
                .build();
        governorCoterieRepository.save(governorCoterie);

        return;
    }

    @Override
    public List<UserDTO> getAllGovernorCotetrieDTOS(List<Integer> coterieIds){

        if(coterieIds==null || coterieIds.isEmpty()) {
            return governorCoterieRepository.findAll().stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        }else{

            return governorCoterieRepository.findGovernorCoteriesByCoterie_Id(coterieIds).stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
        }
    }

    private UserDTO toDTO(GovernorCoterie governorCoterie) {

        UserDTO userDTO=UserDTO.builder()
                .id(governorCoterie.getUser().getId())
                .name(governorCoterie.getUser().getName())
                .surname(governorCoterie.getUser().getSurname())
                .patronymicName(governorCoterie.getUser().getPatronymicName())
                .imagePath(governorCoterie.getUser().getImagePath())
                .email(governorCoterie.getUser().getEmail())
                .role(governorCoterie.getUser().getRole().getName())
                .build();

        return userDTO;
    }

    @Override
    public boolean isGovernorCoterieExistsByUserIdAndCoterieId(final int userId, final int coterieId){

        if(governorCoterieRepository.findGovernorCoterieByUser_IdAndCoterie_Id(userId, coterieId)!=null){

            return true;
        }else{

            return false;
        }
    }

}
