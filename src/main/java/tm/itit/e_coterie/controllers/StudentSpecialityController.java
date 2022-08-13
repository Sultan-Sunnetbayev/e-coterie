package tm.itit.e_coterie.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tm.itit.e_coterie.dtos.StudentSpecialityDTO;
import tm.itit.e_coterie.models.Faculty;
import tm.itit.e_coterie.models.StudentSpeciality;
import tm.itit.e_coterie.services.FacultyService;
import tm.itit.e_coterie.services.StudentSpecialityService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/studentSpeciality")
public class StudentSpecialityController {

    private final StudentSpecialityService studentSpecialityService;
    private final FacultyService facultyService;

    @Autowired
    public StudentSpecialityController(StudentSpecialityService studentSpecialityService,
                                       FacultyService facultyService) {
        this.studentSpecialityService = studentSpecialityService;
        this.facultyService = facultyService;
    }

    @GetMapping(path = "/get/all",produces = "application/json")
    public ResponseEntity getAllStudentSpeciality(){

        Map<String,Object> response=new HashMap<>();
        List<StudentSpecialityDTO> studentSpecialityDTOS=studentSpecialityService.getAllStudentSpecialityDTOS();

        if(studentSpecialityDTOS==null){

            studentSpecialityDTOS=new ArrayList<>();
        }
        response.put("status",true);
        response.put("studentSpecialities",studentSpecialityDTOS);

        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/add",produces = "application/json")
    public ResponseEntity addStudentSpeciality(final @ModelAttribute StudentSpeciality studentSpeciality,
                                               final @RequestParam("facultyId")Integer facultyId){

        Map<String, Object>response=new HashMap<>();
        Faculty faculty =facultyService.getFacultyById(facultyId);
        if(faculty==null){

            response.put("status",false);
            response.put("message","error faculty not found with this id");

            return ResponseEntity.ok(response);
        }
        if(studentSpecialityService.isStudentSpecialityExists(studentSpeciality)){

            response.put("status",false);
            response.put("message","error this student speciality already exists");

            return ResponseEntity.ok(response);
        }
        studentSpecialityService.addStudentSpeciality(studentSpeciality, faculty);
        if(studentSpecialityService.isStudentSpecialityExists(studentSpeciality)){

            response.put("status",true);
            response.put("message","accept student speciality successful added");
        }else{

            response.put("status",false);
            response.put("message","error student speciality don't added");
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "remove/by/id",produces = "application/json")
    public ResponseEntity removeStudentSpecialityById(final @RequestParam("studentSpecialityId")Integer studentSpecialityId){

        Map<String,Object>response=new HashMap<>();

        if(!studentSpecialityService.isStudentSpecialityExistsById(studentSpecialityId)){

            response.put("status",false);
            response.put("message","error student speciality don't found with this id");

            return ResponseEntity.ok(response);
        }
        studentSpecialityService.removeStudentSpecialityById(studentSpecialityId);
        if(!studentSpecialityService.isStudentSpecialityExistsById(studentSpecialityId)){

            response.put("status",true);
            response.put("message","accept student speciality successful removed");
        }else{

            response.put("status",false);
            response.put("message","error student speciality don't removed");
        }

        return ResponseEntity.ok(response);
    }

}
