package edmt.dev.androidcollapsingtoolbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by tomer on 07/12/2017.
 */

public class TwoFragment extends Fragment {

    private FirebaseAuth mAuth;
    View view;

    public TwoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_2, container, false);

        mAuth = FirebaseAuth.getInstance();


        TextView logoutText = (TextView) view.findViewById(R.id.log_out_profile);
        logoutText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LinearLayout linear2 =(LinearLayout)view.findViewById(R.id.linear_layout_items_2);
                ViewGroup.LayoutParams lprams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mAuth.signOut();
            }
        });

        return view;
    }
}
