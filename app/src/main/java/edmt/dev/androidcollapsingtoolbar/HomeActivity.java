package edmt.dev.androidcollapsingtoolbar;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import android.provider.Settings.Secure;

public class HomeActivity extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;



    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewPager_id);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //add the fragments to the adapter list
        adapter.addFragment(new OneFragment(), "Home");
        adapter.addFragment(new TwoFragment(), "Profile");
        adapter.addFragment(new ThreeFragment(), "Settings");

        mAuth = FirebaseAuth.getInstance();



        //implement the adapter
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout_id);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_lightbulb_outline_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_tag_faces_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_settings_black_24dp);





    }



}
