package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientSession implements Runnable {
    private static final Logger log = LogManager.getLogger(ClientSession.class);

    private Socket socket;


    public ClientSession(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        try (BufferedReader br = takeBufferedReader()) {
            // reading headers
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                if (line.equals("")) break;
            }

        } catch (IOException e) {
            log.error("", e);
        }
        log.debug("Thread {} stopped", Thread.currentThread().getName());
    }

    private BufferedReader takeBufferedReader() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }


}
