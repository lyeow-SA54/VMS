//package iss.team5.vms.interceptor;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class MyWebAppConfigurer implements WebMvcConfigurer {
//    @Value("${upload.file.location}")
//    private String fileLocation;
//    @Value("${upload.file.path}")
//    private String filePath;
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler(filePath).addResourceLocations(fileLocation);
//        WebMvcConfigurer.super.addResourceHandlers(registry);
//    }
//}
