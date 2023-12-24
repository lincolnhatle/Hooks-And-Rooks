package com.backend.websocket;

import com.backend.Match.MatchRepository;
import com.backend.Move.*;

import com.backend.Punch.Punch;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

@ServerEndpoint("/moves/{username}/{opponent}")
@Component
public class MoveServer {

    private static Map< Session, String > sessionUsernameMap = new Hashtable< >();
    private static Map < String, Session > usernameSessionMap = new Hashtable < > ();
    private static Map < String, String > matchMap = new Hashtable < > ();

    private final Logger logger = LoggerFactory.getLogger(MoveServer.class);

    private static MoveRepository moveRepository;
    private static MatchRepository matchRepository;

    @Autowired
    public void setMoveRepository(MoveRepository repo) {
        moveRepository = repo;
    }

    @Autowired
    public void setMoveRepository(MatchRepository repo) { matchRepository = repo; }

    @OnOpen
    public void onOpen(Session session,  @PathParam("username") String username, @PathParam("opponent") String opponent){

        sessionUsernameMap.put(session, username);

        // map current username with session
        usernameSessionMap.put(username, session);

        matchMap.put(username, opponent);

        logger.info("[Move onOpen] " + username);

    }

    @OnMessage
    public void onMessage(Session session, String move) throws IOException {

        String user = sessionUsernameMap.get(session);
        String opponent = matchMap.get(user);

        logger.info("[Move onMessage] Move Received");
        Scanner scanner = new Scanner(move);

        int moveType = scanner.nextInt();
        if (moveType == 1) { // If chess move type
            String startSpace = scanner.next();
            String endSpace = scanner.next();

            Move moveObj = new Move(user, startSpace, endSpace);
            String moveString = "1 " + startSpace + " " + endSpace;

            moveRepository.save(moveObj);

            sendChessMoveToParticularUser(opponent, moveString);
            logger.info("[Move onMessage] Move Sent");

        }
        else if (moveType == 0) { // If punch move type
            int punchType = scanner.nextInt();

            sendPunchToParticularUser(opponent, punchType);
            logger.info("[Move onMessage] Move Sent");
        }

    }

    @OnClose
    public void onClose(Session session){

        String username = sessionUsernameMap.get(session);

        matchMap.remove(username);
        sessionUsernameMap.remove(session);
        sessionUsernameMap.remove(username);

        logger.info("[Move onClose] " + username);
    }

    @OnError
    public void onError(Session session,  Throwable throwable){
        logger.info("[Move onError]" + throwable);
    }

    private void sendChessMoveToParticularUser(String username, String move) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(move);
        } catch (IOException e) {
            logger.info("[Move Exception] " + e.getMessage());
        }
    }

    private void sendPunchToParticularUser(String username, int punch) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendObject(punch);
        } catch (IOException e) {
            logger.info("[Move Exception] " + e.getMessage());
        } catch (EncodeException e) {
            throw new RuntimeException(e);
        }
    }

}
