package edmt.dev.androidcollapsingtoolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by tomer on 14/01/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Firebase mRootRef;
    private FirebaseAuth mAuth;

    private List<Idea> ideas;
    private Context context;

    public RecyclerAdapter(Context context, List<Idea> ideas) {
        this.ideas = ideas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent,  false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        mRootRef = new Firebase("https://cyber-project-e74f3.firebaseio.com/");
        mAuth = FirebaseAuth.getInstance();

        final Idea sampleidea = ideas.get(position);
        holder.title.setText(sampleidea.ideatitle);
        holder.desc.setText(sampleidea.ideadesc);

        //Setting a circular image
        Bitmap bitmap = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.macos);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(this.context.getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        holder.image.setImageDrawable(roundedBitmapDrawable);


        //holder.image.setImageResource(sampleidea.ideaimage);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "item " + position + " clicked", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, createIdeaActivity.class);
                i.putExtra("IdeaTitle",sampleidea.ideatitle);
                context.startActivity(i);
            }
        });

        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Toast.makeText(context, "item long clicked", Toast.LENGTH_SHORT).show();
                deleteIdea(ideas.get(position));
                notifyItemRemoved(position);

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return ideas.size();
    }

    //Delete an idea from sampleidea and show the new list
    public void deleteIdea(Idea idea)
    {
        for(int i = 0; i<ideas.size(); i++)
        {
            if(ideas.get(i)==idea)
            {
                ideas.remove(i);
                saveIdeas(ideas);

            }
        }

        //Delete from Firebase
        Firebase mRefChild = mRootRef.child("Ideas");
        Firebase mDeleteThisRef = mRefChild.child("IdIs:"+idea.ideatitle.toString());
        mDeleteThisRef.removeValue();

        //Deleting from user's storage in Firebase
        mDeleteThisRef = mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).child("user_ideas").child("IdIs:"+idea.ideatitle.toString());
        mDeleteThisRef.removeValue();


        //Delete from "Name" (if this is Tomer)
        if(readUserInfo().auth==1) {
            Firebase mRefName = mRootRef.child("Name");

            Gson gson = new Gson();
            String json = gson.toJson((ArrayList)ideas);
            mRefName.setValue(json);
        }
    }

    //Save idea list
    public void saveIdeas(List<Idea> ideas)
    {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson((ArrayList)ideas);

        editor.putString(TAG, json);
        editor.commit();

    }

    //Read idea list from shared preferences
    public List<Idea> readIdeas()
    {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString(TAG, null);
        Type type = new TypeToken<ArrayList<Idea>>() {}.getType();
        ArrayList<Idea> arrayList = gson.fromJson(json, type);
        return (List<Idea>)arrayList;
    }

    //Read user info
    public User readUserInfo()
    {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("User", null);
        Type type = new TypeToken<User>() {}.getType();
        User user = gson.fromJson(json, type);
        return user;
    }

}
