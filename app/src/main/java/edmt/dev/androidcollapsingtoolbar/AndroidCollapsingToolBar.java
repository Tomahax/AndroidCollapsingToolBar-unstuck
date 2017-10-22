package edmt.dev.androidcollapsingtoolbar;

import android.app.Application;
import com.firebase.client.Firebase;

/**
 * Created by tomer on 22/10/2017.
 */

public class AndroidCollapsingToolBar extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);


    }
}
