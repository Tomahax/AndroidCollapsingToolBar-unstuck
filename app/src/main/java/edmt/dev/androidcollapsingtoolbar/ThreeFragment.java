package edmt.dev.androidcollapsingtoolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.ContentValues.TAG;
import static android.provider.Telephony.Mms.Part.FILENAME;

/**
 * Created by tomer on 07/12/2017.
 */

public class ThreeFragment extends Fragment {

    View view;
    RecyclerView recyclerView;

    EditText editText;

    Button mOkChangeColor;

    public ThreeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_3, container, false);

        editText =(EditText) view.findViewById(R.id.ColorChangeField);

        mOkChangeColor = (Button) view.findViewById(R.id.okChangeColor);

        mOkChangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = readUserInfo();
                user.user_color = editText.getText().toString();
                saveUserInfo(user);
            }
        });

//        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_id_3);
//
//         final List<Idea> sampleidea = new ArrayList<>();
//
//        for (int i=0; i<title.length; i++){
//
//            Idea idea = new Idea();
//
//            idea.ideatitle = title[i];
//            idea.ideadesc = desc[i];
//            idea.ideaimage = image[i];
//
//            sampleidea.add(idea);
//
//        }
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(new RecyclerAdapter(getContext() ,sampleidea));

//        //Saving to Shared Preferences
//        saveIdeas(sampleidea);
//        //Reading from Shared Preferences
//        readIdeas();

        //Test
//        Button button = (Button) view.findViewById(R.id.add_item_to_recycler_button);
//        button.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                RelativeLayout relative1 =(RelativeLayout) view.findViewById(R.id.relative_layout_fragment_3);
//                ViewGroup.LayoutParams lprams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                Idea a = new Idea();
//                a.ideatitle = "test";
//                a.ideadesc = "testing the desc";
//                a.ideaimage = R.drawable.macos;
//                sampleidea.add(a);
//                recyclerView.setAdapter(new RecyclerAdapter(getContext(), sampleidea));
//            }
//        });




        return view;
    }





    //Save idea list
    public void saveIdeas(List<Idea> ideas)
    {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson((ArrayList)ideas);

        editor.putString(TAG, json);
        editor.commit();

    }

    //Read idea list from shared preferences
    public void readIdeas()
    {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString(TAG, null);
        Type type = new TypeToken<ArrayList<Idea>>() {}.getType();
        ArrayList<Idea> arrayList = gson.fromJson(json, type);
        Toast.makeText(getActivity(),arrayList.get(1).ideatitle,Toast.LENGTH_SHORT).show();
    }


    //Save user info locally
    public void saveUserInfo(User user) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson((User) user);

        editor.putString("User", json);
        editor.commit();

        //Save user color to firebase
        Firebase mRootRef = new Firebase("https://cyber-project-e74f3.firebaseio.com/");
        Firebase mRefUsers = mRootRef.child("Users");
        Firebase mRefUser = mRefUsers.child(user.identifier);
        Firebase mRefUserColor = mRefUser.child("user_color");
        mRefUserColor.setValue(user.user_color);
    }

    //Read user info
    public User readUserInfo() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("User", null);
        Type type = new TypeToken<User>() {
        }.getType();
        User user = gson.fromJson(json, type);
        return user;
    }

}
