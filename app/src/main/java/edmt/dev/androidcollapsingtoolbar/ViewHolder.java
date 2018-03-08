package edmt.dev.androidcollapsingtoolbar;

import android.media.Image;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by tomer on 14/01/2018.
 */

public class ViewHolder extends RecyclerView.ViewHolder {

    public ImageView image;
    public TextView title;
    public TextView desc;
    public LinearLayout linearLayout;


    public ViewHolder(View itemView) {
        super(itemView);

        image = (ImageView) itemView.findViewById(R.id.imageview_id);
        title = (TextView) itemView.findViewById(R.id.title_textview);
        desc = (TextView) itemView.findViewById(R.id.desc_textview);
        linearLayout = (LinearLayout) itemView.findViewById(R.id.recycleritem_id);
    }


}
