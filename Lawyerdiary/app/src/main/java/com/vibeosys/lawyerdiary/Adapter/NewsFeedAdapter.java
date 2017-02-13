package com.vibeosys.lawyerdiary.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vibeosys.lawyerdiary.R;
import com.vibeosys.lawyerdiary.data.CaseData;
import com.vibeosys.lawyerdiary.data.NewsFeedData;

import java.util.ArrayList;

/**
 * Created by Vibeosys software on 08-10-2016.
 */

/**
 * Show the current activities that done by the user
 * Newsfeed adapter show the current activities like add new Client,add new Event etc.
 */
public class NewsFeedAdapter extends BaseAdapter {

    private ArrayList<NewsFeedData> mData;
    private Context mContext;

    /**
     * Constructor that can accept the data and show it
     *
     * @param mData    List of NewsFeedData
     * @param mContext application context
     */
    public NewsFeedAdapter(ArrayList<NewsFeedData> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    /**
     * Total number of activity count
     * @return integer value give the size of the arraylist
     */
    @Override
    public int getCount() {
        return mData.size();
    }

    /**
     * Get the NewsFeedData object the given position
     * @param position Integer number that give the position of the object
     * @return News feed data object from the list of given position
     */
    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Inflate the view to show the row in the list view
     *
     * @param position    integer that give the position of the item
     * @param convertView View that to be inflated
     * @param parent      parent view group
     * @return View is to be showing
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder viewHolder = null;
        if (row == null) {

            LayoutInflater theLayoutInflator = (LayoutInflater) mContext.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            row = theLayoutInflator.inflate(R.layout.row_news_feed_data, null);
            viewHolder = new ViewHolder();
            viewHolder.txtNewsHeading = (TextView) row.findViewById(R.id.txtNewsHeading);
            viewHolder.txtNewsTitle = (TextView) row.findViewById(R.id.txtNewsTitle);
            viewHolder.txtLogo = (TextView) row.findViewById(R.id.txtLogo);
            viewHolder.txtDesc = (TextView) row.findViewById(R.id.txtDesc);
            row.setTag(viewHolder);

        } else
            viewHolder = (ViewHolder) convertView.getTag();
        NewsFeedData newsData = mData.get(position);
        String name = newsData.getTitle();
        String heading = newsData.getHeading();
        String description = newsData.getDesc();
        viewHolder.txtNewsTitle.setText(name);
        viewHolder.txtDesc.setText(description);
        String[] logos = name.split(" ", 2);
        String logo1 = logos[0];
        String caseLogo = "" + logo1.charAt(0);
        if (logos.length > 1) {
            String logo2 = logos[1];
            if (!TextUtils.isEmpty(logo2)) {
                caseLogo = caseLogo + logo2.charAt(0);
            }
        }

        viewHolder.txtLogo.setText(caseLogo.toUpperCase());
        viewHolder.txtNewsHeading.setText(heading);
        return row;
    }

    private class ViewHolder {
        TextView txtNewsHeading, txtNewsTitle, txtLogo, txtDesc;
    }
}