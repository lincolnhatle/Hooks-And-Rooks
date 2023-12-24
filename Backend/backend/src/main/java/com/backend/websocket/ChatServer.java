package com.backend.websocket;

import com.backend.Message.MessageRepository;
import com.backend.Message.Message;
import jakarta.websocket.OnOpen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Represents a WebSocket chat server for handling real-time communication
 * between users. Each user connects to the server using their unique
 * username.
 *
 * This class is annotated with Spring's `@ServerEndpoint` and `@Component`
 * annotations, making it a WebSocket endpoint that can handle WebSocket
 * connections at the "/chat/{username}" endpoint.
 *
 * Example URL: ws://localhost:8080/chat/username
 *
 * The server provides functionality for broadcasting messages to all connected
 * users and sending messages to specific users.
 */


@ServerEndpoint("/chat/{username}")
@Component
public class ChatServer {

    // Store all socket session and their corresponding username
    // Two maps for the ease of retrieval by key
    private static Map < Session, String > sessionUsernameMap = new Hashtable < > ();
    private static Map < String, Session > usernameSessionMap = new Hashtable < > ();

    // server side logger
    private final Logger logger = LoggerFactory.getLogger(ChatServer.class);


    private static MessageRepository messageRepository;

    @Autowired
    public void setMessageRepository(MessageRepository repo) {
        messageRepository = repo;
    }

    /**
     * This method is called when a new WebSocket connection is established.
     *
     * @param session represents the WebSocket session for the connected user.
     * @param username username specified in path parameter.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {

        // Set read receipts
        List<Message> messages = messageRepository.findAll();
        for (Message message : messages) {
            if ((message.getRecipient() == null) && !(message.isRead())) {
                message.setRead(true);
                messageRepository.save(message);
            }
            else if (message.getRecipient() != null) {
                if (message.getRecipient().equals(username) && !(message.isRead())) {
                    message.setRead(true);
                    messageRepository.save(message);
                }
            }
        }
        // server side log
        logger.info("[Chat onOpen] " + username);

        // Handle the case of a duplicate username
        if (usernameSessionMap.containsKey(username)) {
            session.getBasicRemote().sendText("Username already exists");
            session.close();
        }
        else {
            // map current session with username
            sessionUsernameMap.put(session, username);

            // map current username with session
            usernameSessionMap.put(username, session);

            sendMessageToPArticularUser(username, getChatHistory());

            // send to the user joining in
            sendMessageToPArticularUser(username, "Welcome to the chat server, "+username);

            // send to everyone in the chat
            broadcast("User: " + username + " has Joined the Chat");
        }
    }

    /**
     * Handles incoming WebSocket messages from a client.
     *
     * @param session The WebSocket session representing the client's connection.
     * @param message The message received from the client.
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {

        // get the username by session
        String username = sessionUsernameMap.get(session);

        // server side log
        logger.info("[Chat onMessage] " + username + ": " + message);

        // Direct message to a user using the format "@username <message>"
        if (message.startsWith("@")) {

            // split by space
            String[] split_msg =  message.split("\\s+");

            // Combine the rest of message
            StringBuilder actualMessageBuilder = new StringBuilder();
            for (int i = 1; i < split_msg.length; i++) {
                actualMessageBuilder.append(split_msg[i]).append(" ");
            }
            String destUserName = split_msg[0].substring(1);    //@username and get rid of @
            String actualMessage = actualMessageBuilder.toString();
            sendMessageToPArticularUser(destUserName, "[DM from " + username + "]: " + actualMessage);
            sendMessageToPArticularUser(username, "[DM from " + username + "]: " + actualMessage);

            Session destUserSession = usernameSessionMap.get(destUserName);
            boolean messageIsRead;
            messageIsRead = destUserSession.isOpen();

            //Store message in DB
            Message dbMessage = new Message(message, messageIsRead, username, destUserName);
            messageRepository.save(dbMessage);
        }
        else { // Message to whole chat
            broadcast(username + ": " + message);

            boolean messageIsRead = false;

            for (Session seshIter : usernameSessionMap.values()) {
                if (seshIter.isOpen() && !(sessionUsernameMap.get(seshIter).equals(username))) {
                    messageIsRead = true;
                    break;
                }
            }

            // Store message in DB
            Message dbMessage = new Message(message, messageIsRead, username, null);
            messageRepository.save(dbMessage);
        }
    }

    /**
     * Handles the closure of a WebSocket connection.
     *
     * @param session The WebSocket session that is being closed.
     */
    @OnClose
    public void onClose(Session session) throws IOException {

        // get the username from session-username mapping
        String username = sessionUsernameMap.get(session);

        // server side log
        logger.info("[onClose] " + username);

        // remove user from memory mappings
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);

        // send the message to chat
        broadcast(username + " disconnected");
    }

    /**
     * Handles WebSocket errors that occur during the connection.
     *
     * @param session   The WebSocket session where the error occurred.
     * @param throwable The Throwable representing the error condition.
     */
    @OnError
    public void onError(Session session, Throwable throwable) {

        // get the username from session-username mapping
        String username = sessionUsernameMap.get(session);

        // do error handling here
        logger.info("[onError]" + username + ": " + throwable.getMessage());
    }

    /**
     * Sends a message to a specific user in the chat (DM).
     *
     * @param username The username of the recipient.
     * @param message  The message to be sent.
     */
    private void sendMessageToPArticularUser(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.info("[DM Exception] " + e.getMessage());
        }
    }

    /**
     * Broadcasts a message to all users in the chat.
     *
     * @param message The message to be broadcasted to all users.
     */
    private void broadcast(String message) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.info("[Broadcast Exception] " + e.getMessage());
            }
        });
    }

    private String getChatHistory() {
        List<Message> messages = messageRepository.findAll();

        // convert the list to a string
        StringBuilder sb = new StringBuilder();
        if(messages != null && messages.size() != 0) {
            for (Message message : messages) {
                String boolToReadString;
                if (message.isRead()) {
                    boolToReadString = "Read";
                }
                else {
                    boolToReadString = "Not Read";
                }
                sb.append(message.getSender() + ": " + message.getData() + " // " +  boolToReadString + "\n");
            }
        }
        return sb.toString();
    }

}