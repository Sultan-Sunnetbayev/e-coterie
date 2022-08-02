package tm.itit.e_coterie.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tm.itit.e_coterie.models.User;
import tm.itit.e_coterie.services.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
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
        userService.addRector(user, image);

        if(userService.isUserExists(user)){

            response.put("message","rector successful added");
            response.put("status",true);
        }else{

            response.put("message","rector don't added");
            response.put("status",false);
        }

        return ResponseEntity.ok(response);
    }

}
