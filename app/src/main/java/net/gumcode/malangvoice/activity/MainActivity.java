package net.gumcode.malangvoice.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import net.gumcode.malangvoice.R;
import net.gumcode.malangvoice.adapter.ExpandableNavListAdapter;
import net.gumcode.malangvoice.config.Config;
import net.gumcode.malangvoice.fragment.NewsFeedsFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ExpandableListView mDrawerList;
    private String[] keys;
    private String[] title;
    private String[] pageKeys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        keys = getResources().getStringArray(R.array.nav_child_kanal_keys);
        title  = getResources().getStringArray(R.array.nav_child_kanal);
        pageKeys = getResources().getStringArray(R.array.nav_child_info_keys);
        initNavDrawer();

        changeFragment(new NewsFeedsFragment(), getResources().getStringArray(R.array.nav_menu)[0]);
    }

    private void initNavDrawer() {
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ExpandableListView) findViewById(R.id.navList);
        View header = getLayoutInflater().inflate(R.layout.nav_header_view, null);
        mDrawerList.addHeaderView(header);

        HashMap<String, List<String>> listDataChild = new HashMap<>();

        String[] temp = getResources().getStringArray(R.array.nav_menu);
        ArrayList<String> listDataHeader = new ArrayList<>();
        Collections.addAll(listDataHeader, temp);

        String[] temp1 = getResources().getStringArray(R.array.nav_child_kanal);
        ArrayList<String> kanals = new ArrayList<>();
        Collections.addAll(kanals, temp1);

        String[] temp2 = getResources().getStringArray(R.array.nav_child_info);
        ArrayList<String> info = new ArrayList<>();
        Collections.addAll(info, temp2);

        listDataChild.put(listDataHeader.get(0), new ArrayList<String>());
        listDataChild.put(listDataHeader.get(1), kanals);
        listDataChild.put(listDataHeader.get(2), info);


        ExpandableNavListAdapter adapter = new ExpandableNavListAdapter(this, listDataHeader, listDataChild);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setGroupIndicator(null);
        mDrawerList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                mDrawerLayout.closeDrawers();
                mDrawerList.collapseGroup(groupPosition);
                if (groupPosition == 1) {
                    Bundle send = new Bundle();
                    send.putString("category", keys[childPosition]);

                    NewsFeedsFragment fragment = new NewsFeedsFragment();
                    fragment.setArguments(send);
                    changeFragment(fragment, title[childPosition]);
                } else if (groupPosition == 2) {
                    if (childPosition == 2) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Config.REG_ADS_URL));
                        startActivity(browserIntent);
                    } else {
                        Bundle send = new Bundle();
                        send.putString("ID", pageKeys[childPosition]);

                        Intent change = new Intent(MainActivity.this, InfoActivity.class);
                        change.putExtras(send);
                        startActivity(change);
                    }
                }
                return false;
            }
        });

        mDrawerList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (groupPosition == 0) {
                    mDrawerLayout.closeDrawers();
                    changeFragment(new NewsFeedsFragment(), getResources().getStringArray(R.array.nav_menu)[0]);
                }
                return false;
            }
        });

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name) {

            public void onDrawerClosed(View view) {
                mDrawerList.collapseGroup(0);
            }

            public void onDrawerOpened(View drawerView) {

            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    public void changeFragment(Fragment fragment, String title) {
        setTitle(title);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }
}
