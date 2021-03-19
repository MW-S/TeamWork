package net.mw;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import net.mw.system.config.jwt.JwtAuthenticationTokenFilter;

@SpringBootApplication(scanBasePackages = "net.mw")
@EnableTransactionManagement
@MapperScan(basePackages = "net.mw.system.dao, net.mw.teamwork.dao")
public class TeamWorkApplication {

    public static void main(String[] args){
        SpringApplication.run(TeamWorkApplication.class);
    }

    @Bean
    public FilterRegistrationBean registration(JwtAuthenticationTokenFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }
}
