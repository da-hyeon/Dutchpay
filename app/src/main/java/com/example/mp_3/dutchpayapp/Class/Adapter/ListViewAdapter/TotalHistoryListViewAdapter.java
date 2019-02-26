package com.example.mp_3.dutchpayapp.Class.Adapter.ListViewAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mp_3.dutchpayapp.Class.ListItemClass.ListViewItem_TotalHistory;
import com.example.mp_3.dutchpayapp.R;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TotalHistoryListViewAdapter extends ArrayAdapter<ListViewItem_TotalHistory> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;

    public TotalHistoryListViewAdapter(Context context, List<ListViewItem_TotalHistory> objects) {
        super(context, 0, objects);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // get item for selected view
        ListViewItem_TotalHistory item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);


            // binding view parts to view holder
              viewHolder.contentRequestBtn = cell.findViewById(R.id.content_request_btn);

            viewHolder.title_delimiter = cell.findViewById(R.id.title_delimiter);
            viewHolder.title_dateYMD = cell.findViewById(R.id.title_dateYMD);
            viewHolder.title_dateHMS = cell.findViewById(R.id.title_dateHMS);
            viewHolder.title_remittererID = cell.findViewById(R.id.title_remittererID);
            viewHolder.title_receiverID = cell.findViewById(R.id.title_receiverID);
            viewHolder.title_remittanceAmount = cell.findViewById(R.id.title_remittanceAmount);

            viewHolder.content_dateYMD = cell.findViewById(R.id.content_dateYMD);
            viewHolder.content_dateHMS = cell.findViewById(R.id.content_dateHMS);
            viewHolder.content_remittererID = cell.findViewById(R.id.content_remittererID);
            viewHolder.content_receiverID = cell.findViewById(R.id.content_receiverID);
            viewHolder.content_remittanceAmount = cell.findViewById(R.id.content_remittanceAmount);

            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(false);
            } else {
                cell.fold(false);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        if (null == item)
            return cell;

        // bind data from selected element to view through view holder
        viewHolder.title_delimiter.setText(item.getDelimiter());
        viewHolder.title_dateYMD.setText(item.getDateYMD());
        viewHolder.title_dateHMS.setText(item.getDateHMS());
        viewHolder.title_remittererID.setText("보낸사람 : " + item.getRemittererID());
        viewHolder.title_receiverID.setText("받는사람 : " + item.getReceiverID());
        viewHolder.title_remittanceAmount.setText(String.format("%,d", item.getRemittanceAmount()) + "원");

        viewHolder.content_dateYMD.setText(item.getDateYMD());
        viewHolder.content_dateHMS.setText(item.getDateHMS());
        viewHolder.content_remittererID.setText(item.getRemittererID());
        viewHolder.content_receiverID.setText(item.getReceiverID());
        viewHolder.content_remittanceAmount.setText(String.format("%,d", item.getRemittanceAmount()) + "원");


        // set custom btn handler for list item from that item
        if (item.getRequestBtnClickListener() != null) {
            viewHolder.contentRequestBtn.setOnClickListener(item.getRequestBtnClickListener());
        } else {
            // (optionally) add "default" handler if no handler found in item
            viewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
        }

        return cell;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    // View lookup cache
    private static class ViewHolder {
//        TextView price;
        TextView contentRequestBtn;

        TextView title_delimiter;
        TextView title_dateYMD;
        TextView title_dateHMS;
        TextView title_remittererID;
        TextView title_receiverID;
        TextView title_remittanceAmount;

        TextView content_dateYMD;
        TextView content_dateHMS;
        TextView content_remittererID;
        TextView content_receiverID;
        TextView content_remittanceAmount;
    }
}
