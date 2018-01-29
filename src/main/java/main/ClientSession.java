package main;

import http.HttpRequest;
import http.HttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

import static util.Utils.toBufferedReader;

public class ClientSession implements Runnable {
    private static final Logger log = LogManager.getLogger(ClientSession.class);

    private Socket socket;


    public ClientSession(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        try (BufferedReader br = toBufferedReader(socket.getInputStream())) {

            HttpRequest req = new HttpRequest(br);
            HttpResponse res = HttpResponse.from(req);

            try (BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream())) {
                res.write(os);
            }

        } catch (IOException e) {
            log.error("", e);
        }
        log.debug("Thread stopped");
    }


}
