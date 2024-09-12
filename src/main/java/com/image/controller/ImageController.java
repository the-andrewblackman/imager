package com.image.controller;

import com.image.entity.ImageEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

    @Controller
    public class ImageController {

        @GetMapping("/images")
        public String showImages(Model model) throws IOException {
            Document doc = Jsoup.connect("https://legacy.midjourney.com/showcase/recent/").get();
            Elements images = doc.select("img");

            List<ImageEntity> imageInfoList = new ArrayList<>();
            for (Element img : images) {
                String imageUrl = img.attr("src");
                String prompt = img.attr("alt");  // Assuming prompt is in alt attribute
                String userInfo = img.attr("data-user-info");  // Assuming user info is in a data attribute

                imageInfoList.add(new ImageEntity(imageUrl, prompt, userInfo));
            }

            model.addAttribute("images", imageInfoList);
            return "imageView";
        }
    }

