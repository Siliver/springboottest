package com.siliver.ch1.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * web服务配置类
 */
@ConfigurationProperties(prefix = "server")
@Configuration
public class ServerConfig {
    private int port;
    // 内部静态类对象
    private Servlet servlet=new Servlet();

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Servlet getServlet() {
        return servlet;
    }

    public void setServlet(Servlet servlet) {
        this.servlet = servlet;
    }

    /**
     * 内部静态类
     */
    public static class Servlet{
        String path;
        public String getPath(){
            return path;
        }
        public void setPath(String path){
            this.path=path;
        }
    }
}
