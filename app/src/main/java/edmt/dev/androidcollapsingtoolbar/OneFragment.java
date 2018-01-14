package edmt.dev.androidcollapsingtoolbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by tomer on 07/12/2017.
 */

public class OneFragment extends Fragment {

    //test (can delete if doesn't work)
    RelativeLayout rLayout;

    View view;

    public OneFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_1, container, false);

        LinearLayout relative1 =(LinearLayout)view.findViewById(R.id.relative_layout_items_1);

        ViewGroup.LayoutParams lprams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button tv1 = new Button(getContext());
        tv1.setText("Hello");
        tv1.setLayoutParams(lprams);
        tv1.setId(View.generateViewId());
        relative1.addView(tv1);



        Button button = (Button) view.findViewById(R.id.add_idea_button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LinearLayout relative1 =(LinearLayout)view.findViewById(R.id.relative_layout_items_1);
                ViewGroup.LayoutParams lprams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                Button tv2 = new Button(getContext());
                tv2.setText("Hello");
                tv2.setLayoutParams(lprams);
                tv2.setId(View.generateViewId());
                relative1.addView(tv2);
            }
        });

        return view;



    }


}
