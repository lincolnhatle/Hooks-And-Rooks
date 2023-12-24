package com.example.hooks_and_rooks;

import org.json.JSONException;
import org.json.JSONObject;


public class UserSingleton {

    private static UserSingleton instance;
    private JSONObject user;

    //do I pass a JSONObject as context
    private UserSingleton(JSONObject user) throws JSONException {
        this.user = user;
    }

    public static synchronized UserSingleton getInstance(JSONObject user) throws JSONException {
            instance = new UserSingleton(user);
        return instance;
    }

    public static synchronized UserSingleton getInstance() throws Exception {
        if (instance == null) {
            throw new Exception("Expected instance to be not null here");
        }
        return instance;
    }

    public static int getId() throws JSONException {
        //return user.getInt("id");
        return instance.user.getInt("id");
    }

    public static String getUsername() throws JSONException {
        return instance.user.getString("username");
    }
}
