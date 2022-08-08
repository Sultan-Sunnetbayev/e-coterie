package tm.itit.e_coterie.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tm.itit.e_coterie.dtos.PulpitDTO;
import tm.itit.e_coterie.models.Pulpit;
import tm.itit.e_coterie.services.PulpitService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/pulpit")
public class PulpitController {

    private final PulpitService pulpitService;

    @Autowired
    public PulpitController(PulpitService pulpitService) {
        this.pulpitService = pulpitService;
    }

    @GetMapping(path = "/get/all", produces = "application/json")
    public ResponseEntity getAllPulpit(){

        List<PulpitDTO> pulpitDTOS=pulpitService.getAllPulpitDTO();
        Map<String,Object> response=new HashMap<>();

        if(pulpitDTOS==null){

            pulpitDTOS=new ArrayList<>();
        }
        response.put("pulpits",pulpitDTOS);
        response.put("status",true);

        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/edit/pulpit/by/id", produces="application/json")
    public ResponseEntity editPulpitById(final @ModelAttribute Pulpit pulpit){

        Map<String,Object>response=new HashMap<>();

        if(!pulpitService.isPulpitExistsById(pulpit.getId())){

            response.put("message","error pulpit not found with this id");
            response.put("status",true);

            return ResponseEntity.ok(response);
        }
        pulpitService.editPulpitById(pulpit);
        if(pulpitService.isPulpitExists(pulpit)){

            response.put("message","accept pulpit successful edited");
            response.put("status",true);
        }else{

            response.put("message","error pulpit don't edited");
            response.put("status",true);
        }

        return ResponseEntity.ok(response);
    }

}
