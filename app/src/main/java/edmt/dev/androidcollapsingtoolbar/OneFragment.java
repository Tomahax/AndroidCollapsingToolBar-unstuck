package edmt.dev.androidcollapsingtoolbar;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static edmt.dev.androidcollapsingtoolbar.R.id.time;

import static android.content.ContentValues.TAG;

/**
 * Created by tomer on 07/12/2017.
 */



public class OneFragment extends Fragment {

    //test (can delete if doesn't work)
    RelativeLayout rLayout;

    private Firebase mRootRef;


    View view;
    RecyclerView recyclerView;

    String [] title = {

            "Title1",
            "Title2",
            "Title3",
            "Title4",
            "Title5",
            "Title6",
            "Title7",
            "Title8",
            "Title9",
            "Title10",
            "Title11",

    };

    String [] desc = {
            "Description1",
            "Description2",
            "Description3",
            "Description4",
            "Description5",
            "Description6",
            "Description7",
            "Description8",
            "Description9",
            "Description10",
            "Description11",

    };

    int [] image = {

            R.drawable.macos,
            R.drawable.macos,
            R.drawable.macos,
            R.drawable.macos,
            R.drawable.macos,
            R.drawable.macos,
            R.drawable.macos,
            R.drawable.macos,
            R.drawable.macos,
            R.drawable.macos,
            R.drawable.macos,
    };

    public OneFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_1, container, false);

        mRootRef = new Firebase("https://cyber-project-e74f3.firebaseio.com/");



//        User test_user= new User();
//        test_user.color = "#FFFFFF";
//        test_user.auth = 0;
//        test_user.identifier = "asdjJ5jJAsd5";
//
//        saveUserInfo(test_user);
//        Toast.makeText(getActivity(),readUserInfo().toString(),Toast.LENGTH_SHORT).show();



//        LinearLayout relative1 =(LinearLayout)view.findViewById(R.id.relative_layout_items_1);
//
//        ViewGroup.LayoutParams lprams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        Button tv1 = new Button(getContext());
//        tv1.setText("Hello");
//        tv1.setLayoutParams(lprams);
//        tv1.setId(View.generateViewId());
//        relative1.addView(tv1);
//
//
//
//        Button button = (Button) view.findViewById(R.id.add_idea_button);
//        button.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                LinearLayout relative1 =(LinearLayout)view.findViewById(R.id.relative_layout_items_1);
//                ViewGroup.LayoutParams lprams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                Button tv2 = new Button(getContext());
//                tv2.setText("Hello");
//                tv2.setLayoutParams(lprams);
//                tv2.setId(View.generateViewId());
//                relative1.addView(tv2);
//            }
//        });



        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_id_1);

        final List<Idea> sampleidea = new ArrayList<>();

        if(readIdeas()!=null) {
            for (int i = 0; i < readIdeas().size(); i++) {

                Idea idea = new Idea();

                idea.ideatitle = readIdeas().get(i).ideatitle;
                idea.ideadesc = readIdeas().get(i).ideadesc;
                idea.ideaimage = readIdeas().get(i).ideaimage;

                sampleidea.add(idea);

            }
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);
        //uncomment the following line if turns out not to work
        //recyclerView.setAdapter(new RecyclerAdapter(getContext() ,sampleidea));


        recyclerView.setAdapter(new RecyclerAdapter(getContext() ,sampleidea));

//        //Saving to Shared Preferences
//        saveIdeas(sampleidea);
//        //Reading from Shared Preferences
//        readIdeas();

        //Test
        Button button = (Button) view.findViewById(R.id.add_idea_button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                //Uncross for fragment 3
//                RelativeLayout relative1 =(RelativeLayout) view.findViewById(R.id.relative_layout_fragment_3);
//                ViewGroup.LayoutParams lprams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
////                Idea a = new Idea();
////                a.ideatitle = "test";
////                a.ideadesc = "testing the desc";
////                a.ideaimage = R.drawable.macos;
////                sampleidea.add(a);
////                saveIdeas(sampleidea);
//                //recyclerView.setAdapter(new RecyclerAdapter(getContext(), sampleidea));
//                recyclerView.setAdapter(new RecyclerAdapter(getContext(), sampleidea));

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                LayoutInflater mInflater = LayoutInflater.from(getActivity());
                View mView = mInflater.inflate(R.layout.dialog_add_idea, null);
                final EditText dialogTitle = (EditText) mView.findViewById(R.id.fieldTitle);
                final EditText dialogDescription = (EditText) mView.findViewById(R.id.fieldDescription);
                Button createIdeaButton = (Button) mView.findViewById(R.id.dialogAddIdeaButton);

                //creating the dialog
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                //dialog onClickListener
                createIdeaButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Create Custom Idea
                        Idea a = new Idea();
                        User CurrentUser = readUserInfo();
                        a.ideatitle = dialogTitle.getText().toString();
                        a.ideadesc = dialogDescription.getText().toString();
                        a.ideaimage = R.drawable.macos;
                        if(a.users == null)
                            {a.users = new ArrayList<User>();}

                        a.users.add(new User(CurrentUser.identifier,CurrentUser.user_color, CurrentUser.auth, CurrentUser.user_name));
                        sampleidea.add(a);
                        saveIdeas(sampleidea);
                        //recyclerView.setAdapter(new RecyclerAdapter(getContext(), sampleidea));
                        recyclerView.setAdapter(new RecyclerAdapter(getContext(), sampleidea));
                        dialog.dismiss();

                        //Adding new idea to Firebase
                        long counter =  System.currentTimeMillis() % 1000;
                        Firebase mRefChild = mRootRef.child("Ideas");
                        Firebase currentIdea = mRefChild.child("IdIs:"+dialogTitle.getText());
                        Firebase RefTitle = currentIdea.child("Title");
                        Firebase RefDesc = currentIdea.child("Desc");
                        Firebase RefContent = currentIdea.child("Content");
                        RefContent.setValue("");
                        RefTitle.setValue(a.ideatitle = dialogTitle.getText().toString());
                        RefDesc.setValue(a.ideadesc = dialogDescription.getText().toString());
                        a.ideakey = currentIdea.getKey();



                    }
                });



            }
        });

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

    //Save user info locally
    public void saveUserInfo(User user)
    {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson((User)user);

        editor.putString("User", json);
        editor.commit();
    }

    //Read idea list from shared preferences
    public ArrayList<Idea> readIdeas()
    {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString(TAG, null);
        Type type = new TypeToken<ArrayList<Idea>>() {}.getType();
        ArrayList<Idea> arrayList = gson.fromJson(json, type);
        return arrayList;
    }

    //Read user info`
    public User readUserInfo()
    {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        String json = sharedPrefs.getString("User", null);
        Type type = new TypeToken<User>() {}.getType();
        User user = gson.fromJson(json, type);
        return user;
    }


    //Delete an idea from sampleidea and show the new list
    public ArrayList<Idea> deleteIdea(ArrayList<Idea> ideas, Idea idea)
    {
        for(int i = 0; i<ideas.size(); i++)
        {
            if(ideas.get(i)==idea)
            {
                ideas.remove(i);
                return ideas;
            }
        }
        return null;
    }


}
