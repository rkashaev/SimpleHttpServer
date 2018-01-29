package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private static final Logger log = LogManager.getLogger(Server.class);
    private static final int DEFAULT_PORT = 80;

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(DEFAULT_PORT)) {
            log.info("Server started");
//            while (true) {
            new Thread(new ClientSession(ss.accept())).start();
//            }
        } catch (IOException e) {
            log.error("", e);
        }
        log.info("Server stopped");
    }
}
