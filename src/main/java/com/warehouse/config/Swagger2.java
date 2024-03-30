package com.warehouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Swagger2 {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                //加载配置信息
                .apiInfo(apiInfo())
                .globalRequestParameters(globalRequestParameters())
                .select()
                //加载swagger 扫描包
                .apis(RequestHandlerSelectors.basePackage("com.warehouse.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 获取swagger创建初始化信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger 测试文档")
                .description("Swagger documentation of EasyWareFlow")
                .version("1.0.0")
                .build();
    }

    private List<RequestParameter> globalRequestParameters() {
        RequestParameterBuilder tokenPar = new RequestParameterBuilder();
        List<RequestParameter> pars = new ArrayList<>();
        tokenPar.name("token")
                .description("jwt token")
                .in(ParameterType.HEADER)
                .required(true)
                .build();
        pars.add(tokenPar.build());
        return pars;
    }
}
