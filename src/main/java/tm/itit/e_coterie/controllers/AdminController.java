package tm.itit.e_coterie.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tm.itit.e_coterie.dtos.StudentSpecialityDTO;
import tm.itit.e_coterie.models.*;
import tm.itit.e_coterie.services.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final UserService userService;
    private final FacultyService facultyService;
    private final DeanFacultyService deanFacultyService;
    private final StudentService studentService;
    private final StudentSpecialityService studentSpecialityService;
    private final CoterieService coterieService;
    private final GovernorCoterieService governorCoterieService;

    @Autowired
    public AdminController(UserService userService, FacultyService facultyService,
                           DeanFacultyService deanFacultyService, StudentService studentService,
                           StudentSpecialityService studentSpecialityService, CoterieService coterieService,
                           GovernorCoterieService governorCoterieService) {
        this.userService = userService;
        this.facultyService = facultyService;
        this.deanFacultyService = deanFacultyService;
        this.studentService = studentService;
        this.studentSpecialityService = studentSpecialityService;
        this.coterieService = coterieService;
        this.governorCoterieService = governorCoterieService;
    }

    @PostMapping(path = "/add/rector", produces = "application/json")
    public ResponseEntity addRector(final @ModelAttribute User user,
                                    final @RequestParam(value = "image",required = false)MultipartFile image){

        Map<String, Object> response=new HashMap<>();

        if(userService.isUserExists(user)){

            response.put("status",false);
            response.put("message","this user already exists");

            return ResponseEntity.ok(response);
        }
        final String role="ROLE_RECTOR";

        userService.addUser(user, image, role);
        if(userService.isUserExists(user)){

            response.put("message","rector successful added");
            response.put("status",true);
        }else{

            response.put("message","error rector don't added");
            response.put("status",false);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/add/prorector", produces = "application/json")
    public ResponseEntity addProrector(final @ModelAttribute User prorector,
                          final @RequestParam(value = "image", required = false)MultipartFile image){

        Map<String, Object>response=new HashMap<>();

        if(userService.isUserExists(prorector)){

            response.put("message","this user already exists");
            response.put("status",false);

            return ResponseEntity.ok(response);
        }
        final String role="ROLE_PRORECTOR";

        userService.addUser(prorector, image, role);
        if(userService.isUserExists(prorector)){

            response.put("message","prorector successful added");
            response.put("status", true);
        }else{

            response.put("message","error prorector don't added");
            response.put("status",false);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/add/faculty", produces = "application/json")
    public ResponseEntity addFaculty(final @ModelAttribute Faculty faculty){

        Map<String,Object>response=new HashMap<>();

        if(facultyService.isFacultyExists(faculty)){

            response.put("status",true);
            response.put("message","error this faculty already exists");

            return ResponseEntity.ok(response);
        }
        facultyService.addFaculty(faculty);
        if (facultyService.isFacultyExists(faculty)) {

            response.put("status",true);
            response.put("message","faculty successful added");
        }else{

            response.put("status",false);
            response.put("message","error faculty don't added");
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/remove/faculty/by/id", produces = "application/json")
    public ResponseEntity removeFacultyById(final @RequestParam("facultyId")Integer facultyId){

        Map<String,Object>response=new HashMap<>();

        if(facultyService.isFacultyExistsById(facultyId)){

            List<DeanFaculty>deanFaculties= deanFacultyService.getDeanFacultiesByFacultyId(facultyId);
            List<StudentSpecialityDTO>studentSpecialityDTOS=studentSpecialityService.getStudentSpecialityDTOByFacultyId(facultyId);

            facultyService.removeFacultyById(facultyId);
            for(DeanFaculty deanFaculty:deanFaculties){

                if(!userService.isUserExistsById(deanFaculty.getUser().getId())){

                    if(deanFaculty.getUser().getImagePath()!=null && !deanFaculty.getUser().getImagePath().isEmpty()){

                        File image=new File(deanFaculty.getUser().getImagePath());

                        if(image.exists()){

                            image.delete();
                        }
                    }
                }
            }
            for(StudentSpecialityDTO studentSpecialityDTO : studentSpecialityDTOS){

                List<Student>students=studentService.getStudentsByStudentSpecialityId(studentSpecialityDTO.getId());

                students.forEach(student -> {
                    if(student.getUser().getImagePath()!=null && !student.getUser().getImagePath().isEmpty()){

                        File image =new File(student.getUser().getImagePath());

                        if(image.exists()){

                            image.delete();
                        }
                    }
                });
            }
        }else{

            response.put("status",false);
            response.put("message","error faculty don't found with this id");

            return ResponseEntity.ok(response);
        }
        if(!facultyService.isFacultyExistsById(facultyId)){

            response.put("status",true);
            response.put("message","faculty successful removed");
        }else{

            response.put("status",false);
            response.put("message","error faculty don't removed");
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/add/deanFaculty", produces = "application/json")
    public ResponseEntity addDeanFaculty(final @ModelAttribute User deanFaculty,
                                         final @RequestParam(value = "image", required = false)MultipartFile image,
                                         final @RequestParam("facultyId")Integer facultyId){

        Map<String,Object>response=new HashMap<>();

        if(userService.isUserExists(deanFaculty)){

            response.put("status",false);
            response.put("message","error this user already exists");

            return ResponseEntity.ok(response);
        }
        if(!facultyService.isFacultyExistsById(facultyId)){

            response.put("status",false);
            response.put("message","error faculty don't found with this id");

            return ResponseEntity.ok(response);
        }
        final String role="ROLE_DEAN_FACULTY";

        userService.addUser(deanFaculty, image,role);
        if(userService.isUserExists(deanFaculty)){

            User user=userService.getUserByEmail(deanFaculty.getEmail());

            if(user==null){

                response.put("status",false);
                response.put("message","error with user");

                return ResponseEntity.ok(response);
            }
            Faculty faculty=facultyService.getFacultyById(facultyId);

            if(faculty==null){

                response.put("status",false);
                response.put("message","error with faculty");
            }
            deanFacultyService.addDeanFaculty(user,faculty);
            if(deanFacultyService.checkDeanFacultyByUserIdAndFacultyId(user.getId(), faculty.getId())){

                response.put("status",true);
                response.put("message","dean faculty successful added");
            }else{

                userService.removeUserById(user.getId());
                response.put("status",false);
                response.put("message","error dean faculty don't added");
            }
        }else{

            response.put("status",false);
            response.put("message","error dean faculty don't added");

        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/remove/user/by/id", produces = "application/json")
    public ResponseEntity removeUserById(final @RequestParam("userId")Integer userId){

        Map<String,Object>response=new HashMap<>();

        if(!userService.isUserExistsById(userId)){

            response.put("message","error user not found with this id");
            response.put("status",true);

            return ResponseEntity.ok(response);
        }
        userService.removeUserById(userId);
        if(!userService.isUserExistsById(userId)){

            response.put("message","accept user successful removed");
            response.put("status",true);
        }else{

            response.put("message","error user don't removed");
            response.put("status",false);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/add/governor/coterie", produces = "application/json")
    public ResponseEntity addGovernorCoterie(final @ModelAttribute User governorCoterie,
                                             final @RequestParam(value = "image",required = false)MultipartFile image,
                                             final @RequestParam("coterieId")int coterieId){

        Map<String, Object>response=new HashMap<>();

        if(userService.isUserExists(governorCoterie)){

            response.put("status",false);
            response.put("message","error this user already exists");

            return ResponseEntity.ok(response);
        }
        if(!coterieService.isCoterieExistsById(coterieId)){

            response.put("status",false);
            response.put("message","error coterie not found with this id");

            return ResponseEntity.ok(response);
        }
        final String roleName="ROLE_GOVERNOR_COTERIE";

        userService.addUser(governorCoterie, image, roleName);
        if(userService.isUserExists(governorCoterie)){

            User user=userService.getUserByEmail(governorCoterie.getEmail());
            Coterie coterie=coterieService.getCoterieById(coterieId);

            governorCoterieService.addGovernorCoterie(user, coterie);
            if(governorCoterieService.isGovernorCoterieExistsByUserIdAndCoterieId(user.getId(), coterieId)){

                response.put("status",true);
                response.put("message","accept governor coterie successful added");
            }else{

                response.put("status",false);
                response.put("message","error governor coterie don't added");
            }
        }else{

            response.put("status",false);
            response.put("message","error with user");
        }

        return ResponseEntity.ok(response);
    }
}
