package com.lacamentopeca.pedidosDePecas;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.CrossOrigin;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    @CrossOrigin("*")
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PedidosDePecasApplication.class);
    }

}
