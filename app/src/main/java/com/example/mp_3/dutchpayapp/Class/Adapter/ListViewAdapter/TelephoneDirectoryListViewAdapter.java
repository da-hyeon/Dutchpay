package com.example.mp_3.dutchpayapp.Class.Adapter.ListViewAdapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.mp_3.dutchpayapp.Class.ListItemClass.ListViewItem_TelephoneDirectory;
import com.example.mp_3.dutchpayapp.Class.ListItemClass.ListViewItem_TotalHistory;
import com.example.mp_3.dutchpayapp.R;

import java.util.List;
import java.util.Locale;

public class TelephoneDirectoryListViewAdapter extends ArrayAdapter<ListViewItem_TelephoneDirectory> {

    private List telephoneDirectoryList;

    class productViewHolder {
        public TextView tv_name;
        public TextView tv_phoneNumber;
    }


    public TelephoneDirectoryListViewAdapter(Context context, List<ListViewItem_TelephoneDirectory> telephoneDirectoryList) {
        super(context, 0, telephoneDirectoryList);
        this.telephoneDirectoryList = telephoneDirectoryList;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public View getView(int i, View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView; // 코드 가독성을 위해서 rowView 변수를 사용합니다.
        final productViewHolder viewHolder;

        if (rowView == null) {
            rowView = LayoutInflater.from(getContext()).inflate(R.layout.item_telephone_directory, parent, false);

            viewHolder = new productViewHolder();
            viewHolder.tv_name = (TextView) rowView.findViewById(R.id.tv_name );
            viewHolder.tv_phoneNumber = (TextView) rowView.findViewById(R.id.tv_phoneNumber );

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (productViewHolder) rowView.getTag();
        }
        final ListViewItem_TelephoneDirectory telephoneDirectory = (ListViewItem_TelephoneDirectory)  telephoneDirectoryList.get(i);

        viewHolder.tv_name.setText(telephoneDirectory.getUserName());
        viewHolder.tv_phoneNumber.setText(telephoneDirectory.getUserPhoneNumber());

        return rowView;
    }
}
