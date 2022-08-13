package tm.itit.e_coterie.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tm.itit.e_coterie.daos.CoterieRepository;
import tm.itit.e_coterie.dtos.CoterieDTO;
import tm.itit.e_coterie.helper.FileUploadUtil;
import tm.itit.e_coterie.models.Coterie;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CoterieServiceImpl implements CoterieService{

    private final CoterieRepository coterieRepository;

    @Value("${coterie.image.path}")
    private String imagePath;
    @Value("${default.image.name}")
    private String defaultImageName;

    @Autowired
    public CoterieServiceImpl(CoterieRepository coterieRepository) {
        this.coterieRepository = coterieRepository;
    }

    @Override
    @Transactional
    public void addCoterie(final Coterie coterie, final MultipartFile image){

        Coterie savedCoterie=Coterie.builder()
                .name(coterie.getName())
//                .pulpit(pulpit)
                .build();
        if(image!=null && !image.isEmpty()){

            String fileName= UUID.randomUUID().toString()+"_"+image.getOriginalFilename();
            try {

                FileUploadUtil.save(imagePath, fileName, image);
                savedCoterie.setImagePath(imagePath+"/"+fileName);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }else{

            String uuid=UUID.randomUUID().toString();

            try {

                FileUploadUtil.saveDefaultImage(imagePath, defaultImageName, uuid);
                savedCoterie.setImagePath(imagePath+"/" + uuid + "_" + defaultImageName);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        coterieRepository.save(savedCoterie);

        return;
    }

    @Override
    public boolean isCoterieExists(final String name){

        if(coterieRepository.findCoterieByName(name)!=null){

            return true;
        }else{

            return false;
        }
    }

    @Override
    @Transactional
    public void editCoterieById(final Coterie coterie, final MultipartFile image){

        if(coterie.getId()==0){

            return;
        }
        Coterie editedCoterie=coterieRepository.findCoterieById(coterie.getId());

        if(editedCoterie==null){

            return;
        }
        Coterie check=coterieRepository.findCoterieByName(coterie.getName());

        if(check!=null && check.getId()!=coterie.getId()){

            return;
        }
        if(coterie.getName()!=null && !coterie.getName().isEmpty()){

            editedCoterie.setName(coterie.getName());
        }
        if(image!=null && !image.isEmpty()){

            File file=new File(editedCoterie.getImagePath());

            if(file.exists()){

                file.delete();
            }
            String fileName=UUID.randomUUID().toString()+"_"+image.getOriginalFilename();

            try {

                FileUploadUtil.save(imagePath, fileName, image);
                editedCoterie.setImagePath(imagePath+"/"+fileName);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        coterieRepository.save(editedCoterie);

        return;
    }

    @Override
    @Transactional
    public void removeCoterieById(final Integer coterieId){

        Coterie coterie=coterieRepository.findCoterieById(coterieId);

        if(coterie!=null){

            if(coterie.getImagePath()!=null && !coterie.getImagePath().isEmpty()){

                File image=new File(coterie.getImagePath());

                if(image.exists()){

                    image.delete();
                }
            }
            coterieRepository.deleteById(coterieId);
        }

        return;
    }

    @Override
    public List<CoterieDTO> getAllCoterieDTO(){

        return coterieRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

    }

    private CoterieDTO toDTO(Coterie coterie) {

        CoterieDTO coterieDTO=CoterieDTO.builder()
                .id(coterie.getId())
                .name(coterie.getName())
                .imagePath(coterie.getImagePath())
                .build();

        return coterieDTO;
    }

    @Override
    public boolean isCoterieExistsById(final Integer coterieId){

        if(coterieRepository.findCoterieById(coterieId)!=null){

            return true;
        }else{

            return false;
        }
    }

    @Override
    public Coterie getCoterieById(final Integer coterieId){

        Coterie coterie=coterieRepository.findCoterieById(coterieId);

        return coterie;
    }
    
}
