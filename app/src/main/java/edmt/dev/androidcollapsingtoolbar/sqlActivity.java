package edmt.dev.androidcollapsingtoolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static java.lang.Thread.sleep;

import static edmt.dev.androidcollapsingtoolbar.R.id.time;


public class sqlActivity extends AppCompatActivity {

    private Button mSendData;
    private Button mSelectImage;
    private Button mLogOut;
    private Button mUpdateName;
    private Button mHome;
    private EditText valueField;
    private TextView mTextView;

    private Firebase mRootRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private StorageReference mStorage;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);


        mRootRef = new Firebase("https://cyber-project-e74f3.firebaseio.com/");
        mStorage = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        mSendData = (Button) findViewById(R.id.sendButton);
        mSelectImage = (Button) findViewById(R.id.selectImage);
        mLogOut = (Button) findViewById(R.id.logOut);
        mUpdateName = (Button) findViewById(R.id.updateName);
        mHome = (Button) findViewById(R.id.home);
        valueField = (EditText) findViewById(R.id.valueField);
        mTextView = (TextView) findViewById(R.id.textView);

//        InApp text field updater
        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null)
                {
                    startActivity(new Intent(sqlActivity.this, MainActivity.class));
                }
            }
        };

        mLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });

        //Pictures stuff
//        mSelectImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, 2);
//            }
//        });

        //Realtime drive updater
        final Handler ha = new Handler();
        ha.postDelayed(new Runnable() {
            @Override
            public void run() {
                String value = valueField.getText().toString();
                Firebase childRef = mRootRef.child("Name");
                childRef.setValue(value); // Hello Tomer !!!
                ha.postDelayed(this, 500);
            }
        }, 500);

/*
        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value1= dataSnapshot.getValue(String.class);
                mSendData.setText(value1.toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
*/


       /*  mSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counter = time/1000;
                Firebase mRefChild = mRootRef.child("Name"+counter);
                mRefChild.setValue("Tomer");


                String value = valueField.getText().toString();
                Firebase childRef = mRootRef.child("Tomer");
                childRef.setValue(value);


               mSendData.setText("pressed");
            }
        });
        */
    }

    //Receives realtime data from database and changes onscreen text
    private void showData(DataSnapshot dataSnapshot) {
        mTextView.setText(dataSnapshot.child("Name").getValue().toString());
        valueField.setText(dataSnapshot.child("Name").getValue().toString());
        valueField.setSelection(valueField.length());


    }

    //Images stuff
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK)
        {
            Uri uri = data.getData();

            StorageReference filepath = mStorage.child("Photos").child(uri.getLastPathSegment());

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(sqlActivity.this, "Upload Done", Toast.LENGTH_LONG).show();
                }
            });
        }


    }

    //Button add value onClick
    public void addValue(View view) {
        String value = valueField.getText().toString();
        //Firebase mRefChild = mRootRef.child("Name");
        //mRefChild.setValue("Tomer");
        mRootRef.push().setValue(value);


    }

    //Instant update
    public void instantU(View view)
    {
        String value = valueField.getText().toString();
        Firebase childRef = mRootRef.child("Name");
        childRef.setValue(value);
    }

    //onClick for going to home page
    public void onClickHome(View view)
    {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}

