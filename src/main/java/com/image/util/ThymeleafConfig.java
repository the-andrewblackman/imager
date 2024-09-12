package com.image.util;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

public class ThymeleafConfig implements WebMvcConfigurer {
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("classpath:/templates/");  // Path to templates
        templateResolver.setSuffix(".html");                  // File extension
        templateResolver.setTemplateMode(TemplateMode.HTML);  // Template type
        templateResolver.setCacheable(false);                 // Disable caching for development
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(SpringResourceTemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);  // Set resolver
        templateEngine.setEnableSpringELCompiler(true);        // Enable Spring EL
        return templateEngine;
    }
    @Bean
    public ThymeleafViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine);         // Use the template engine
        viewResolver.setOrder(1);                               // Order of view resolution
        viewResolver.setViewNames(new String[] {"*.html"});      // Only resolve .html views
        return viewResolver;
    }

}
