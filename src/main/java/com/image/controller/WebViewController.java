package com.image.controller;

import com.image.entity.ImageEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

@Controller  // Use @Controller for Thymeleaf templates
public class WebViewController {

    @GetMapping("/scrape-images")
    @ResponseBody  // This ensures the data is returned as JSON
    // HashSet to store URLs that returned a 404 error
    public List<ImageEntity> showImages() {
        List<ImageEntity> imageEntities = new ArrayList<>();
        HashSet<String> invalidUrls = new HashSet<>();
        HashSet<String> doubleUrls = new HashSet<>();

        try {
            // Connect to the Wikimedia page
            Document doc = Jsoup.connect("https://commons.wikimedia.org/wiki/Category:Pictures_of_the_day_(2008)").get();
            Elements imageLinks = doc.select("a[href^='/wiki/File:']");

            // Iterate over each image link
            for (Element link : imageLinks) {
                String href = link.attr("href");
                String filePageUrl = "https://commons.wikimedia.org" + href;

                if(!doubleUrls.add(filePageUrl)){
                    System.out.println("Skipping double link");
                    continue;
                }
                // Skip if URL is in invalidUrls set
                if (invalidUrls.contains(filePageUrl)) {
                    System.out.println("Skipping previously invalid URL: " + filePageUrl);
                    continue;
                }

                try {
                    // Attempt to connect to the file page
                    Document fileDoc = Jsoup.connect(filePageUrl).get();

                    // Select the large image
                    Element imageElement = fileDoc.selectFirst("img[alt^='File:']");
                    if (imageElement != null) {
                        String highResImageUrl = imageElement.attr("src");
                        if (!highResImageUrl.startsWith("https:")) {
                            highResImageUrl = "https:" + highResImageUrl;
                        }

                        // Add the image data to the list
                        imageEntities.add(new ImageEntity(highResImageUrl, ""));
                    }
                } catch (IOException e) {
                    // If 404 error, add the URL to the invalidUrls set
                    System.out.println("Skipping URL: " + filePageUrl + " due to 404 error.");
                    invalidUrls.add(filePageUrl);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageEntities;
    }
}

