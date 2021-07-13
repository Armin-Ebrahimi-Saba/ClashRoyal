package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final int port;
    private final ArrayList<ClientHandler> handlers;

    /**
     * this is a constructor
     * @param port is the port number of this server
     */
    public Server(int port) {
        this.port = port;
        handlers = new ArrayList<>();
    }

    /**
     * this method runs the server
     * @param args is the given arguments
     */
    public static void main(String[] args) {
        Server server = new Server(7760);
        try(ServerSocket welcomingSocket = new ServerSocket(server.getPort())) {
            System.out.println("Server Started.");
            int count = 0;
            while (count < 2) {
                Socket connectionSocket = welcomingSocket.accept();
                ClientHandler handler = new ClientHandler(connectionSocket, server);
                server.handlers.add(handler);
                server.executor.execute(handler);
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this is a getter
     * @return port number
     */
    public int getPort() {
        return port;
    }

    /**
     * this method send command for opponent
     * @param message is the command which will be sent
     * @param clientHandler is the client handler which handle sender of the message
     */
    public void sendForOpponent(String message, ClientHandler clientHandler) {
        handlers.forEach((handler) -> {
            if (handler != clientHandler)
                handler.writeMessage(message);
        });
    }

    /** this class handles the client*/
    static class ClientHandler implements Runnable {
        private final Socket connectionSocket;
        private InputStream inputStream;
        private OutputStream outputStream;
        private BufferedReader reader;
        private final Server server;

        public ClientHandler(Socket connectionSocket, Server server) {
            this.connectionSocket = connectionSocket;
            this.server = server;
        }

        @Override
        public void run() {
            try {
                inputStream = connectionSocket.getInputStream();
                outputStream = connectionSocket.getOutputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                startMessageListener();
            } catch(IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        private void startMessageListener() throws InterruptedException {
            Thread listener = new Thread(this::readMessage);
            listener.start();
            listener.join();
        }

        private void readMessage() {
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    server.sendForOpponent(line, this);
                }
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }

        public void writeMessage(String message) {
            try {
                outputStream.write((message + "\n").getBytes());
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
