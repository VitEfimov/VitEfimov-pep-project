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
        app.post("register", this::registerUser);
        app.post("login", this::loginUser);
        app.post("messages", this::createMessage);
        app.get("messages", this::getMessages);
        app.get("messages/{message_id}", this::getMessageById);
        app.delete("messages/{message_id}", this::deleteMessageById);
        app.patch("messages/{message_id}", this::updateMessageById);
        app.get("accounts/{account_id}/messages", this::getMessagesByAccountId);
        return app;
    }

    private void registerUser(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        if (account.getUsername() == null || account.getUsername().isEmpty() || account.getPassword().length() < 4) {
            ctx.status(400);
        } else {
            Account registredAccount = accountService.registerUser(account);
            if (registredAccount != null) {
                ctx.json(mapper.writeValueAsString(registredAccount));
            } else {
                ctx.status(400);
            }
        }
    }

    private void loginUser(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loggedAccount = accountService.loginUser(account);
        if (loggedAccount != null) {
            ctx.json(mapper.writeValueAsString(loggedAccount));
        } else {
            ctx.status(401);
        }
    }

    private void createMessage(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(ctx.body(), Message.class);
        if (msg.getMessage_text() == null || msg.getMessage_text().isEmpty() || msg.getMessage_text().length() > 255) {
            ctx.status(400);
        } else {
            Message newMsg = messageService.createMessage(msg);
            if (newMsg != null) {
                ctx.json(mapper.writeValueAsString(newMsg));
            } else {
                ctx.status(400);
            }
        }
    }

    private void getMessages(Context ctx) {
        List<Message> messages = messageService.getMessages();
        ctx.json(messages);
    }

    private void getMessageById(Context ctx) throws JsonProcessingException {
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

    private void getMessagesByAccountId(Context ctx) throws JsonProcessingException {
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByAccountId(account_id);
        ctx.json(messages);
    }
}