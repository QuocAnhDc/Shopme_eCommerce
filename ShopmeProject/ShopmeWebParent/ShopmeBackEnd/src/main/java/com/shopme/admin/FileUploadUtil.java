package com.shopme.admin;

import org.springframework.security.core.parameters.P;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    public static void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {

        Path uploadPath = Paths.get(uploadDir);
        // check path upload exist
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }


        try (InputStream inputStream = multipartFile.getInputStream()) {
            // upload file
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            //System.out.println("error");
            throw new IOException("Could not save file"  + fileName, e);
        }
    }
    public static void cleanDir(String dir){
        Path dirPath = Paths.get(dir);

        try {
            Files.list(dirPath).forEach(file->{
                if (!Files.isDirectory(file)){
                    try{
                        Files.delete(file);
                    } catch (IOException ex){
                        System.out.println("Could not delete file: "+file);
                    }
                }
            });
        } catch (IOException ex) {
            System.out.println("Could not list directory: "+ dirPath);
        }
    }
}
