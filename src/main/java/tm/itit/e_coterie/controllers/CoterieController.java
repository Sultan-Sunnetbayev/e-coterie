package tm.itit.e_coterie.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tm.itit.e_coterie.dtos.CoterieDTO;
import tm.itit.e_coterie.dtos.UserDTO;
import tm.itit.e_coterie.models.Coterie;
import tm.itit.e_coterie.models.Faculty;
import tm.itit.e_coterie.models.StudentSpeciality;
import tm.itit.e_coterie.services.CoterieService;
import tm.itit.e_coterie.services.FacultyService;
import tm.itit.e_coterie.services.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/coterie")
public class CoterieController {

    private final CoterieService coterieService;

    @Autowired
    public CoterieController(CoterieService coterieService) {
        this.coterieService = coterieService;
    }

    @GetMapping(path = "/get/all", produces="application/json")
    public ResponseEntity getAllCoterie(){

        List<CoterieDTO> coterieDTOS=coterieService.getAllCoterieDTO();
        Map<String,Object> response=new HashMap<>();

        response.put("status",true);
        if(coterieDTOS==null){

            coterieDTOS=new ArrayList<>();
        }
        response.put("coteries",coterieDTOS);

        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/add", produces = "application/json")
    public ResponseEntity addCoterie(final @ModelAttribute Coterie coterie,
                                     final @RequestParam(value = "image",required = false) MultipartFile image){

        Map<String,Object> response=new HashMap<>();

        if(coterieService.isCoterieExists(coterie.getName())){

            response.put("status",false);
            response.put("message","error this coterie already exists");

            return ResponseEntity.ok(response);
        }
        coterieService.addCoterie(coterie,image);
        if(coterieService.isCoterieExists(coterie.getName())){

            response.put("status",true);
            response.put("message","accept coterie successful added");
        }else{

            response.put("status",false);
            response.put("message","error coterie don't added");
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/remove/by/id", produces = "application/json")
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