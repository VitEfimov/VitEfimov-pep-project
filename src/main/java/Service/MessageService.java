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

    public List<Message> getAllMessages() {
        return messageDAO.getAllMasseges();
    }

    public Message getMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }
    
    public List<Message> getAllMessagesByUserId(int account_id) {
        return messageDAO.getAllMessagesByUserId(account_id);
    }

    public Message createNewMassage(Message msg){
        return messageDAO.createNewMassage(msg);
    }

    public Message deleteMessageById(int message_id) {
        return messageDAO.deleteMessageById(message_id);
    }

    public boolean updateMessage(int message_id, String message_text) {
        return messageDAO.updateMessage(message_id, message_text);
    }
}
