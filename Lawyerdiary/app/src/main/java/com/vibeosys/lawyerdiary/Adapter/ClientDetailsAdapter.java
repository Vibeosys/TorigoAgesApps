package com.vibeosys.lawyerdiary.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vibeosys.lawyerdiary.R;

import java.util.ArrayList;

/**
 * Created by shrinivas on 26-04-2016.
 */
public class ClientDetailsAdapter extends BaseAdapter {
    Context context;
    ArrayList<ClientData> clientData;
    public ClientDetailsAdapter(Context context ,ArrayList<ClientData> clientData)
    {
        this.context = context;
        this.clientData = clientData;
    }

    @Override
    public int getCount() {
        return clientData.size();
    }

    @Override
    public Object getItem(int position) {
        return clientData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView ==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.client_details,null);
            holder = new Holder();
            holder.imgView = (ImageView)convertView.findViewById(R.id.imageView2);
            holder.custmerName = (TextView)convertView.findViewById(R.id.custmer_name);
            holder.TypeOfLitigation = (TextView)convertView.findViewById(R.id.type_of_ligitation);
            holder.categoryOfLigitation =(TextView)convertView.findViewById(R.id.category_of_ligitation);
            convertView.setTag(holder);

        }else
        {
            holder = (Holder)convertView.getTag();
        }
        holder.custmerName.setText(clientData.get(position).getCustmerName());
        holder.TypeOfLitigation.setText(clientData.get(position).getTypeOfLigitation());
        holder.categoryOfLigitation.setText(clientData.get(position).getCategoryOfLegitation());
        holder.imgView.setImageResource(clientData.get(position).getIcon());
        return convertView;
    }
    public class Holder
    {
        ImageView imgView;
        TextView custmerName;
        TextView TypeOfLitigation;
        TextView categoryOfLigitation;
    }
}
