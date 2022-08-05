package tm.itit.e_coterie.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tm.itit.e_coterie.dtos.FacultyDTO;
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
}
