package com.image.controller;

import com.image.entity.ImageEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ImageController {

    @GetMapping("/scrape-images")
    public List<ImageEntity> scrapeImages() {
        List<ImageEntity> imageEntities = new ArrayList<>();

        try {
            // Connect to Wikimedia Commons and scrape images
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

        return imageEntities;  // Return the list of ImageEntity objects as JSON
    }
}
