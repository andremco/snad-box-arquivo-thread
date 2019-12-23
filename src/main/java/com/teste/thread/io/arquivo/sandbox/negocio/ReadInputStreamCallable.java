package com.teste.thread.io.arquivo.sandbox.negocio;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.concurrent.Callable;

public class ReadInputStreamCallable implements Callable<Integer> {

    private InputStream inputStream;

    private byte[] bytes;

    static Logger logger = Logger.getLogger(TesteIOArquivo.class.getName());

    public ReadInputStreamCallable(InputStream inputStream, byte[] bytes){
        this.inputStream = inputStream;
        this.bytes = bytes;
    }

    @Override
    public Integer call() throws Exception {
        int lengthRead;
        lengthRead = inputStream.read(bytes);
        logger.info("Lendo " + lengthRead);
        return lengthRead;
    }
}
