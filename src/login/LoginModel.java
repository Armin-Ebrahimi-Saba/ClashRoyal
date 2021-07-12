package login;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import dbUtil.DBConnection;
import player.Status;

public class LoginModel {
    Connection connection;

    public LoginModel() {
        try {
            this.connection = DBConnection.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (!isDataBaseConnected()) {
            System.exit(1);
        }
    }

    public boolean isDataBaseConnected() {
        return this.connection != null;
    }

    /**
     * this method validate a username and password which was sent by the user
     * @param username is the sent username
     * @param password is the sent password
     * @return status related to this username and password if it is new username and password it will send simplest status possible
     * @throws Exception is an exception that might be thrown due to working with prepared statement
     */
    public Status validateUsernameAndPassword(String username,
                                              String password) throws Exception {
        PreparedStatement preparedStatement  = null;
        ResultSet resultSet = null;

        String command = "SELECT * FROM login where username = ?";

        try {
            preparedStatement = this.connection.prepareStatement(command);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                Status newStatus = new Status(username);
                String commandInsert = "INSERT INTO login(username, password, data) VALUES(?,?,?)";
                PreparedStatement preparedStatementInsert = this.connection.prepareStatement(commandInsert);
                preparedStatementInsert.setString(1, username);
                preparedStatementInsert.setString(2, password);
                preparedStatementInsert.setString(3, toString(newStatus));
                return newStatus;
            } else if (password.equals(resultSet.getString(2)))
                return (Status)fromString(resultSet.getString(3));
        } catch(SQLException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (preparedStatement != null)
                preparedStatement.close();

            if (resultSet != null)
                resultSet.close();
        }
        return null;
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

    /** Write the object to a Base64 string. */
    private static String toString( Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}
