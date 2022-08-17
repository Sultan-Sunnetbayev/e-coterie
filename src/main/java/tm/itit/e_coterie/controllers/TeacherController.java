package tm.itit.e_coterie.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tm.itit.e_coterie.dtos.UserDTO;
import tm.itit.e_coterie.models.Coterie;
import tm.itit.e_coterie.models.User;
import tm.itit.e_coterie.services.CoterieService;
import tm.itit.e_coterie.services.TeacherService;
import tm.itit.e_coterie.services.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/teacher")
public class TeacherController {

    private final TeacherService teacherService;
    private final CoterieService coterieService;
    private final UserService userService;

    @Autowired
    public TeacherController(TeacherService teacherService, CoterieService coterieService,
                             UserService userService) {
        this.teacherService = teacherService;
        this.coterieService = coterieService;
        this.userService = userService;
    }

    @GetMapping(path = "/get", produces = "application/json")
    public ResponseEntity getTeachersByParameter(@RequestParam(value = "coterieIds",required = false) List<Integer> teacherIds){

        Map<String,Object> response=new HashMap<>();
        List<UserDTO>teacherDTOS=teacherService.getAllTeacherDTO(teacherIds);

        if(teacherDTOS==null){

            teacherDTOS=new ArrayList<>();
        }
        response.put("status",true);
        response.put("message",teacherDTOS);

        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/add", produces = "application/json")
    public ResponseEntity addTeacher(final @ModelAttribute User user,
                                     final @RequestParam("image")MultipartFile image,
                                     final @RequestParam("coterieId")int coterieId){

        Map<String,Object>response=new HashMap<>();
        Coterie coterie=coterieService.getCoterieById(coterieId);

        if(coterie==null){

            response.put("status",false);
            response.put("message","error coterie not found with this id");

            return ResponseEntity.ok(response);
        }
        if(!userService.isUserExists(user)){

            final String role="ROLE_TEACHER";
            userService.addUser(user, image, role);
        }
        if(!userService.isUserExists(user)){

            response.put("status",false);
            response.put("message","error user don't added");

            return ResponseEntity.ok(response);
        }
        User teacher=userService.getUserByEmail(user.getEmail());

        if(teacher!=null){

            teacherService.addTeacher(teacher, coterie);
        }else{

            response.put("status",false);
            response.put("message","error with user");

            return ResponseEntity.ok(response);
        }
        if(teacherService.isTeacherExistsByUserIdAndCoterieId(teacher.getId(), coterieId)){

            response.put("status",true);
            response.put("message","accept teacher successful added");
        }else{

            response.put("status",false);
            response.put("message","error teacher don't added");
        }

        return ResponseEntity.ok(response);
    }

}
