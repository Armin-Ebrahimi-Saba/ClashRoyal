package player;

import gameUtil.AliveTroop;
import gameUtil.Card;
import javafx.geometry.Point2D;

import java.io.*;
import java.net.Socket;
import java.util.Base64;

public class Client extends Player implements Runnable {
    private Status status;
    private BufferedReader reader;
    private OutputStream out;
    private String lastRespond;
    private boolean isSecondPlayer =true;

    /** this is the main method the class which is used for running a client server. */
    @Override
    public void run() {
        try (Socket connectionSocket = new Socket("127.0.0.1", 7660)) {
            InputStream in = connectionSocket.getInputStream();
            out = connectionSocket.getOutputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            startMessageListener();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * this is a getter
     * @return status of the client
     */
    public Status getStatus () {
        return status;
    }

    /**
     * this method set status
     * @param status is the status of the client
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * this method starts the message listener
     */
    private void startMessageListener() throws InterruptedException {
        Thread listener = new Thread(this::readMessage);
        listener.start();
    }

    /**
     * this method read and get message from server
     */
    private void readMessage() {
        String command = "";
        try {
            while((command = reader.readLine()) != null) {
                lastRespond = command;
                if (lastRespond.contains("PLAY")) {
                    String[] partitions = command.split(" ", 4);
                    Point2D point = new Point2D(
                            Double.parseDouble(partitions[1]),
                            Double.parseDouble(partitions[2]));
                    AliveTroop aliveTroop =
                            new AliveTroop((Card)fromString(partitions[3]), point);
                    status.getEnemyStatus().getAliveAllyTroops().add(aliveTroop);
                }
            }
        } catch(IOException | ClassNotFoundException ex) {ex.printStackTrace();}
    }

    /**
     * this method parse this command and execute it
     * @param command is the which should be parsed and then executed
     */
    private void parseAndExecuteCommand(String command) {

    }

    /**
     * this method writes and send message for server
     * @param message is the message which will be sent
     */
    public void sendCommand(String message) {
        try {
            out.write((message + "\n").getBytes());
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * this method return the last respond from the server
     * @return last respond from server
     */
    public String getLastRespond() {
        return lastRespond;
    }

    /** this change player from being second to first or visa versa. */
    public void changeTurn () {
        isSecondPlayer = !isSecondPlayer;
    }

    /** this method checks if this client is the second player at its current game. */
    public boolean isFirstPlayer() {
        return !isSecondPlayer;
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
}
