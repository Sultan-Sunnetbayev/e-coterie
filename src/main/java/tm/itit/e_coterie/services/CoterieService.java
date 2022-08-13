package tm.itit.e_coterie.services;

import org.springframework.web.multipart.MultipartFile;
import tm.itit.e_coterie.dtos.CoterieDTO;
import tm.itit.e_coterie.models.Coterie;

import javax.transaction.Transactional;
import java.util.List;

public interface CoterieService {

    @Transactional
    void addCoterie(Coterie coterie, MultipartFile image);

    boolean isCoterieExists(String name);

    @Transactional
    void editCoterieById(Coterie coterie, MultipartFile image);

    @Transactional
    void removeCoterieById(Integer coterieId);

    List<CoterieDTO> getAllCoterieDTO();

    boolean isCoterieExistsById(Integer coterieId);

    Coterie getCoterieById(Integer coterieId);
}
