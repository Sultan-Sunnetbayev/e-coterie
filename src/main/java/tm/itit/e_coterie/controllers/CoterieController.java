package tm.itit.e_coterie.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tm.itit.e_coterie.dtos.CoterieDTO;
import tm.itit.e_coterie.services.CoterieService;

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

}