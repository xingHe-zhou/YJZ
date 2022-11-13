package com.hsinghai.yzj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <h2>易记账应用程序</h2>
 *
 * @author zh、lyq
 */
@MapperScan(basePackages = {"com.hsinghai.yzj.repository"})
@SpringBootApplication(scanBasePackages = {"com.hsinghai.yzj"})
public class YzjApplication {
    public static void main(String[] args) {
        new SpringApplication(YzjApplication.class).run(args);
    }
}
