package Service;

import DAO.MessageDAO;
import Model.Message;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message) {
        return messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int messageid) {
        return messageDAO.getMessageById(messageid);
    }

    public Message deleteMessage(int messageid) {
        return messageDAO.deleteMessage(messageid);
    }

    public Message updateMessage(int messageid, String newtext) {
        return messageDAO.updateMessage(messageid, newtext);
    }

    public List<Message> getUserMessages(int accountid) {
        return messageDAO.getUserMessages(accountid);
    }
}
