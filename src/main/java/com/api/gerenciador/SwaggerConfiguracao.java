package com.api.gerenciador;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguracao {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.api.gerenciador.controllers"))
                .paths(PathSelectors.any())
                .build() /*Constroi*/
                .apiInfo(apiInfo()); /*Chama o metodo de cabeçalho*/
    }

    /*Uma especie de Cabeçalho*/
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("API - Gerenciador")
                .description("Gerenciador de alunos")
                .version("1.0")
                .termsOfServiceUrl("https://github.com/crausmiguel/studentmanager")
                .license("João Vitor Miguel")
                .build();
    }

}
