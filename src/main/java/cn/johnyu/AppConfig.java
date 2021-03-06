package cn.johnyu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"cn.johnyu.controllers"})
@EnableWebSecurity
public class AppConfig implements WebMvcConfigurer {
    /**
     * 将JSP存储于classpath下的views中
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/classes/views/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }

    //简化了ViewController的编写
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

//        registry.addViewController("/suc").setViewName("suc");
        registry.addViewController("/error").setViewName("error");
    }

    //静态资源的开放
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations(
                "classpath:/assets/");// 3
    }



}