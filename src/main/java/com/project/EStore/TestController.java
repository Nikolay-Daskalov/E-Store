package com.project.EStore;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Controller
public class TestController {

    private final Cloudinary cloudinary;

    public TestController(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @GetMapping("/web")
    public String index() {
        return "index";
    }

    @PostMapping("/webP")
    public String postIndex(@RequestParam(name = "testFile") MultipartFile multipartFile) throws IOException {
        File file = File.createTempFile("test", "test");
        multipartFile.transferTo(file);
        cloudinary.uploader().upload(file, Map.of());

        file.delete();
        return "redirect:/web";
    }
}
