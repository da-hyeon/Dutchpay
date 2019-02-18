package com.example.mp_3.dutchpayapp.Class.Adapter.ListViewAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mp_3.dutchpayapp.Class.ListItemClass.ListViewItem_DutchStart;
import com.example.mp_3.dutchpayapp.Class.ListItemClass.ListViewItem_DutchStartConfirmed;
import com.example.mp_3.dutchpayapp.R;

import java.util.ArrayList;

public class DutchStartConfirmedListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ListViewItem_DutchStartConfirmed> listViewItemList;
    private LayoutInflater layoutInflater;
    private int participantCount;
    private int totalAmount;


    public DutchStartConfirmedListViewAdapter(Context context, ArrayList<ListViewItem_DutchStartConfirmed> listViewItemList, int participantCount, int totalAmount) {
        this.listViewItemList = listViewItemList;
        this.participantCount = participantCount;
        this.totalAmount = totalAmount;
    }

    //뷰홀더
    private class ViewHolder {
        private TextView userID;
        private TextView Amount;
        private CheckBox check;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = listViewItemList.get(position).getListNum();
        final Context context = parent.getContext();
        final ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_dutch_group, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.userID = (TextView) convertView.findViewById(R.id.tv_member_host);
            viewHolder.Amount = (TextView) convertView.findViewById(R.id.tv_cost_host);
            viewHolder.check = (CheckBox) convertView.findViewById(R.id.cb_check_host);
            viewHolder.check.setClickable(false);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.userID.setText(listViewItemList.get(position).getUserID());
        viewHolder.Amount.setText(String.format("%,d", listViewItemList.get(position).getAssignedAmount()));
        viewHolder.check.setChecked(listViewItemList.get(position).isPrePaymentCheck());


        return convertView;
    }
}
