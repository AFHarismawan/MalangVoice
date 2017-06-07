package net.gumcode.malangvoice.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import net.gumcode.malangvoice.R;
import net.gumcode.malangvoice.config.Config;
import net.gumcode.malangvoice.config.Constants;
import net.gumcode.malangvoice.model.Page;
import net.gumcode.malangvoice.utilities.HTTPHelper;
import net.gumcode.malangvoice.utilities.JSONParser;

/**
 * Created by A. Fauzi Harismawan on 1/6/2016.
 */
public class InfoActivity extends AppCompatActivity {

    private Page item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle recv = getIntent().getExtras();
        getData(Config.ONE_PAGE_JSON_URL + recv.getString("ID"));
    }

    private void getData(final String url) {
        new AsyncTask<Void, Void, Void>() {

            ProgressDialog pd;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pd = new ProgressDialog(InfoActivity.this);
                pd.setMessage("Loading...");
                pd.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                Log.d(Constants.LOG_TAG, url);
                item = JSONParser.parsePage(HTTPHelper.sendGETRequest(url));
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
        TextView title = (TextView) findViewById(R.id.title);
        TextView text = (TextView) findViewById(R.id.text);

        title.setText(Html.fromHtml(item.getTitle()));
        text.setText(Html.fromHtml(item.getContent()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}