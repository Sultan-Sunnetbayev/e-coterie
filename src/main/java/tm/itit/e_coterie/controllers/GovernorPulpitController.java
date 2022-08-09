package tm.itit.e_coterie.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tm.itit.e_coterie.models.Coterie;
import tm.itit.e_coterie.models.Pulpit;
import tm.itit.e_coterie.services.CoterieService;
import tm.itit.e_coterie.services.PulpitService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/governor/pulpit")
public class GovernorPulpitController {

    private final CoterieService coterieService;
    private final PulpitService pulpitService;

    @Autowired
    public GovernorPulpitController(CoterieService coterieService, PulpitService pulpitService) {
        this.coterieService = coterieService;
        this.pulpitService = pulpitService;
    }

    @PostMapping(path = "/add/coterie", produces = "application/json")
    public ResponseEntity addCoterie(final @ModelAttribute Coterie coterie,
                                     final @RequestParam(value = "iamge",required = false) MultipartFile image,
                                     final @RequestParam("pulpitId")Integer pulpitId){

        Map<String,Object> response=new HashMap<>();

        if(coterieService.isCoterieExists(coterie.getName())){

            response.put("status",false);
            response.put("message","error this coterie already exists");

            return ResponseEntity.ok(response);
        }
        Pulpit pulpit=pulpitService.getPulpitById(pulpitId);

        if(pulpit==null){

            response.put("status",false);
            response.put("message","error pulpit not found with this id");

            return ResponseEntity.ok(response);
        }
        coterieService.addCoterie(coterie,pulpit,image);
        if(coterieService.isCoterieExists(coterie.getName())){

            response.put("status",true);
            response.put("message","accept coterie successful added");
        }else{

            response.put("status",false);
            response.put("message","error coterie don't added");
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "remove/coterie/by/id", produces = "application/json")
    public ResponseEntity removeCoterieById(final @RequestParam("coterieId")Integer coterieId){

        Map<String,Object>response=new HashMap<>();

        if(!coterieService.isCoterieExistsById(coterieId)){

            response.put("status",false);
            response.put("message","error coterie not found with this id");

            return ResponseEntity.ok(response);
        }
        coterieService.removeCoterieById(coterieId);
        if(coterieService.isCoterieExistsById(coterieId)){

            response.put("status",false);
            response.put("message","error coterie don't removed");
        }else{

            response.put("status",true);
            response.put("message","accept coterie successful removed");
        }

        return ResponseEntity.ok(response);
    }

}
