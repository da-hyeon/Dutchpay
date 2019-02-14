package com.example.mp_3.dutchpayapp.Class.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mp_3.dutchpayapp.Class.ListItemClass.ListViewItem;
import com.example.mp_3.dutchpayapp.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    public ListViewAdapter() {

    }
//asd
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_payment_history,parent,false);
        }

        TextView date = (TextView) convertView.findViewById(R.id.tv_date);
        TextView shop = (TextView) convertView.findViewById(R.id.tv_shop);
        TextView member = (TextView) convertView.findViewById(R.id.tv_member);
        TextView cost = (TextView) convertView.findViewById(R.id.tv_cost);

        ListViewItem listViewItem = listViewItemList.get(position);

        date.setText(listViewItem.getDate());
        shop.setText(listViewItem.getShop());
        member.setText(listViewItem.getMamber());
        cost.setText(listViewItem.getCost());

        return convertView;
    }

    public void addItem(String date, String shop, String member, String cost){
        ListViewItem item = new ListViewItem();

        item.setDate(date);
        item.setShop(shop);
        item.setMamber(member);
        item.setCost(cost);

        listViewItemList.add(item);
    }
}
