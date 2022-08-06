package tm.itit.e_coterie.services;

import tm.itit.e_coterie.dtos.PulpitDTO;
import tm.itit.e_coterie.models.Pulpit;

import javax.transaction.Transactional;
import java.util.List;

public interface PulpitService {
    @Transactional
    void addPulpit(Pulpit pulpit);

    @Transactional
    void editPulpitById(Pulpit pulpit);

    @Transactional
    boolean removePulpitById(Integer pulpitId);

    List<PulpitDTO> getAllPulpitDTO();

    PulpitDTO getPulpitDTOById(Integer pulpitId);

    boolean isPulpitExists(Pulpit pulpit);

    Pulpit getPulpitById(Integer pulpitId);

    boolean isPulpitExistsById(Integer pulpitId);
}
