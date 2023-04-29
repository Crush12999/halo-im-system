package com.halo.im.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * IM 服务层应用
 *
 * @author halo
 * @since 2023/4/30 02:03
 */
@SpringBootApplication(scanBasePackages = {"com.halo.im.service"})
public class ImServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ImServiceApplication.class, args);
    }
}
