package com.ebiggerr.sims.service.input;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class imageUpload {

    //file storage directory
    private static String UPLOADED_FOLDER="src/main/resources/static/images/item/";

    /**
     * <h1> Save image uploaded to project root directory </h1>
     * @param file Multipart file (image uploaded by user)
     * @param filename SKU number of the item as the filename
     * @return path of where the image is store ( the path is the stored in the database as String
     * @throws IOException exception when saving/reading the file
     */
    public static String saveUploadFile(MultipartFile file, String filename) throws IOException {

        String extension = null;

        if( !file.isEmpty() ){

            //String[] format = file.getOriginalFilename().split(".");
            extension = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];

            filename = filename+ "."+ extension;

            //normal size
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + filename);
            Files.write(path, bytes);

            //thumbnail size
            //TODO thumbnail resize

        }

        return "images/item/"+ filename /*+ "."+ extension*/;

    }
}
