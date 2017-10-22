package edmt.dev.androidcollapsingtoolbar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

import static edmt.dev.androidcollapsingtoolbar.R.id.time;


public class sqlActivity extends AppCompatActivity {

    private Button mSendData;
    private EditText valueField;

    private Firebase mRootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);


        mRootRef = new Firebase("https://cyber-project-e74f3.firebaseio.com/");

        mSendData = (Button) findViewById(R.id.sendButton);
        valueField = (EditText) findViewById(R.id.valueField);


        mSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* int counter = time/1000;
                Firebase mRefChild = mRef.child("Name"+counter);
                mRefChild.setValue("Tomer"); */

                String value = valueField.getText().toString();
                Firebase childRef = mRootRef.child("Name");
                childRef.setValue(value);
            }
        });
    }

/*   public void addValue(View view)
    {
        AndroidCollapsingToolBar mRefChild = mRef.child("Name");
        mRefChild.setValue("Tomer");
 */ }

