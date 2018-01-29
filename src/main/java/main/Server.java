package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Paths;

public class Server {
    private static final Logger log = LogManager.getLogger(Server.class);
    private static final int DEFAULT_PORT = 80;

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(DEFAULT_PORT)) {
            log.info("Server started and made {} to be current document root", Paths.get(".").toAbsolutePath());

            while (true) {
                new Thread(new ClientSession(ss.accept())).start();
            }

        } catch (IOException e) {
            log.error("", e);
        }
        log.info("Server stopped");
    }
}
