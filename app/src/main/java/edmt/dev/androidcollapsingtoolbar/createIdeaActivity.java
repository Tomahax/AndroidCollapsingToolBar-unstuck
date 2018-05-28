package edmt.dev.androidcollapsingtoolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class createIdeaActivity extends AppCompatActivity {

    private EditText mEditText;

    private Firebase mRootRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private StorageReference mStorage;

    private Bundle IdeaNameData;

    private User currentUser;

    public static Idea thisIdea;

    private String IdeaNameString;

    private ToggleButton toggleButton;

    boolean ManualMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_idea);

        mEditText = (EditText) findViewById(R.id.title_edit_text);
        mEditText.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);

        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

        mRootRef = new Firebase("https://cyber-project-e74f3.firebaseio.com/");
        mStorage = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        currentUser = readUserInfo();

        IdeaNameData = getIntent().getExtras();
        IdeaNameString = IdeaNameData.getString("IdeaTitle");

        //Turning Manual Mode off
        ManualMode = false;

        //Find the Idea
        List<Idea> Ideas = readIdeas();
        for (int i = 0; i < Ideas.size(); i++) {
            if (Ideas.get(i).ideatitle.equals(IdeaNameString)) {
                thisIdea = Ideas.get(i);
                break;

            }
        }


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(createIdeaActivity.this, MainActivity.class));
                }
            }
        };


        //InApp text field updater
        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //mEditText.setTextColor(Color.parseColor(currentUser.user_color));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (mEditText.getText() != null) {
//                    String value = mEditText.getText().toString();
//                    Firebase childRef = mRootRef.child("Ideas");
//
//                    //Set color for text (uncomment!!!!!!!!!!!!!!!!!!!!!!!!!!)
////                    SpannableString text = new SpannableString(value);
////                    text.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, 0);
////                    mEditText.setText(text, TextView.BufferType.SPANNABLE);
//                    // mEditText.setTextColor(Color.parseColor(currentUser.user_color));
//
//                    //Save to Idea
//                    thisIdea.ideacontent = value;
//
//                    //Save Idea content to Firebase
//                    Firebase currentIdea = childRef.child("IdIs:" + thisIdea.ideatitle.toString());
//                    Firebase RefContent = currentIdea.child("Content");
//                    RefContent.setValue(value);
//
//                    //Save to user part of storage
//                    RefContent = mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).child("user_ideas").child("IdIs:" + IdeaNameString).child("Content");
//                    RefContent.setValue(value);
                }

            }
        });

        //Realtime drive updater
        final Handler ha = new Handler();
        ha.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mEditText.getText() != null && !ManualMode) {
                    String value = mEditText.getText().toString();
                    Firebase childRef = mRootRef.child("Ideas");

                    //Set color for text (uncomment!!!!!!!!!!!!!!!!!!!!!!!!!!)
//                    SpannableString text = new SpannableString(value);
//                    text.setSpan(new ForegroundColorSpan(Color.RED), 0, 1, 0);
//                    mEditText.setText(text, TextView.BufferType.SPANNABLE);
//                    mEditText.setTextColor(Color.parseColor(currentUser.user_color));

                    //Save to Idea
                    thisIdea.ideacontent = value;

                    //Save Idea content to Firebase
                    Firebase currentIdea = childRef.child("IdIs:" + thisIdea.ideatitle.toString());
                    Firebase RefContent = currentIdea.child("Content");
                    RefContent.setValue(value);

                    //Save to user part of storage
                    RefContent = mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).child("user_ideas").child("IdIs:" + IdeaNameString).child("Content");
                    RefContent.setValue(value);

                }
                ha.postDelayed(this, 1000);
            }
        }, 1000);


        //Manual Mode toggleButton listener
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleButton.isChecked()) {
                    ManualMode = true;
                } else {
                    ManualMode = false;
                }
            }
        });


    }

    //Receives realtime data from database and changes onscreen text

    private void showData(DataSnapshot dataSnapshot) {
        mEditText.setText(dataSnapshot.child("Ideas").child("IdIs:" + thisIdea.ideatitle.toString()).child("Content").getValue().toString());
        mEditText.setSelection(mEditText.length());

    }

    //Read idea list from shared preferences
    public ArrayList<Idea> readIdeas() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString(TAG, null);
        Type type = new TypeToken<ArrayList<Idea>>() {
        }.getType();
        ArrayList<Idea> arrayList = gson.fromJson(json, type);
        return arrayList;
    }

    //Read user info
    public User readUserInfo() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("User", null);
        Type type = new TypeToken<User>() {
        }.getType();
        User user = gson.fromJson(json, type);
        return user;
    }


    public void manualSave(View view) {
        String value = mEditText.getText().toString();
        Firebase childRef = mRootRef.child("Ideas");

        //Save to Idea
        thisIdea.ideacontent = value;

        //Save Idea content to Firebase
        Firebase currentIdea = childRef.child("IdIs:" + thisIdea.ideatitle.toString());
        Firebase RefContent = currentIdea.child("Content");
        RefContent.setValue(value);

        //Save to user part of storage
        RefContent = mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).child("user_ideas").child("IdIs:" + IdeaNameString).child("Content");
        RefContent.setValue(value);
    }


}
