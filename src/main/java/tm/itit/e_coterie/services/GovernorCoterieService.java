package tm.itit.e_coterie.services;

import tm.itit.e_coterie.dtos.UserDTO;
import tm.itit.e_coterie.models.Coterie;
import tm.itit.e_coterie.models.User;

import javax.transaction.Transactional;
import java.util.List;

public interface GovernorCoterieService {

    @Transactional
    void addGovernorCoterie(User user, Coterie coterie);

    List<UserDTO> getAllGovernorCotetrieDTOS(List<Integer> governorCoterieIds);

    boolean isGovernorCoterieExistsByUserIdAndCoterieId(int userId, int coterieId);
}
