package edmt.dev.androidcollapsingtoolbar;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tomer on 07/12/2017.
 */

public class TwoFragment extends Fragment {

    private FirebaseAuth mAuth;
    private Firebase mRootRef;
    private DatabaseReference mRefUsers;


    View view;

    private TextView mUserName;

    private RecyclerView allUsersList;

    private FirebaseRecyclerAdapter<User, UserViewHolder> firebaseRecyclerAdapter;
    private LinearLayoutManager mLinearLayoutManager;


    public TwoFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_2, container, false);

        mAuth = FirebaseAuth.getInstance();

        mUserName = (TextView) view.findViewById(R.id.user_profile_name);

        //mUserName.setText(getUsername());


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
                        R.layout.recycler_item,
                        UserViewHolder.class,
                        mRefUsers

                )
        {
            @Override
            protected void populateViewHolder(UserViewHolder viewHolder, User model, int position) {
                Toast.makeText(getActivity(),"Inside populateViewHolder",Toast.LENGTH_LONG).show();
                viewHolder.setUser_Name(model.user_name);
                viewHolder.setUser_Color(model.user_color);
                viewHolder.setUser_Image(R.drawable.img_profile_circular_demo);

                Toast.makeText(getActivity(),"Inside populateViewHolder",Toast.LENGTH_LONG).show();

            }
        };





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

        public UserViewHolder(View itemView) {
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
}
