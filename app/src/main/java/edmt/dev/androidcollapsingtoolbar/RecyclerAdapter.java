package edmt.dev.androidcollapsingtoolbar;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by tomer on 14/01/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<Idea> ideas;

    public RecyclerAdapter(List<Idea> ideas) {
        this.ideas = ideas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent,  false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Idea sampleidea = ideas.get(position);
        holder.title.setText(sampleidea.ideatitle);
        holder.desc.setText(sampleidea.ideadesc);
        holder.image.setImageResource(sampleidea.ideaimage);

    }

    @Override
    public int getItemCount() {
        return ideas.size();
    }
}
