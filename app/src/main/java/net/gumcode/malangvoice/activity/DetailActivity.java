package net.gumcode.malangvoice.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.gumcode.malangvoice.R;
import net.gumcode.malangvoice.config.Config;
import net.gumcode.malangvoice.config.Constants;
import net.gumcode.malangvoice.model.Post;
import net.gumcode.malangvoice.utilities.HTTPHelper;
import net.gumcode.malangvoice.utilities.JSONParser;
import net.gumcode.malangvoice.utilities.Utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by A. Fauzi Harismawan on 12/31/2015.
 */
public class DetailActivity extends AppCompatActivity {

    private Post item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle recv = getIntent().getExtras();
        getData(Config.ONE_POST_JSON_URL + recv.getInt("ID"));
    }

    private void getData(final String url) {
        new AsyncTask<Void, Void, Void>() {

            ProgressDialog pd;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pd = new ProgressDialog(DetailActivity.this);
                pd.setMessage("Loading...");
                pd.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                Log.d(Constants.LOG_TAG, url);
                item = JSONParser.parsePost(HTTPHelper.sendGETRequest(url));
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (item != null) {
                    initView();
                } else {
                    // error
                }
                pd.dismiss();
            }
        }.execute();
    }

    private void initView() {
        ImageView image = (ImageView) findViewById(R.id.image);
        TextView title = (TextView) findViewById(R.id.title);
        WebView view = (WebView) findViewById(R.id.webview);
        WebSettings s =view.getSettings();
        s.setDefaultFontSize(14);
//        TextView text = (TextView) findViewById(R.id.text);
        TextView time = (TextView) findViewById(R.id.time);

        Picasso.with(this).load(item.getImgUrl()).into(image);
        title.setText(Html.fromHtml(item.getTitle()));

        view.loadData(item.getDesc(), "text/html", "UTF-8");
//        text.setText(Html.fromHtml(item.getDesc()));
        String show = item.getCategory() + " / " + item.getAuthor() + " / " + Utilities.getStringTime(item.getTime());
        time.setText(show);
        FloatingActionButton share = (FloatingActionButton) findViewById(R.id.btn_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.setType("text/plain");
                        sendIntent.putExtra(Intent.EXTRA_SUBJECT, item.getTitle());
                        sendIntent.putExtra(Intent.EXTRA_TITLE, item.getTitle());
                        sendIntent.putExtra(Intent.EXTRA_TEXT, item.getLink());
                        try {
                            sendIntent.putExtra(Intent.EXTRA_STREAM, getImageUri(DetailActivity.this, Picasso.with(DetailActivity.this).load(item.getImgUrl()).get(), item.getImgUrl()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        startActivity(Intent.createChooser(sendIntent, "Share Post"));
                    }
                }).start();
            }
        });
    }

    public Uri getImageUri(Context inContext, Bitmap inImage, String title) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, title, null);
        return Uri.parse(path);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}
