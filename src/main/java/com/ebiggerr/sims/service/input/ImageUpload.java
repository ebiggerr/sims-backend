/*
 * Copyright (c) 2021 EbiggerR
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ebiggerr.sims.service.input;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ImageUpload {

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
