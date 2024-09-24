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
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

@Controller  // Use @Controller for Thymeleaf templates
public class WebViewController {

        @GetMapping("/show-images")
        public String showImages(Model model) {
            List<ImageEntity> imageEntities = new ArrayList<>();
            
           try {
                // Scrape the images
                Document doc = Jsoup.connect("https://commons.wikimedia.org/wiki/Category:Pictures_of_the_day_(2008)").get();
                Elements images = doc.select("img");

                for (Element img : images) {
                    String imageUrl = img.attr("src");
                    String description = img.attr("alt");
                    imageEntities.add(new ImageEntity(imageUrl, description));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            model.addAttribute("images", imageEntities);  // Add the scraped images to the model
            return "imageView";  // Return the Thymeleaf template (imageView.html)
        }
    }


