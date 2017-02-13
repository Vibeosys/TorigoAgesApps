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
 * Created by Vibeosys Software on 12-09-2016.
 */

/**
 * Client autocomplete controller adapter is used in autocomplete Edit text<br/>
 * Show the saved client in the local database in autocomplete text field
 */
public class ClientAutoCompleteAdapter extends ArrayAdapter<Client> {

    private ArrayList<Client> items;
    private ArrayList<Client> itemsAll;
    private ArrayList<Client> suggestions;
    private int viewResourceId;

    /**
     * Constructor of the ClientAutoCompleteAdapter
     *
     * @param context        application context
     * @param viewResourceId Resource Id the layout id which we inflate
     * @param items          Client ArrayList is to show on autocomplete View
     */
    public ClientAutoCompleteAdapter(Context context, int viewResourceId, ArrayList<Client> items) {
        super(context, viewResourceId, items);
        this.items = items;
        this.itemsAll = (ArrayList<Client>) items.clone();
        this.suggestions = new ArrayList<Client>();
        this.viewResourceId = viewResourceId;
    }

    /**
     * Inflate the view to show the row in the list view
     *
     * @param position    integer that give the position of the item
     * @param convertView View that to be inflated
     * @param parent      parent view group
     * @return View is to be showing
     */
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

    /**
     * Filter the client as per the name
     */
    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((Client) (resultValue)).getName();
            return str;
        }

        /**
         * Get the filter result with the given constraint
         * @param constraint Character sequence that we want to search
         * @return Filter result object contains the values and the count
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Client customer : itemsAll) {
                    // Search the name of the given constraint
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

        /**
         * Call after the performFiltering show the filtered items on the autocomplete list
         * @param constraint CharSequence that is to be searched
         * @param results   Filter result that get from the performFiltering method
         */
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

    /**
     * Check the given client name is present in the list
     *
     * @param clientName Name of the client that is to be searched
     * @return the Client object which fulfill the criteria
     */
    public Client contains(String clientName) {
        Client client = null;
        for (Client clientData : itemsAll) {
            String name = clientData.getName();
            // Check the given name is present in the list or not
            if (name.toLowerCase().equals(clientName.toLowerCase())) {
                client = clientData;
            }
        }
        return client;
    }
}
