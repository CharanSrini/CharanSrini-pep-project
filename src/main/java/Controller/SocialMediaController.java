package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::userRegistrationHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getOneMessageHandler);
        app.delete("/messages/{message_id}", this::deleteOneMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesFromAccountHandler);
        return app;
    }

    private void userRegistrationHandler(Context context) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.userRegistration(account);
        if(addedAccount!=null){
            context.json(mapper.writeValueAsString(addedAccount));
            context.status(200);
        }else{
            context.status(400);
        }
    }

    private void loginHandler(Context context) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account foundAccount = accountService.login(account);
        if(foundAccount!=null){
            context.json(mapper.writeValueAsString(foundAccount));
            context.status(200);
        }else{
            context.status(401);
        }
    }

    private void createMessagesHandler(Context context) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message Message = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.createNewMessage(Message);
        if(addedMessage!=null){
            context.json(mapper.writeValueAsString(addedMessage));
            context.status(200);
        }else{
            context.status(400);
        }
    }

    private void getAllMessagesHandler(Context context) throws JsonMappingException, JsonProcessingException {
        context.json(messageService.getAllMessages());
    }

    private void getOneMessageHandler(Context context) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message gottenMessage = messageService.getMessage(Integer.parseInt(context.pathParam("message_id")));
        if(gottenMessage!=null){
            context.json(mapper.writeValueAsString(gottenMessage));
        }
        context.status(200);
    }

    private void deleteOneMessageHandler(Context context) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message deletedMessage = messageService.deleteMessage(Integer.parseInt(context.pathParam("message_id")));
        if(deletedMessage!=null){
            context.json(mapper.writeValueAsString(deletedMessage));
        }
        context.status(200);
    }

    private void updateMessageHandler(Context context) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        message.setMessage_id(Integer.parseInt(context.pathParam("message_id")));
        Message updatedMessage = messageService.updateMessage(message);
        if(updatedMessage!=null){
            context.json(mapper.writeValueAsString(updatedMessage));
            context.status(200);
        }else{
            context.status(400);
        }
    }

    private void getMessagesFromAccountHandler(Context context) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // List<Message> messages = messageService.getMessageByAccount(Integer.parseInt(context.pathParam("account_id")));
        context.json(mapper.writeValueAsString(messageService.getMessageByAccount(Integer.parseInt(context.pathParam("account_id")))));
        context.status(200);
    }
}