package com.mrochko.dev.telegramgptchatservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Pavlo Mrochko
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/index").setViewName("index");
    registry.addViewController("/").setViewName("index");
    registry.addViewController("/admin").setViewName("admin/menu");
    registry.addViewController("/admin/menu").setViewName("admin/menu");
    registry.addViewController("/admin/chats").setViewName("admin/chats");
    registry.addViewController("/admin/users")
        .setViewName("forward:/api/v1/admin/users");
    registry.addViewController("/register")
        .setViewName("forward:/api/v1/register");
    registry.addViewController("/login").setViewName("login");
  }

}
