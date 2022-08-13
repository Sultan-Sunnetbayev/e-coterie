//package tm.itit.e_coterie.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import tm.itit.e_coterie.daos.GovernorPulpitRepository;
//import tm.itit.e_coterie.models.GovernorPulpit;
//import tm.itit.e_coterie.models.Pulpit;
//import tm.itit.e_coterie.models.User;
//
//import javax.transaction.Transactional;
//
//@Service
//public class GovernorServiceImpl implements GovernorPulpitService{
//
//    private final GovernorPulpitRepository governorPulpitRepository;
//
//    @Autowired
//    public GovernorServiceImpl(GovernorPulpitRepository governorPulpitRepository) {
//        this.governorPulpitRepository = governorPulpitRepository;
//    }
//
//    @Override
//    @Transactional
//    public void addGovernorPulpit(final User governorPulpit, final Pulpit pulpit){
//
//        if(governorPulpitRepository.findGovernorPulpitByUser_IdAndPulpit_Id(governorPulpit.getId(),pulpit.getId())!=null){
//
//            return;
//        }
//        GovernorPulpit savedGovernorPulpit=GovernorPulpit.builder()
//                .user(governorPulpit)
//                .pulpit(pulpit)
//                .build();
//
//        governorPulpitRepository.save(savedGovernorPulpit);
//
//        return;
//    }
//
//    @Override
//    public boolean isGovernorPulpitExists(final Integer governorPulpitId, final Integer pulpitId){
//
//        if(governorPulpitRepository.findGovernorPulpitByUser_IdAndPulpit_Id(governorPulpitId, pulpitId)!=null){
//
//            return true;
//        }else{
//
//            return false;
//        }
//    }
//
//}
