package tm.itit.e_coterie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tm.itit.e_coterie.daos.PulpitRepository;
import tm.itit.e_coterie.dtos.PulpitDTO;
import tm.itit.e_coterie.models.Pulpit;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PulpitServiceImpl implements PulpitService{

    private final PulpitRepository pulpitRepository;

    @Autowired
    public PulpitServiceImpl(PulpitRepository pulpitRepository) {
        this.pulpitRepository = pulpitRepository;
    }

    @Override
    @Transactional
    public void addPulpit(final Pulpit pulpit){

        if(isFullNamePulpitExists(pulpit.getFullName())){

            return;
        }
        if(isShortNamePulpitExists(pulpit.getShortName())){

            return;
        }
        Pulpit savedPulpit=Pulpit.builder()
                .shortName(pulpit.getShortName())
                .fullName(pulpit.getFullName())
                .build();

        pulpitRepository.save(savedPulpit);

        return;
    }

    private boolean isShortNamePulpitExists(String shortName) {

        if(pulpitRepository.findPulpitByShortName(shortName)!=null){

            return true;
        }else{

            return false;
        }
    }

    private boolean isFullNamePulpitExists(String fullName) {

        if(pulpitRepository.findPulpitByFullName(fullName)!=null){

            return true;
        }else{

            return false;
        }
    }

    @Override
    @Transactional
    public void editPulpitById(final Pulpit pulpit){

        Pulpit check=pulpitRepository.findPulpitByFullName(pulpit.getFullName());

        if(check!=null && check.getId()!=pulpit.getId()){

            return;
        }
        check=pulpitRepository.findPulpitByShortName(pulpit.getShortName());
        if(check!=null && check.getId()!=pulpit.getId()){

            return;
        }
        Pulpit editedPulpit=pulpitRepository.findPulpitById(pulpit.getId());

        if(editedPulpit==null){

            return;
        }
        if(pulpit.getFullName()!=null && !pulpit.getFullName().isEmpty()){

            editedPulpit.setFullName(pulpit.getFullName());
        }
        if(pulpit.getShortName()!=null && !pulpit.getShortName().isEmpty()){

            editedPulpit.setFullName(pulpit.getFullName());
        }
        pulpitRepository.save(editedPulpit);

        return;
    }

    @Override
    @Transactional
    public void removePulpitById(final Integer pulpitId){

        if(pulpitRepository.findPulpitById(pulpitId)!=null){

            pulpitRepository.deleteById(pulpitId);
        }

        return;
    }

    @Override
    public List<PulpitDTO> getAllPulpitDTO(){

        return pulpitRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private PulpitDTO toDTO(Pulpit pulpit) {

        PulpitDTO pulpitDTO=PulpitDTO.builder()
                .id(pulpit.getId())
                .fullName(pulpit.getFullName())
                .shortName(pulpit.getShortName())
                .build();

        return pulpitDTO;
    }

    @Override
    public PulpitDTO getPulpitDTOById(final Integer pulpitId){

        Pulpit pulpit=pulpitRepository.findPulpitById(pulpitId);

        if(pulpit!=null){

            return toDTO(pulpit);
        }else{

            return null;
        }
    }

    @Override
    public boolean isPulpitExists(final Pulpit pulpit){

        if(pulpitRepository.findPulpitByFullNameAndShortName(pulpit.getFullName(), pulpit.getShortName())!=null){

            return true;
        }else{

            return false;
        }
    }

    @Override
    public Pulpit getPulpitById(final Integer pulpitId){

        Pulpit pulpit=pulpitRepository.findPulpitById(pulpitId);

        return pulpit;
    }

    @Override
    public boolean isPulpitExistsById(final Integer pulpitId){

        if(pulpitRepository.findPulpitById(pulpitId)!=null){

            return true;
        }else{

            return false;
        }
    }

}
