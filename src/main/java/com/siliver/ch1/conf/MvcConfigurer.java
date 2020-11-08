package com.siliver.ch1.conf;

import com.siliver.ch1.entity.User;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class MvcConfigurer implements WebMvcConfigurer {
    /**
     * 
     * @param registory
     */
    public void addInterceptors(InterceptorRegistration registory){
        registory=new InterceptorRegistration(new SessionHandlerInterceptor());
        registory.addPathPatterns("/user/**");
    }

    public void addCorsMappings(CorsRegistry registry){

    }

    public void addFormatters(FormatterRegistry registry){
        registry.addFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
    }

    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/index.html").setViewName("/index.btl");
        registry.addRedirectViewController("/**/*.do","/index.html");
    }

    /**
     * 内部类，用于判断请求对象中SESSION中是否包含用户信息
     */
    class SessionHandlerInterceptor implements HandlerInterceptor{
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response) throws IOException {
            User user=(User) request.getSession().getAttribute("user");
            if (user==null){
                response.sendRedirect("/login.html");
                return false;
            }
            return true;
        }
    }


    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                connector.setProperty("relaxedQueryChars", "|{}[]");
            }
        });
        return factory;
    }
}
