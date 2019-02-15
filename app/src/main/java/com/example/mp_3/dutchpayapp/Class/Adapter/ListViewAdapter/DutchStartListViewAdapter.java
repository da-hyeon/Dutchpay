package com.example.mp_3.dutchpayapp.Class.Adapter.ListViewAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mp_3.dutchpayapp.Activity.LoginActivity;
import com.example.mp_3.dutchpayapp.Activity.MainActivity;
import com.example.mp_3.dutchpayapp.Class.ListItemClass.ListViewItem_DutchStart;
import com.example.mp_3.dutchpayapp.R;

import java.util.ArrayList;

public class DutchStartListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ListViewItem_DutchStart> listViewItemList;
    private LayoutInflater layoutInflater;
    private int participantCount;

    public DutchStartListViewAdapter(Context context, ArrayList<ListViewItem_DutchStart> listViewItemList, int participantCount) {
        this.listViewItemList = listViewItemList;
        this.participantCount = participantCount;
    }

    //뷰홀더
    private class ViewHolder {
        private TextView userID;
        private TextView Amount;
        private EditText usercost;
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
            viewHolder.usercost = (EditText) convertView.findViewById(R.id.et_Ucost_host);
            viewHolder.check = (CheckBox) convertView.findViewById(R.id.cb_check_host);


            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        //viewHolder.usercost.setText(listViewItemList.get(position).getDirectInputAmount()+"");

//        viewHolder.usercost.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
////                // 입력되는 텍스트에 변화가 있을 때
////                if(viewHolder.usercost.getText().toString().length() > 0) {
////                    if (Integer.parseInt(viewHolder.usercost.getText().toString()) > listViewItemList.get(position).getAmount() / participantCount) {
////                        viewHolder.usercost.setText("0");
////                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
////                        AlertDialog dialog = builder.setMessage("입력한 금액이 총 금액보다 많습니다.")
////                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
////                                    @Override
////                                    public void onClick(DialogInterface dialog, int which) {
////
////                                    }
////                                }).setCancelable(false)
////                                .create();
////                        dialog.show();
////                    } else{
//
//                //}
////                } else {
////                    viewHolder.usercost.setText("0");
////                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//                // 입력이 끝났을 때
//                listViewItemList.get(position).setDirectInputAmount(Integer.parseInt(viewHolder.usercost.getText().toString()));
//                Log.d("적용금액", viewHolder.usercost.getText().toString());
//                notifyDataSetChanged();
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // 입력하기 전에
//
//            }
//        });


        viewHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.check.isChecked()) {
                    listViewItemList.get(position).setPrePaymentCheck(true);
                    Log.d("체크확인", viewHolder.check.isChecked() + "");
                } else {
                    listViewItemList.get(position).setPrePaymentCheck(false);
                    Log.d("체크확인", viewHolder.check.isChecked() + "");
                }
            }
        });

        viewHolder.userID.setText(listViewItemList.get(position).getUserID());
        viewHolder.Amount.setText(listViewItemList.get(position).getAmount() / participantCount + "원");
        //usercost.setText("");

        //-----변동 data------//
        //        String Susercost = viewHolder.usercost.getText().toString();
        //        Boolean Bcheck = viewHolder.check.isChecked();
        //        //--------------------//
        return convertView;
    }
}
