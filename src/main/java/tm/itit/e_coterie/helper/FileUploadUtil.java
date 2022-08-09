package tm.itit.e_coterie.helper;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileUploadUtil {

    public static void save(final String imagePath, final String fileName, final MultipartFile image) throws IOException {

        Path path= Paths.get(imagePath);

        if(!Files.exists(path)){

            Files.createDirectories(path);
        }

        try(InputStream inputStream = image.getInputStream()){

            Path fullImagePath=path.resolve(fileName);

            Files.copy(inputStream, fullImagePath, StandardCopyOption.REPLACE_EXISTING);

        }catch(IOException exception){

            exception.printStackTrace();
        }
    }

    public static void saveDefaultImage(final String imagePath, final String defaultImageName,
                                            final String uuid) throws IOException {

        Path uploadDir=Paths.get(imagePath);

        if(!Files.exists(uploadDir)){

            Files.createDirectories(uploadDir);
        }
        File defaultImage=new File(imagePath+"/"+defaultImageName);

        if(!defaultImage.exists()){

            return;
        }
        Path fullPath=Paths.get(imagePath+"/"+uuid+"_"+defaultImageName);

        Files.createFile(fullPath);
        File image=new File(imagePath+"/"+uuid+"_"+defaultImageName);

        FileUtils.copyFile(defaultImage,image);

    }
}
