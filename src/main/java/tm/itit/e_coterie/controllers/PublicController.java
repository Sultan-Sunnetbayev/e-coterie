package tm.itit.e_coterie.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.activation.FileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class PublicController {

    @Value("${user.image.path}")
    private String imagePath;

    @PostMapping(path = "/login",produces = "application/json")
    public ResponseEntity login(@RequestParam("email")String email,
                                @RequestParam("password")String password){

        return null;
    }

    @GetMapping(path = "/home/sultan/data/imageUsers/{fileName}", produces = "application/json")
    public ResponseEntity getImage(@PathVariable("fileName")String fileName) throws IOException {

        File image=new File(imagePath+"/"+fileName);

        if(image.exists()){

            return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().
                    getContentType(image))).body(Files.readAllBytes(image.toPath()));
        }else{

            Map<String, Object>response=new HashMap<>();

            response.put("status",false);
            response.put("message","don't load image");

            return ResponseEntity.badRequest().body(response);
        }

    }
}
