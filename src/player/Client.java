package player;

public class Client {
    private Status status;

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
}
