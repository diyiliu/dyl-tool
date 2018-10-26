package com.dyl.tool.web.image;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.image4j.codec.ico.ICOEncoder;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * Description: ImgController
 * Author: DIYILIU
 * Update: 2018-10-25 16:57
 */

@Controller
@RequestMapping("/image")
public class ImgController {

    @PostMapping("/ico")
    public ResponseEntity<byte[]> convertIco(MultipartFile file, @RequestParam(required = false) Integer size) throws IOException {
        int length = 16;
        if (size != null) {
            length = size;
        }

        String fileName = file.getOriginalFilename();
        org.springframework.core.io.Resource tempDir = new UrlResource("file:./temp");
        File tempFile = File.createTempFile("temp", fileName.substring(fileName.lastIndexOf(".")).toLowerCase(), tempDir.getFile());
        FileCopyUtils.copy(file.getBytes(), tempFile);

        Thumbnails.of(tempFile.getPath()).size(length, length).keepAspectRatio(false).toFile(tempFile.getPath());
        File icoFile = File.createTempFile("favicon-", ".ico", tempDir.getFile());
        ICOEncoder.write(ImageIO.read(tempFile), icoFile);
        tempFile.delete();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", icoFile.getName());

        return new ResponseEntity(FileUtils.readFileToByteArray(icoFile), headers, HttpStatus.CREATED);
    }
}
