package com.example.mp_3.dutchpayapp.Class.Adapter.ListViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.mp_3.dutchpayapp.Class.ListItemClass.ListViewItem_ConfirmationPayment;
import com.example.mp_3.dutchpayapp.Class.ListItemClass.ListViewItem_DutchStartConfirmed;
import com.example.mp_3.dutchpayapp.R;

import java.util.ArrayList;

public class ConfirmationPaymentListViewAdapter extends BaseAdapter {


    private ArrayList<ListViewItem_ConfirmationPayment> listViewItemList;


    public ConfirmationPaymentListViewAdapter( ArrayList<ListViewItem_ConfirmationPayment> listViewItemList) {
        this.listViewItemList = listViewItemList;
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
        final Context context = parent.getContext();
        final ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_confirmation_payment, parent, false);

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
        viewHolder.Amount.setText(String.format("%,d", listViewItemList.get(position).getAssignedAmount()) + "원");
        viewHolder.check.setChecked(listViewItemList.get(position).isPrePaymentCheck());

        return convertView;
    }
}
