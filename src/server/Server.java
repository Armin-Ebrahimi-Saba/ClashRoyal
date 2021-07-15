package server;

import dbUtil.DBConnection;
import login.LoginModel;
import player.Status;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private Connection connection;
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final int port;
    private final ArrayList<ClientHandler> handlers;
    private final LoginModel loginModel;
    private final ArrayList<ClientHandler> battleWaitList = new ArrayList<>();
    private final ArrayList<ClientHandler> twoOnTwoWaitList = new ArrayList<>();
    private final ArrayList<ClientHandler[]> twoOnTwoPlayingList = new ArrayList<>();

    /**
     * this is a constructor
     * @param port is the port number of this server
     */
    public Server(int port) {
        try {
            this.connection = DBConnection.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (!isDataBaseConnected()) {
            System.exit(1);
        }

        this.port = port;
        handlers = new ArrayList<>();
        this.loginModel = new LoginModel();
    }

    /**
     * this method runs the server
     * @param args is the given arguments
     */
    public static void main(String[] args) {
        Server server = new Server(7660);
        try(ServerSocket welcomingSocket = new ServerSocket(server.getPort())) {
            System.out.println("Server Started.");
            while (true) {
                Socket connectionSocket = welcomingSocket.accept();
                ClientHandler handler = new ClientHandler(connectionSocket, server);
                server.handlers.add(handler);
                server.executor.execute(handler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method updates the database
     * @param statusString is encoded of a status
     */
    public void updateDB(String statusString) {
        PreparedStatement preparedStatement  = null;
        ResultSet resultSet = null;

        String command = "SELECT * FROM login where username = ?";

        try {
            Status newStatus = (Status)fromString(statusString);
            String username = newStatus.getUsername();

            preparedStatement = this.connection.prepareStatement(command);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            String password = resultSet.getString(2);

            String commandInsert = "INSERT INTO login(username, password, data) VALUES(?,?,?)";
            PreparedStatement preparedStatementInsert = this.connection.prepareStatement(commandInsert);
            preparedStatementInsert.setString(1, username);
            preparedStatementInsert.setString(2, password);
            preparedStatementInsert.setString(3, toString(newStatus));
            preparedStatementInsert.execute(commandInsert);
        } catch(SQLException | IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();

                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException ex) {ex.printStackTrace();}
        }
    }

    /** Write the object to a Base64 string. */
    private static String toString( Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    /** Read the object from a Base64 string. */
    private static Object fromString( String s ) throws IOException ,
            ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode( s );
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(  data ) );
        Object o  = ois.readObject();
        ois.close();
        return o;
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
                handler.sendRespondMessage(message);
        });
    }

    /**
     * this method is a getter
     * @return handlers of this server
     */
    public ArrayList<ClientHandler> getHandlers() {
        return handlers;
    }

    /**
     * this method is a getter
     * @return loginModel of this server
     */
    public LoginModel getLoginModel() {
        return loginModel;
    }

    /**
     * this method is a getter
     * @return waiting list for one on one battle
     */
    public ArrayList<ClientHandler> getBattleWaitList() {
        return battleWaitList;
    }

    /**
     * this method is a getter
     * @return waiting list for two on two battle
     */
    public ArrayList<ClientHandler> getTwoOnTwoWaitList() {
        return twoOnTwoWaitList;
    }

    /**
     * this method checks if model is connected to the database
     * @return true if it is connected else false
     */
    public boolean isDataBaseConnected() {
        return this.connection != null;
    }

    /**
     * this method is a getter
     * @return playing list for two on two battle
     */
    public ArrayList<ClientHandler[]> getTwoOnTwoPlayingList() {
        return twoOnTwoPlayingList;
    }

    /** this class handles the client*/
    static class ClientHandler implements Runnable {
        private final Socket connectionSocket;
        private String encodedStatus;
        private InputStream inputStream;
        private OutputStream outputStream;
        private BufferedReader reader;
        private final Server server;
        private ClientHandler componentsHandler;

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
                    String command;
                    if ((command = line.substring(line.indexOf("<") + 1, line.indexOf(">"))).equalsIgnoreCase("LOGIN")) {
                        var usernameAndPassword = line.split(" ");
                        encodedStatus = server.getLoginModel().validateUsernameAndPassword(usernameAndPassword[1], usernameAndPassword[2]);
                        sendRespondMessage(encodedStatus);
                    } else if (command.equalsIgnoreCase("PLAY")) {
                        componentsHandler.sendRespondMessage(line);
                    } else if (command.equalsIgnoreCase("FIND_COMPONENT_1")) {
                        server.getBattleWaitList().add(this);
                        Thread thread = new Thread(() -> {
                            while (true) {
                                try {
                                    Thread.sleep(150);
                                } catch (InterruptedException e) {e.printStackTrace();}
                                if (server.handlers.size() > 1) {
                                    componentsHandler = chooseClient();
                                    server.getTwoOnTwoPlayingList().add(new ClientHandler[]{this, componentsHandler});
                                    sendRespondMessage("<READY> " + (componentsHandler != null ? componentsHandler.getEncodedStatus() : null));
                                    break;
                                } else
                                    sendRespondMessage("<!READY>");
                            }
                        });
                        thread.start();
                    } else if (command.equalsIgnoreCase("FIND_COMPONENT_2")) {
                        server.getBattleWaitList().add(this);
                        Thread thread = new Thread(() -> {
                            while (true) {
                                try {
                                    Thread.sleep(150);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (server.handlers.size() > 3) {
                                    sendRespondMessage("<READY>");
                                    break;
                                } else
                                    sendRespondMessage("<!READY>");
                            }
                        });
                        thread.start();
                    } else if (command.contains("<SAVE>")) {
                        String[] commandPartitions = command.split(" ", 2);
                        server.updateDB(commandPartitions[1]);
                    }
                }
            } catch(Exception ex) {ex.printStackTrace();}
        }

        /**
         * this method choose the client
         * @return encoded status of opponent of client of this handler
         */
        private ClientHandler chooseClient() {
            final ClientHandler[] handler = new ClientHandler[1];
            server.getBattleWaitList().forEach(clientHandler -> {
                if (clientHandler != this)
                    handler[0] = clientHandler;
            });
            server.getBattleWaitList().remove(handler[0]);
            return null;
        }

        /**
         * this method sends a respond message from server to client
         * @param message is the message which will be sent
         */
        public void sendRespondMessage(String message) {
            try {
                outputStream.write((message + "\n").getBytes());
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }

        /**
         * this is a getter
         * @return a string which is encoded status
         */
        public String getEncodedStatus() {
            return encodedStatus;
        }
    }
}
