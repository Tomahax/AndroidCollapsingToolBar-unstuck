package edmt.dev.androidcollapsingtoolbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomer on 07/12/2017.
 */

public class ThreeFragment extends Fragment {

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

    public ThreeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_3, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_id_1);

        List<Idea> sampleidea = new ArrayList<>();

        for (int i=0; i<title.length; i++){

            Idea idea = new Idea();

            idea.ideatitle = title[i];
            idea.ideadesc = desc[i];
            idea.ideaimage = image[i];

            sampleidea.add(idea);

        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new RecyclerAdapter(sampleidea));

        return view;
    }
}
