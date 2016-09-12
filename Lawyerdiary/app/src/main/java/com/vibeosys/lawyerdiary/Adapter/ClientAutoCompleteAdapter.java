package com.vibeosys.lawyerdiary.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;


import com.vibeosys.lawyerdiary.R;
import com.vibeosys.lawyerdiary.data.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akshay on 12-09-2016.
 */
public class ClientAutoCompleteAdapter extends ArrayAdapter<Client> {

    private ArrayList<Client> items;
    private ArrayList<Client> itemsAll;
    private ArrayList<Client> suggestions;
    private int viewResourceId;

    public ClientAutoCompleteAdapter(Context context, int viewResourceId, ArrayList<Client> items) {
        super(context, viewResourceId, items);
        this.items = items;
        this.itemsAll = (ArrayList<Client>) items.clone();
        this.suggestions = new ArrayList<Client>();
        this.viewResourceId = viewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        Client customer = items.get(position);
        if (customer != null) {
            TextView customerNameLabel = (TextView) v.findViewById(R.id.clientName);
            if (customerNameLabel != null) {
//              Log.i(MY_DEBUG_TAG, "getView Customer Name:"+customer.getName());
                customerNameLabel.setText(customer.getName());
            }
        }
        return v;
    }

    @Override
    public Client getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((Client) (resultValue)).getName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Client customer : itemsAll) {
                    if (customer.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(customer);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Client> filteredList = (ArrayList<Client>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Client c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };

    public Client contains(String clientName) {
        Client client = null;
        for (Client clientData : itemsAll) {
            String name = clientData.getName();
            if (name.toLowerCase().equals(clientName.toLowerCase())) {
                client = clientData;
            }
        }
        return client;
    }
}
