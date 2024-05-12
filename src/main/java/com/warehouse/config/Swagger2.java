package com.warehouse.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("spring-platform-interface")
                //加载配置信息
                .apiInfo(apiInfo()).globalOperationParameters(setHeaderToken()).select()
                //加载swagger 扫描包
                .apis(RequestHandlerSelectors.basePackage("com.warehouse.controller")).paths(PathSelectors.any()).build();
    }


    /**
     * 获取swagger创建初始化信息
     * @return
     */
    private ApiInfo apiInfo() {
        Contact contact = new Contact("wms", "", "1203823603`@qq.com");
        return new ApiInfoBuilder().title("swagger 测试文档").description("dev by ds").contact(contact).version("1.0.0").build();
    }

    private List<Parameter> setHeaderToken() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("token").description("token").modelRef(new ModelRef("string")).parameterType("header")
                .defaultValue("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsInVzZXJDb2RlIjoiYWRtaW4iLCJ1c2VyTmFtZSI6IueuoeeQhuWRmCIsImlzQWRtaW4iOiIxIiwiaWF0IjoxNzE0MTQ3MzY4LCJleHAiOjE3MTQyNDczNjh9.iTLHNcpd3UkZ6MpTBhb8ODzuxIy7_ocWB8mUUS1os08")
                .build();
        pars.add(tokenPar.build());
        return pars;
    }
}
