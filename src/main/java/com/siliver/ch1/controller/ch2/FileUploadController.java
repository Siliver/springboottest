package com.siliver.ch1.controller.ch2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/upload")
public class FileUploadController {

    @PostMapping("fore")
    @ResponseBody
    public String handleFormUpload(@RequestParam("name") String name, @RequestParam("file")MultipartFile file) throws IOException {
        if (!file.isEmpty()){
            String fileName=file.getOriginalFilename();
            InputStream ins=file.getInputStream();

            return new String(ins.readAllBytes(),"utf-8");
        }
        return "failure";
    }
}
