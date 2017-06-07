package net.gumcode.malangvoice.adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.gumcode.malangvoice.R;
import net.gumcode.malangvoice.config.Config;
import net.gumcode.malangvoice.config.Constants;
import net.gumcode.malangvoice.model.Post;
import net.gumcode.malangvoice.utilities.Utilities;

import java.util.List;

/**
 * Created by A. Fauzi Harismawan on 1/1/2016.
 */
public class NewsListAdapter extends ArrayAdapter<Post> {

    public NewsListAdapter(Context context, int resource, List<Post> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if ((position + 1) % 4 == 0) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.list_item_ads, null);

            Post w = getItem(position);

            if (w != null) {
                ImageView img = (ImageView) convertView.findViewById(R.id.img);

                if (img != null) {
                    Log.d(Constants.LOG_TAG, w.getImgUrl());
                    Picasso.with(getContext()).load(Config.BASE_URL + w.getImgUrl()).into(img);
                }
            }
        } else {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            convertView = vi.inflate(R.layout.list_item_rss_feeds, null);

            Post w = getItem(position);

            if (w != null) {

                ImageView img = (ImageView) convertView.findViewById(R.id.img);
                TextView tt1 = (TextView) convertView.findViewById(R.id.title);
                TextView tt2 = (TextView) convertView.findViewById(R.id.text);
                TextView tt3 = (TextView) convertView.findViewById(R.id.time);

                if (img != null) {
                    Picasso.with(getContext()).load(w.getImgUrl()).into(img);
                }

                if (tt1 != null) {
                    tt1.setText(Html.fromHtml(w.getTitle()));
                }

                if (tt2 != null) {
                    tt2.setText(Html.fromHtml(w.getDesc()));
                }

                if (tt3 != null) {
                    if (w.getTime() != null) {
                        tt3.setText(Utilities.getStringTime(w.getTime()));
                    } else {
                        tt3.setText("");
                    }
                }
            }
        }
        return convertView;
    }
}
