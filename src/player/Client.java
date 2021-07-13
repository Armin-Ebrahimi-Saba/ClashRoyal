package player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

public class Client {
    private final Status status;
    private BufferedReader reader;
    private OutputStream outputStream;

    /**
     * this is a constructor
     * @param status is the status of the client
     */
    public Client(Status status) {
        this.status = status;
    }

    /**
     * this is a getter
     * @return status of the client
     */
    public Status getStatus () {
        return status;
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
        String command;
        try {
            while((command = reader.readLine()) != null) {
                parseAndExecuteCommand(command);
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
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
    public void writeMessage(String message) {
        try {
            outputStream.write((message + "\n").getBytes());
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
