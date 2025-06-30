package Service;

import java.util.ArrayList;
import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
     private MessageDAO messageDAO;
    
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message createNewMessage(Message newMessage){
        if(newMessage.getMessage_text() != "" && newMessage.getMessage_text().length() < 255){
            return messageDAO.createMessage(newMessage);
        }
        return null;
    }

    public List<Message> getAllMessages(){
        List<Message> messages = messageDAO.getAllMessages();
        return messages;
    }

    public Message getMessage(int message_id){
        Message gotten = messageDAO.getSingleMessage(message_id);
        // if(gotten == null){
        //     return new Message(0, "", 0);
        // }
        return gotten;
    }

    public Message deleteMessage(int message_id){
        Message deleaten = messageDAO.deleteMessage(message_id);
        // if(message == null){
        //     return new Message(0, "", 0);
        // }
        return deleaten;
    }

    public Message updateMessage(Message message){
        if(message.getMessage_text() != "" && message.getMessage_text().length() < 255){
            return messageDAO.updateMessage(message);
        }
        return null;
    }

    public List<Message> getMessageByAccount(int account_id){
        List<Message> messages = messageDAO.getAllMessagesByAccount(account_id);
        // if(messages == null){
        //     return new ArrayList<Message>();
        // }
        return messages;
    }
}