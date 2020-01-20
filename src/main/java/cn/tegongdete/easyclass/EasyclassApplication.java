package cn.tegongdete.easyclass;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.tegongdete.easyclass.mapper")
public class EasyclassApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyclassApplication.class, args);
    }

}
