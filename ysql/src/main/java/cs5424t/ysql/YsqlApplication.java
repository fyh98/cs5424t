package cs5424t.ysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class YsqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(YsqlApplication.class, args);
    }

}
