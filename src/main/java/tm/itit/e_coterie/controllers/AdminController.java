package tm.itit.e_coterie.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tm.itit.e_coterie.models.DeanFaculty;
import tm.itit.e_coterie.models.Faculty;
import tm.itit.e_coterie.models.User;
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
//    private final PulpitService pulpitService;
//    private final GovernorPulpitService governorPulpitService;

    @Autowired
    public AdminController(UserService userService, FacultyService facultyService,
//                           PulpitService pulpitService, GovernorPulpitService governorPulpitService,
                           DeanFacultyService deanFacultyService
                           ) {
        this.userService = userService;
        this.facultyService = facultyService;
        this.deanFacultyService = deanFacultyService;
//        this.pulpitService = pulpitService;
//        this.governorPulpitService = governorPulpitService;
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
        String role="ROLE_PRORECTOR";

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

            List<DeanFaculty>deanFaculties=facultyService.getFacultyById(facultyId).getDeanFaculties();

            for(DeanFaculty deanFaculty:deanFaculties){

                if(deanFaculty.getUser().getImagePath()!=null && !deanFaculty.getUser().getImagePath().isEmpty()){

                    File image=new File(deanFaculty.getUser().getImagePath());

                    if(image.exists()){

                        image.delete();
                    }
                }
            }
            facultyService.removeFacultyById(facultyId);
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

                response.put("status",false);
                response.put("message","error dean faculty don't added");
            }
        }else{

            response.put("status",false);
            response.put("message","error dean faculty don't added");

        }

        return ResponseEntity.ok(response);
    }

//    @PostMapping(path = "/add/pulpit",produces = "application/json")
//    public ResponseEntity addPulpit(final @ModelAttribute Pulpit pulpit){
//
//        Map<String,Object>response=new HashMap<>();
//
//        if(pulpitService.isPulpitExists(pulpit)){
//
//            response.put("status",false);
//            response.put("message","error this pulpit already exists");
//
//            return ResponseEntity.ok(response);
//        }
//        pulpitService.addPulpit(pulpit);
//        if(pulpitService.isPulpitExists(pulpit)){
//
//            response.put("status",true);
//            response.put("message","accept pulpit successful added");
//        }else{
//
//            response.put("status",false);
//            response.put("message","error pulpit don't added");
//        }
//
//        return ResponseEntity.ok(response);
//    }
//
//    @PostMapping(path = "/add/governor/pulpit",produces = "application/json")
//    public ResponseEntity addGovernorPulpit(final @ModelAttribute User governorPulpit,
//                                            final @RequestParam(value = "image",required = false)MultipartFile image,
//                                            final @RequestParam("pulpitId")Integer pulpitId){
//
//        Map<String,Object>response=new HashMap<>();
//
//        if(userService.isUserExists(governorPulpit)){
//
//            response.put("status",false);
//            response.put("message","this user already exists");
//
//            return ResponseEntity.ok(response);
//        }
//        if(!pulpitService.isPulpitExistsById(pulpitId)){
//
//            response.put("status",false);
//            response.put("message","error pulpit not found with this id");
//
//            return ResponseEntity.ok(response);
//        }
//        final String role="ROLE_GOVERNOR_PULPIT";
//
//        userService.addUser(governorPulpit, image,  role);
//        if(userService.isUserExists(governorPulpit)){
//
//            final User user=userService.getUserByEmail(governorPulpit.getEmail());
//            final Pulpit pulpit=pulpitService.getPulpitById(pulpitId);
//
//            governorPulpitService.addGovernorPulpit(user, pulpit);
//            if(governorPulpitService.isGovernorPulpitExists(user.getId(), pulpitId)){
//
//                response.put("status",true);
//                response.put("message","accept governor pulpit successful added");
//            }else{
//
//                response.put("status",false);
//                response.put("message","error governor pulpit don't added");
//            }
//        }else{
//
//            response.put("status",false);
//            response.put("message","error governor pulpit don't added");
//        }
//
//        return ResponseEntity.ok(response);
//    }
//
//    @DeleteMapping(path = "/remove/pulpit/by/id",produces = "application/json")
//    public ResponseEntity removePulpitById(final @RequestParam("pulpitId")Integer pulpitId){
//
//        Map<String,Object>response = new HashMap<>();
//
//        if(!pulpitService.isPulpitExistsById(pulpitId)){
//
//            response.put("message","error pulpit not found with this id");
//            response.put("status",false);
//
//            return ResponseEntity.ok(response);
//        }
//        pulpitService.removePulpitById(pulpitId);
//
//        if(!pulpitService.isPulpitExistsById(pulpitId)){
//
//            response.put("message","accept pulpit successful removed");
//            response.put("status",true);
//        }else{
//
//            response.put("message","error pulpit don't removed");
//            response.put("status",true);
//        }
//
//        return ResponseEntity.ok(response);
//    }

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

}
