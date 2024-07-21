package Service;

import java.util.*;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {

    public MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public List<Message> getMessages() {
        return messageDAO.getMessages();
    }

    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    public List<Message> getMessagesByAccountId(int account_id) {
        return messageDAO.getMessagesByAccountId(account_id);
    }

    public Message createMessage(Message msg) {
        return messageDAO.createMessage(msg);
    }

    public Message deleteMessageById(int message_id) {
        return messageDAO.deleteMessageById(message_id);
    }

    public boolean updateMessage(int message_id, String message_text) {
        return messageDAO.updateMessage(message_id, message_text);
    }
}
