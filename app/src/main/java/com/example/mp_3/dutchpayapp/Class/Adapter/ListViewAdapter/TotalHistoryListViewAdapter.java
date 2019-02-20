package com.example.mp_3.dutchpayapp.Class.Adapter.ListViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mp_3.dutchpayapp.Class.ListItemClass.ListViewItem_TotalHistory;
import com.example.mp_3.dutchpayapp.R;

import java.util.ArrayList;

public class TotalHistoryListViewAdapter extends BaseAdapter {

    private ArrayList<ListViewItem_TotalHistory> listViewItemList;

    public TotalHistoryListViewAdapter( ArrayList<ListViewItem_TotalHistory> listViewItemList) {
        this.listViewItemList = listViewItemList;
    }

    private class ViewHolder {
        public TextView date;
        public TextView shop;
        public TextView member;
        public TextView cost;
    }

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
        final Context context = parent.getContext();
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_payment_history, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.date = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.shop = (TextView) convertView.findViewById(R.id.tv_shop);
            viewHolder.member = (TextView) convertView.findViewById(R.id.tv_member);
            viewHolder.cost = (TextView) convertView.findViewById(R.id.tv_cost);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.date.setText(listViewItemList.get(position).getDate());
        viewHolder.shop.setText(listViewItemList.get(position).getCompanyName());
        viewHolder.member.setText(listViewItemList.get(position).getParticipantName());
        viewHolder.cost.setText(String.format("%,d", listViewItemList.get(position).getAmount()) + "원");

        return convertView;
    }
}
