package Controller;

import java.util.*;
import Model.*;
import Service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in
     * the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * 
     * @return a Javalin app object which defines the behavior of the Javalin
     *         controller.
     */

    MessageService messageService = new MessageService();
    AccountService accountService = new AccountService();

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("register", this::signIn);
        app.post("login", this::logIn);
        app.post("messages", this::createNewMassage);
        app.get("messages", this::getAllMessages);
        app.get("messages/{message_id}", this::getMessageById);
        app.delete("messages/{message_id}", this::deleteMessageById);
        app.patch("messages/{message_id}", this::updateMessageById);
        app.get("accounts/{account_id}/messages", this::getAllMessagesByUserId);
        return app;
    }

    private void signIn(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        if (account.getUsername() == null || account.getUsername().isEmpty() || account.getPassword().length() < 4) {
            ctx.status(400);
        } else {
            Account registredAccount = accountService.signIn(account);
            if (registredAccount != null) {
                ctx.json(mapper.writeValueAsString(registredAccount));
            } else {
                ctx.status(400);
            }
        }
    }

    private void logIn(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loggedAccount = accountService.logIn(account);
        if (loggedAccount != null) {
            ctx.json(mapper.writeValueAsString(loggedAccount));
        } else {
            ctx.status(401);
        }
    }

    private void createNewMassage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(ctx.body(), Message.class);
        if (msg.getMessage_text() == null || msg.getMessage_text().isEmpty() || msg.getMessage_text().length() > 255) {
            ctx.status(400);
        } else {
            Message newMsg = messageService.createNewMassage(msg);
            if (newMsg != null) {
                ctx.json(mapper.writeValueAsString(newMsg));
            } else {
                ctx.status(400);
            }
        }
    }

    private void getAllMessages(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageById(Context ctx) throws JsonMappingException, JsonProcessingException {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message msg = messageService.getMessageById(message_id);
        if (msg != null) {
            ctx.json(msg);
        } else {
            ctx.status(200);
        }

    }

    private void deleteMessageById(Context ctx) {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMsg = messageService.deleteMessageById(message_id);
        if (deletedMsg != null) {
            ctx.json(deletedMsg);
        } else {
            ctx.status(200);
        }
    }

    private void updateMessageById(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        String newText = msg.getMessage_text();
        if (newText == null || newText.isEmpty() || newText.length() > 255) {
            ctx.status(400);
        } else {
            boolean msgIsUpdated = messageService.updateMessage(message_id, newText);
            if (msgIsUpdated) {
                Message updatedMsg = messageService.getMessageById(message_id);
                ctx.json(updatedMsg);
            } else {
                ctx.status(400);
            }
        }
    }

    private void getAllMessagesByUserId(Context ctx) throws JsonProcessingException {
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByUserId(account_id);
        ctx.json(messages);
    }
}