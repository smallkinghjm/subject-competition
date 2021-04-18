package org.glut.competition.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
@Component
public class SwaggerConfig {
/*
*springboot集成swagger-ui(以及使用bootstrap-ui）
*url:http://localhost:8080/doc.html
* */

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.glut.competition.controller"))//
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("smallkinghjm","https://github.com/smallkinghjm/","2955164425@qq.com");
        return new ApiInfoBuilder()
                //.contact("作者名")//已弃用
                 .contact(contact)
                .title("学科赛事管理系统api文档")
                .description("restful 风格接口")
                .version("v1.0")
                .build();
    }


}


