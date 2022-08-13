package tm.itit.e_coterie.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tm.itit.e_coterie.models.Coterie;
import tm.itit.e_coterie.models.StudentSpeciality;
import tm.itit.e_coterie.models.User;
import tm.itit.e_coterie.services.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    private final StudentService studentService;
    private final CoterieService coterieService;
    private final FacultyService facultyService;
    private final UserService userService;
    private final StudentSpecialityService studentSpecialityService;

    @Autowired
    public StudentController(StudentService studentService, CoterieService coterieService,
                             FacultyService facultyService, UserService userService,
                             StudentSpecialityService studentSpecialityService) {
        this.studentService = studentService;
        this.coterieService = coterieService;
        this.facultyService = facultyService;
        this.userService = userService;
        this.studentSpecialityService = studentSpecialityService;
    }

    @GetMapping(path = "/get/students",produces = "application/json")
    public ResponseEntity getStudentsByParameter(final @RequestParam("coterieId")Integer coterieId){

        return null;
    }

    @PostMapping(path = "/add",produces = "application/json")
    public ResponseEntity addStudent(final @ModelAttribute User user,
                                     final @RequestParam(value = "image",required = false)MultipartFile image,
                                     final @RequestParam("coterieId")Integer coterieId,
                                     final @RequestParam(value = "hostel",required = false)Boolean hostel,
                                     final @RequestParam("studentSpecialityId")Integer studentSpecialityId){

        Map<String,Object> response=new HashMap<>();

        final StudentSpeciality studentSpeciality=studentSpecialityService.getStudentSpecialityById(studentSpecialityId);

        if(studentSpeciality==null){

            response.put("status",false);
            response.put("message","error student stpeciality not found with this id");

            return ResponseEntity.ok(response);
        }
        final Coterie coterie=coterieService.getCoterieById(coterieId);

        if(coterie==null){

            response.put("status",false);
            response.put("message","error coterie not found with this id");

            return ResponseEntity.ok(response);
        }
        if(userService.isUserExists(user)) {

            final String role = "ROLE_STUDENT";

            userService.addUser(user, image, role);
        }
        if(!userService.isUserExists(user)){

            response.put("status",false);
            response.put("message","error user don't added");

            return ResponseEntity.ok(response);
        }
        User student=userService.getUserByEmail(user.getEmail());
        if(!studentService.isStudentExists(student.getId(),studentSpecialityId,coterie.getId())) {

            studentService.addStudent(student, coterie, studentSpeciality, hostel);
        }else{

            response.put("status",false);
            response.put("message","error this student's mail already exists");

            return ResponseEntity.ok(response);
        }
        if(studentService.isStudentExists(student.getId(),studentSpecialityId,coterie.getId())){

            response.put("status",true);
            response.put("message","accept student successful added");
        }else{

            response.put("status",false);
            response.put("message","error student don't added");
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/remove/by/id", produces = "application/json")
    public ResponseEntity removeStudentById(final @RequestParam("studentId")Integer studentId){

        Map<String,Object>response=new HashMap<>();

        if(studentService.isStudentExistsById(studentId)){

            studentService.removeStudentById(studentId);
        }else{

            response.put("status",false);
            response.put("message","error student not found with this id");

            return ResponseEntity.ok(response);
        }
        if(!studentService.isStudentExistsById(studentId)){

            response.put("status",true);
            response.put("message","accept student successful removed");
        }else{

            response.put("status",false);
            response.put("message","error student don't removed");
        }

        return ResponseEntity.ok(response);
    }
}
