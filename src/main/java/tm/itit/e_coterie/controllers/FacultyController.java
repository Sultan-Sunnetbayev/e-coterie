package tm.itit.e_coterie.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tm.itit.e_coterie.dtos.FacultyDTO;
import tm.itit.e_coterie.models.Faculty;
import tm.itit.e_coterie.services.FacultyService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    @Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping(path = "/get/all", produces = "application/json")
    public ResponseEntity getAllFaculty(){

        List<FacultyDTO> facultyDTOS=facultyService.getAllFacultyDTO();
        Map<String,Object>response=new HashMap<>();

        if(facultyDTOS==null) {

            facultyDTOS = new ArrayList<>();
        }
        response.put("status",true);
        response.put("faculty",facultyDTOS);

        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/edit/faculty/by/id", produces = "application/json")
    public ResponseEntity editFacultyById(final @ModelAttribute Faculty faculty){

        Map<String,Object>response=new HashMap<>();

        if(!facultyService.isFacultyExistsById(faculty.getId())){

            response.put("status",false);
            response.put("message","error faculty don't found with this id");

            return ResponseEntity.ok(response);
        }
        facultyService.editFacultyById(faculty);

        if(facultyService.isFacultyExists(faculty)){

            response.put("status",true);
            response.put("message","faculty successful edited");
        }else{

            response.put("status",false);
            response.put("message","error faculty don't edited");
        }

        return ResponseEntity.ok(response);
    }

}
