package com.image.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  // Use @Controller for Thymeleaf templates
public class ShowController {

    @GetMapping("/show-images")
    public String showImagePage() {
        return "imageView"; // This will render imageView.html
    }



}




