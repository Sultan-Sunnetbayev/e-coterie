package tm.itit.e_coterie.services;

import tm.itit.e_coterie.models.Pulpit;
import tm.itit.e_coterie.models.User;

import javax.transaction.Transactional;

public interface GovernorPulpitService {

    @Transactional
    void addGovernorPulpit(User governorPulpit, Pulpit pulpit);

    boolean isGovernorPulpitExists(Integer governorPulpitId, Integer pulpitId);
}
