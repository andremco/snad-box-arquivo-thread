package com.teste.thread.io.arquivo.sandbox.route;

import com.teste.thread.io.arquivo.sandbox.negocio.TesteIOArquivo;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestRoute extends RouteBuilder {

    @Autowired
    TesteIOArquivo testeIOArquivo;

    private Logger logger = Logger.getLogger(TestRoute.class);

    @Override
    public void configure() throws Exception {

        testeIOArquivo.start();

    }
}
