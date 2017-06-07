package net.gumcode.malangvoice.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.gumcode.malangvoice.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by A. Fauzi Harismawan on 12/16/2015.
 */
public class ExpandableNavListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, List<String>> _listDataChild;

    public ExpandableNavListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String category = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_nav_menu, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.nav_text);

        txtListChild.setText(category);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_nav_menu_group, null);
        }

        ImageView navIcon = (ImageView) convertView.findViewById(R.id.nav_icon);
        switch (groupPosition) {
            case 0 :
                navIcon.setImageResource(R.drawable.ic_home_grey600_24dp);
                break;
            case 1 :
                navIcon.setImageResource(R.drawable.ic_view_list_grey600_24dp);
                break;
            case 2 :
                navIcon.setImageResource(R.drawable.ic_info_grey600_24dp);
                break;
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.nav_text);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        ImageView navImage = (ImageView) convertView.findViewById(R.id.nav_img);
        navImage.setVisibility(View.GONE);
        if (groupPosition >= 1) {
            if (isExpanded) {
                navImage.setVisibility(View.VISIBLE);
                navImage.setImageResource(R.drawable.ic_keyboard_arrow_up_grey600_24dp);
            } else {
                navImage.setVisibility(View.VISIBLE);
                navImage.setImageResource(R.drawable.ic_keyboard_arrow_down_grey600_24dp);
            }
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
