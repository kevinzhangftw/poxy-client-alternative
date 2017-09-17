package com.example.eric.mapactivity2.Networking;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by kai on 2017-07-16.
 * Stores user memory, token, user id, park, a
 * anything app has to remember
 * cache store fore token
 */

public class UserState {
    private static Badge userBadge;

    public static Badge getBadge(Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);

        if (pref.getFloat("user_id", 0) != 0){
            userBadge = new Badge(pref.getFloat("user_id", 0), pref.getString("session_token", null));
        }

        return userBadge;
    }

    public static void setBadge(Badge badgeTobeSaved, Context context){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor e = pref.edit();
        e.putFloat("user_id", badgeTobeSaved.getUser_id());
        e.putString("session_token", badgeTobeSaved.getSession_token());
        e.commit();
    }
}
