package DAO;

import Model.Message;
import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;

public class MessageDAO {
    public Message createMessage (Message message, Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
