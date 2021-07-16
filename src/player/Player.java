package player;

public class Player {
    private Status status;

    /**
     * this method set the status of the player
     * @param status is the new status which will be assigned to the player
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * this method is a getter
     * @return status of the player
     */
    public Status getStatus(){
        return status;
    }
}
