package com.teste.thread.io.arquivo.sandbox.negocio;

import org.springframework.stereotype.Component;

import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class TesteIOArquivo {

    static Logger logger = Logger.getLogger(TesteIOArquivo.class.getName());

    public void start() throws InterruptedException, IOException {
        logger.info("Inicio...");
//        copiarArquivoUtilizandoThread();
//        copiarArquivoSemThread();
//        zipFile();

    }

    public void copiarArquivoUtilizandoThread(){
        File fileIn = new File("data/in/psd7003.xml");
        File fileOut = new File("data/out/newXml.xml");

        try (InputStream in = new BufferedInputStream(new FileInputStream(fileIn));
             OutputStream out = new BufferedOutputStream(new FileOutputStream(fileOut))) {

            //100 mb
            byte[] buffer = new byte[100 * 1024 * 1024];
            int lengthRead;
            ArrayList<Integer> lista = new ArrayList<>();

            ExecutorService executor = Executors.newWorkStealingPool();

            List<ReadInputStreamCallable> callables = Arrays.asList(
                    new ReadInputStreamCallable(in, buffer),
                    new ReadInputStreamCallable(in, buffer),
                    new ReadInputStreamCallable(in, buffer),
                    new ReadInputStreamCallable(in, buffer),
                    new ReadInputStreamCallable(in, buffer),
                    new ReadInputStreamCallable(in, buffer),
                    new ReadInputStreamCallable(in, buffer),
                    new ReadInputStreamCallable(in, buffer),
                    new ReadInputStreamCallable(in, buffer),
                    new ReadInputStreamCallable(in, buffer),
                    new ReadInputStreamCallable(in, buffer)
            );

            executor.invokeAll(callables)
                    .stream()
                    .map(future -> {
                        try {
                            return future.get();
                        }
                        catch (Exception e) {
                            throw new IllegalStateException(e);
                        }
                    })
                    .forEach(lista::add);
            logger.info("Tamanho da lista " + lista.size());

            for (Integer item : lista){
                if (item > 0){
                    out.write(buffer, 0, item);
                    out.flush();
                }
            }
        } catch (InterruptedException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copiarArquivoSemThread(){
        File fileIn = new File("data/in/psd7003.xml");
        File fileOut = new File("data/out/newXml2.xml");

        try (InputStream in = new BufferedInputStream(new FileInputStream(fileIn));
             OutputStream out = new BufferedOutputStream(new FileOutputStream(fileOut))) {

            //100 mb
            byte[] buffer = new byte[100 * 1024 * 1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void zipFile() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("Test String");

        File f = new File("data/out/test.zip");
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
        ZipEntry e = new ZipEntry("data/in/psd7003.xml");
        out.putNextEntry(e);
        out.close();
    }

    public void processFile() throws IOException {
        File fileIn = new File("data/in/psd7003.xml");
        try (InputStream in = new BufferedInputStream(new FileInputStream(fileIn))){

        }
    }
}
