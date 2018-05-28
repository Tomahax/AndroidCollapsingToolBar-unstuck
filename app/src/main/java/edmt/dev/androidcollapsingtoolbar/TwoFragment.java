package edmt.dev.androidcollapsingtoolbar;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tomer on 07/12/2017.
 */

public class TwoFragment extends Fragment {

    private FirebaseAuth mAuth;
    private Firebase mRootRef;
    private DatabaseReference mRefUsers;
    private FirebaseAuth.AuthStateListener mAuthListener;


    View view;

    private TextView mUserName;

    private Button mLogOut;

    private ImageView profileImageView;


    private RecyclerView allUsersList;

    private FirebaseRecyclerAdapter<User, UserViewHolder> firebaseRecyclerAdapter;
    private LinearLayoutManager mLinearLayoutManager;


    static RoundedBitmapDrawable roundedBitmapDrawable1;


    public TwoFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_2, container, false);

        mAuth = FirebaseAuth.getInstance();

        mUserName = (TextView) view.findViewById(R.id.user_profile_name);


        mLogOut = (Button) view.findViewById(R.id.log_out_profile);
        mUserName.setText(readUserInfo().user_name);

        mLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });


        //Circular image view
        profileImageView = (ImageView) view.findViewById(R.id.user_profile_photo);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.macos);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        profileImageView.setImageDrawable(roundedBitmapDrawable);


        TextView logoutText = (TextView) view.findViewById(R.id.log_out_profile);
//        logoutText.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                LinearLayout linear2 =(LinearLayout)view.findViewById(R.id.relative_layout_fragment_2);
//                ViewGroup.LayoutParams lprams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                mAuth.signOut();
//            }
//        });

        mRefUsers = FirebaseDatabase.getInstance().getReferenceFromUrl("https://cyber-project-e74f3.firebaseio.com/Users");

        firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<User, UserViewHolder>
                (
                        User.class,
                        R.layout.recycler_item_user,
                        UserViewHolder.class,
                        mRefUsers

                ) {
            @Override
            protected void populateViewHolder(UserViewHolder viewHolder, User model, int position) {

                viewHolder.startOnClickListener(model.user_name);

                viewHolder.setUser_Name(model.user_name);
                viewHolder.setUser_Color(model.user_color);
                viewHolder.setUser_Image(R.drawable.img_profile_circular_demo);

                //Toast.makeText(getActivity(),"Inside populateViewHolder",Toast.LENGTH_LONG).show();


            }

        };


        //Loads user profile image


        new Thread() {
            @Override
            public void run() {
                roundedBitmapDrawable1 = RoundedBitmapDrawableFactory.create(getResources(), getBitmapFromURL(mAuth.getCurrentUser().getPhotoUrl().toString()));
                roundedBitmapDrawable1.setCircular(true);
            }
        }.start();

        profileImageView.setImageDrawable(roundedBitmapDrawable1);


        //Loads Friends Recycle View
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        allUsersList = (RecyclerView) view.findViewById(R.id.friends_recycler);
        allUsersList.setHasFixedSize(true);
        allUsersList.setLayoutManager(mLinearLayoutManager);


        allUsersList.setAdapter(firebaseRecyclerAdapter);


        return view;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public UserViewHolder(final View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setUser_Name(String user_name) {
            TextView name = (TextView) mView.findViewById(R.id.title_textview);
            name.setText(user_name);
        }

        public void setUser_Color(String user_color) {
            TextView color = (TextView) mView.findViewById(R.id.desc_textview);
            color.setText(user_color);
        }

        public void setUser_Image(int resource) {
            ImageView image = (ImageView) mView.findViewById(R.id.imageview_id);
            image.setImageResource(resource);
        }

        public void startOnClickListener(String user_name) {
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setUser_Color("Blue");
                }
            });

        }

    }

    public String getUsername() {
        AccountManager manager = AccountManager.get(getContext());
        Account[] accounts = manager.getAccountsByType("com.google");
        List<String> possibleEmails = new LinkedList<String>();

        for (Account account : accounts) {
            // TODO: Check possibleEmail against an email regex or treat
            // account.name as an email address only for certain account.type values.
            possibleEmails.add(account.name);
        }

        if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
            String email = possibleEmails.get(0);
            String[] parts = email.split("@");

            if (parts.length > 1)
                return parts[0];
        }
        return null;
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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



