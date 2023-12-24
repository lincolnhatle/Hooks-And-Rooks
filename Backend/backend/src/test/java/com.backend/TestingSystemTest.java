package com.backend;

import com.backend.Match.Match;
import com.backend.Match.MatchRepository;
import com.backend.Player.Player;
import com.backend.Player.PlayerRepository;
import com.backend.Users.Users;
import com.backend.Users.UsersRepository;
import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import io.restassured.response.Response;
import org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.server.LocalServerPort;	// SBv3
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)


@RunWith(SpringRunner.class)
public class TestingSystemTest {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    PlayerRepository playerRepository;

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void usersGetTests() {

        //Test1
        Response response = (Response) RestAssured
                .with()
                .request("GET", "/users");

        int statuscode = response.getStatusCode();

        assertEquals(200, statuscode);

        //Test 2
        response = (Response) RestAssured
                .with()
                .request("GET", "/users/1");

        statuscode = response.getStatusCode();

        assertEquals(200, statuscode);
    }

    @Test
    public void friendsTest() {
        Users testUser1 = new Users("TESTUSER1", "TESTPASS1");
        Users testUser2 = new Users("TESTUSER2", "TESTPASS2");
        usersRepository.save(testUser1);
        usersRepository.save(testUser2);

        String requestString = "/users/friendRequest/" + testUser2.getId();

        Response response = (Response) RestAssured
                .with()
                .header("Content-Type", "application/json")
                .header("charset","utf-8")
                .body(testUser1)
                .request("PUT", requestString);

        int statuscode = response.getStatusCode();

        assertEquals(200, statuscode);
    }

    @Test
    public void matchTest() {
        Users matchTestUser1 = new Users("MATCHTESTUSER1", "TESTPASS1");
        Users matchTestUser2 = new Users ("MATCHTESTUSER1", "TESTPASS2");

        usersRepository.save(matchTestUser1);
        usersRepository.save(matchTestUser2);


        Player matchPlayer1 = new Player(matchTestUser1);
        Player matchPlayer2 = new Player(matchTestUser2);

        playerRepository.save(matchPlayer2);
        playerRepository.save(matchPlayer1);


        Match testMatch = new Match(matchPlayer1, matchPlayer2);
        matchRepository.save(testMatch);

        Response response = (Response) RestAssured
                .with()
                .request("GET", "match/" + testMatch.getId());

        int statuscode = response.getStatusCode();

        assertEquals(200, statuscode);
    }

    @Test
    public void messageGetTest() {
        Response response = (Response) RestAssured
                .with()
                .request("GET", "/messages");

        int statuscode = response.getStatusCode();

        assertEquals(200, statuscode);

    }

    @Test
    public void getAllMatchTest() {
        Response response = (Response) RestAssured
                .with()
                .request("GET", "/match");

        int statuscode = response.getStatusCode();

        assertEquals(200, statuscode);
    }

    @Test
    public void matchmakingTest() {
        Users matchmakingTestUser = new Users("MMTESTUSER", "MMTESTPASS");
        Users matchmakingTestUser2 = new Users("MMTESTUSER2", "MMTESTPASS2");

        Response response = (Response) RestAssured
                .with()
                .header("Content-Type", "application/json")
                .header("charset","utf-8")
                .body(matchmakingTestUser)
                .request("POST", "/matchmaking");

        Response response2 = (Response) RestAssured
                .with()
                .header("Content-Type", "application/json")
                .header("charset","utf-8")
                .body(matchmakingTestUser2)
                .request("GET", "/matchmaking");


        int statuscode = response.getStatusCode();
        int statuscode2 = response2.getStatusCode();

        assertEquals(200, statuscode);
        assertEquals(200, statuscode2);
        //assertNull(response.getBody());

    }

}
