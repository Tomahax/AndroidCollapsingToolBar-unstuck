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
import android.widget.EditText;

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

    public static Idea thisIdea;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_idea);

        mEditText = (EditText) findViewById(R.id.title_edit_text);
        mEditText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);

        mRootRef = new Firebase("https://cyber-project-e74f3.firebaseio.com/");
        mStorage = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        IdeaNameData = getIntent().getExtras();
        final String IdeaNameString = IdeaNameData.getString("IdeaTitle");



        //Find the Idea
        List<Idea> Ideas= readIdeas();
        for (int i = 0; i<Ideas.size(); i++)
        {
            if (Ideas.get(i).ideatitle.equals(IdeaNameString ))
            {
                thisIdea = Ideas.get(i);
                break;

            }
        }


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
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(createIdeaActivity.this, MainActivity.class));
                }
            }
        };


        //Realtime drive updater
        final Handler ha = new Handler();
        ha.postDelayed(new Runnable() {
            @Override
            public void run() {
                String value = mEditText.getText().toString();
                Firebase childRef = mRootRef.child("Ideas");


                //Save to Idea
                thisIdea.ideacontent = value;

                //Save Iea content to Firebase
                Firebase currentIdea = childRef.child(thisIdea.ideatitle.toString());
                Firebase RefDesc = currentIdea.child("Desc");

                RefDesc.setValue(value);


                ha.postDelayed(this, 500);
            }
        }, 500);


    }

    //Receives realtime data from database and changes onscreen text
    private void showData(DataSnapshot dataSnapshot) {
        mEditText.setText(dataSnapshot.child("Name").getValue().toString());
        mEditText.setSelection(mEditText.length());
    }

    //Read idea list from shared preferences
    public ArrayList<Idea> readIdeas()
    {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString(TAG, null);
        Type type = new TypeToken<ArrayList<Idea>>() {}.getType();
        ArrayList<Idea> arrayList = gson.fromJson(json, type);
        return arrayList;
    }

}
