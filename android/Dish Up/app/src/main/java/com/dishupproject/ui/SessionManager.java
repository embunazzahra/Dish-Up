package com.dishupproject.ui;

import android.content.Context;
import android.content.SharedPreferences;

import com.dishupproject.data.model.User;

/**
 * This is class for managing login session.
 */
public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session user";

    public SessionManager(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(User user){
        int id = user.getUser_id();
        editor.putInt(SESSION_KEY, id).commit();
    }

    public int getSession(){
        //return user id whose session is saved
        return sharedPreferences.getInt(SESSION_KEY,-1);
    }

    public void removeSession(){
        editor.putInt(SESSION_KEY,-1).commit();
    }

}
