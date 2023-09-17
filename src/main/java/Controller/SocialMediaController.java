package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        //app.patch("/messages/{message_id}", this::updateMessageHandler);
        //app.get("/accounts/{account_id}/messages", this::getUserMessagesHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerHandler (Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account registeredaccount = accountService.registerAccount(account);
        if (registeredaccount != null 
        && !registeredaccount.getUsername().isBlank() 
        && registeredaccount.getPassword().length() >= 4) {
            ctx.json(mapper.writeValueAsString(registeredaccount));
        } else {
            ctx.status(400);
        }
    }

    private void loginHandler (Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account logincred = accountService.loginAttempt(account);
        if (logincred != null) {
            ctx.json(mapper.writeValueAsString(logincred));
        } else {
            ctx.status(401);
        }
    }

    private void createMessageHandler (Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message postedmessage = messageService.createMessage(message);
        if (postedmessage != null 
        && !postedmessage.getMessage_text().isBlank() 
        && postedmessage.getMessage_text().length() < 255 
        && accountService.checkForUser(postedmessage.getPosted_by()) != null) {
            ctx.json(mapper.writeValueAsString(postedmessage));
        } else {
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler (Context ctx) throws JsonProcessingException {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageHandler (Context ctx) throws JsonProcessingException {
        String[] splitpath = ctx.path().split("/");
        Message messagecheck = messageService.getMessageById(Integer.parseInt(splitpath[splitpath.length - 1]));
        if (messagecheck != null) {
            ctx.json(messagecheck);
        } else {
            ctx.status(200);
        }
    }

    private void deleteMessageHandler (Context ctx) throws JsonProcessingException {
        String[] splitpath = ctx.path().split("/");
        Message messagecheck = messageService.deleteMessage(Integer.parseInt(splitpath[splitpath.length - 1]));
        if (messagecheck != null) {
            ctx.json(messagecheck);
        } else {
            ctx.status(200);
        }
    }
}