package edmt.dev.androidcollapsingtoolbar;

import android.media.Image;

/**
 * Created by tomer on 15/03/2018.
 */



public class User {
    public String identifier;
    public String user_color;
    public int auth;
    public String user_name;
    public String user_status;
    public Image user_image;

    public User(String identifier, String color, int auth, String name) {
        this.user_name = name;

        this.identifier = identifier;
        this.user_color = color;
        this.auth = auth;
    }

    public User()
    {}

    @Override
    public String toString() {
        return "User{" +
                "identifier='" + identifier + '\'' +
                ", color='" + user_color + '\'' +
                ", auth=" + auth +
                ", name='" + user_name + '\'' +
                '}';
    }
}
