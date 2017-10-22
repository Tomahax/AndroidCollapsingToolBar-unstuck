package edmt.dev.androidcollapsingtoolbar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;







    public class ProfileActivity extends AppCompatActivity {
        Button click;
        Button button;


        public  static TextView data;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);

            click = (Button) findViewById(R.id.button);
            data = (TextView) findViewById(R.id.fetcheddata);
           


          click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fetchData process = new fetchData();
                    process.execute();
                }
            });

        }

    public void onClick(View view)
    {
        click.setText("pressedOne");
    }


}
