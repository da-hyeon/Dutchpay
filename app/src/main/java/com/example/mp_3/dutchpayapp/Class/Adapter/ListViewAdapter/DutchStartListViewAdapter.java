package com.example.mp_3.dutchpayapp.Class.Adapter.ListViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mp_3.dutchpayapp.Class.ListItemClass.ListViewItem_DutchStart;
import com.example.mp_3.dutchpayapp.R;

import java.util.ArrayList;

public class DutchStartListViewAdapter extends BaseAdapter {

    private ArrayList<ListViewItem_DutchStart> listViewItemList;
    private LayoutInflater layoutInflater;

    public DutchStartListViewAdapter( ArrayList<ListViewItem_DutchStart> listViewItemList) {
        this.listViewItemList = listViewItemList;
    }

    //뷰홀더
    private class ViewHolder {
        private TextView member;
        private TextView cost;
        private  EditText usercost;
        private  CheckBox check;
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
        final ViewHolder viewHolder;

        if(convertView == null){
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.item_dutch_group,parent,false);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_dutch_group,parent,false);

            viewHolder = new ViewHolder();
            viewHolder.member = (TextView) convertView.findViewById(R.id.tv_member_host);
            viewHolder.cost = (TextView) convertView.findViewById(R.id.tv_cost_host);
            viewHolder.usercost = (EditText) convertView.findViewById(R.id.et_Ucost_host);
            viewHolder.check = (CheckBox) convertView.findViewById(R.id.cb_check_host);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.member.setText(listViewItemList.get(position).getMember());
        viewHolder.cost.setText(listViewItemList.get(position).getCost()+"원");
        //usercost.setText("");

        //-----변동 data------//
        String Susercost = viewHolder.usercost.getText().toString();
        Boolean Bcheck = viewHolder.check.isChecked();
        //--------------------//
        return convertView;
    }
}
