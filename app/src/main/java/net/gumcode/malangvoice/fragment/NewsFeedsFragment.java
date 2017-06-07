package net.gumcode.malangvoice.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import net.gumcode.malangvoice.R;
import net.gumcode.malangvoice.activity.DetailActivity;
import net.gumcode.malangvoice.adapter.NewsListAdapter;
import net.gumcode.malangvoice.config.Config;
import net.gumcode.malangvoice.config.Constants;
import net.gumcode.malangvoice.model.Post;
import net.gumcode.malangvoice.utilities.HTTPHelper;
import net.gumcode.malangvoice.utilities.JSONParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by A. Fauzi Harismawan on 12/16/2015.
 */
public class NewsFeedsFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView list;
    private List<Post> postList;
    private List<Post> adsList;
    private List<Post> fixedList = new ArrayList<>();
    private SwipeRefreshLayout swipe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_feeds, container, false);
        list = (ListView) rootView.findViewById(R.id.list);
        swipe = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Bundle recv = getArguments();
        if (recv == null) {
            getData(Config.ALL_POST_JSON_URL);
        } else {
            getData(Config.CATEGORY_POST_JSON_URL + recv.getString("category"));
        }

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (recv == null) {
                    refresh(Config.ALL_POST_JSON_URL);
                } else {
                    refresh(Config.CATEGORY_POST_JSON_URL + recv.getString("category"));
                }
            }
        });
    }

    private void initList() {
        NewsListAdapter adapter = new NewsListAdapter(getActivity(), R.layout.list_item_rss_feeds, fixedList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if ((position + 1) % 4 == 0) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fixedList.get(position).getAuthor()));
            startActivity(browserIntent);
        } else {
            Bundle send = new Bundle();
            send.putInt("ID", fixedList.get(position).getId());

            Intent change = new Intent(getActivity(), DetailActivity.class);
            change.putExtras(send);
            startActivity(change);
        }
    }

    private void refresh(final String url) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                Log.d(Constants.LOG_TAG, url);
                postList = JSONParser.parsePostList(HTTPHelper.sendGETRequest(url));
                adsList = JSONParser.parseAdsList(HTTPHelper.sendGETRequest(Config.ADS_JSON_URL));
                fixedList.clear();
                int i = 1;
                int j = 0;
                int k = 0;
                boolean run = true;
                while(run) {
                    if (i % 4 == 0) {
                        if (j == adsList.size()) {
                            j = 0;
                        }
                        if (adsList.size() != 0) {
                            fixedList.add(adsList.get(j));
                            j++;
                        }
                    } else {
                        if (postList.size() != 0) {
                            fixedList.add(postList.get(k));
                            k++;
                        }
                        if (k == postList.size()) {
                            run = false;
                        }
                    }
                    i++;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (postList.size() != 0) {
                    initList();
                } else {
                    // no post
                }
                swipe.setRefreshing(false);
            }
        }.execute();
    }

    private void getData(final String url) {
        new AsyncTask<Void, Void, Void>() {

            ProgressDialog pd;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pd = new ProgressDialog(getActivity());
                pd.setMessage("Loading...");
                pd.setIndeterminate(true);
                pd.show();
            }

            @Override
            protected Void doInBackground(Void... params) {
                Log.d(Constants.LOG_TAG, url);
                postList = JSONParser.parsePostList(HTTPHelper.sendGETRequest(url));
                adsList = JSONParser.parseAdsList(HTTPHelper.sendGETRequest(Config.ADS_JSON_URL));
                fixedList.clear();
                int i = 1;
                int j = 0;
                int k = 0;
                boolean run = true;
                while(run) {
                    if (i % 4 == 0) {
                        if (j == adsList.size()) {
                            j = 0;
                        }
                        if (adsList.size() != 0) {
                            fixedList.add(adsList.get(j));
                            j++;
                        }
                    } else {
                        if (postList.size() != 0) {
                            fixedList.add(postList.get(k));
                            k++;
                        }
                        if (k == postList.size()) {
                            run = false;
                        }
                    }
                    i++;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (postList.size() != 0) {
                    initList();
                } else {
                    // no post
                }
                pd.dismiss();
            }
        }.execute();
    }
}
