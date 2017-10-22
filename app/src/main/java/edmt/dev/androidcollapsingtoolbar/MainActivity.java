package edmt.dev.androidcollapsingtoolbar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.button);
        final Button sql_button = (Button) findViewById(R.id.button2);


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void onClick(View view)
    {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    public void clickSql(View view)
    {
        Intent i = new Intent(this, sqlActivity.class);
        startActivity(i);
    }
}
