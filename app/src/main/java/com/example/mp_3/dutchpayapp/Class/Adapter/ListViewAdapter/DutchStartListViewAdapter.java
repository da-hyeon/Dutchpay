package com.example.mp_3.dutchpayapp.Class.Adapter.ListViewAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private int totalAmount;

    //AlertDialog.Builder dialog;

    public DutchStartListViewAdapter(Context context, ArrayList<ListViewItem_DutchStart> listViewItemList, int participantCount, int totalAmount) {
        this.listViewItemList = listViewItemList;
        this.participantCount = participantCount;
        this.totalAmount = totalAmount;
        //dialog = new AlertDialog.Builder(context);
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

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        viewHolder.userID.setText(listViewItemList.get(position).getUserID());
        viewHolder.Amount.setText(String.format("%,d", listViewItemList.get(position).getAssignedAmount()) + "원");
        viewHolder.check.setChecked(listViewItemList.get(position).isPrePaymentCheck());

        viewHolder.Amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et = new EditText(context);
                et.setText(listViewItemList.get(position).getAssignedAmount() + "");
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                AlertDialog dialog = builder.setTitle("금액 직접입력")
                        .setView(et)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int directAmountTotal = 0;
                                int directAmountPersonCount = 0;
                                int maximumAmountCheck = 0;

                                //login service
                                String value = et.getText().toString();
                                int tempAmount = listViewItemList.get(position).getAssignedAmount();

                                //입력한 금액이 1원 이상이어야 함
                                if (value.length() > 0) {
                                    //입력한 금액이 총 금액보다 많을경우 총 금액으로 강제 변경
                                    if (totalAmount < Integer.parseInt(value)) {
                                        listViewItemList.get(position).setAssignedAmount(totalAmount);
                                    } else {
                                        listViewItemList.get(position).setAssignedAmount(Integer.parseInt(value));
                                    }
                                } else {
                                    listViewItemList.get(position).setAssignedAmount(0);
                                }

                                // 1/n한 금액의 차액을 총 금액에서 뺴고 나눠야하므로 차액을 구해야함.
                                for (int i = 0; i < listViewItemList.size(); i++) {
                                    maximumAmountCheck += listViewItemList.get(i).getAssignedAmount();

                                    if (listViewItemList.get(i).getAssignedAmount() == listViewItemList.get(i).getDistributedAmount())
                                        continue;

                                    //입력한 금액이 자동분배금액과 다를경우
                                    //직접입력한 값을 directAmountTotal에 더한다.
                                    directAmountTotal += listViewItemList.get(i).getAssignedAmount();
                                    directAmountPersonCount++;
                                }
                                Log.d("maximumAmountCheck" , maximumAmountCheck+"");

                                if (directAmountTotal > totalAmount){
                                    listViewItemList.get(position).setAssignedAmount(tempAmount);
                                    return;
                                }

                                //나머지를 구한다.
                                double restAmount = (totalAmount - directAmountTotal) % (listViewItemList.size() - directAmountPersonCount);
                                int shareAmount = (totalAmount - directAmountTotal) / (listViewItemList.size() - directAmountPersonCount);

                                //금액이 나누어 떨어지지 않을때
                                if (restAmount != 0) {
                                    for (int i = 0; i < listViewItemList.size(); i++) {
                                        //입력값에 변동이 없었다면
                                        if (listViewItemList.get(i).getAssignedAmount() == listViewItemList.get(i).getDistributedAmount()) {
                                            //호스트에게 나머지금액을 부담하게 함
                                            if (listViewItemList.get(i).getHostID().equals(listViewItemList.get(i).getUserID())) {
                                                listViewItemList.get(i).setAssignedAmount(listViewItemList.get(i).getAssignedAmount() + (int) restAmount);
                                                listViewItemList.get(i).setDistributedAmount(listViewItemList.get(i).getAssignedAmount());
                                            }
                                            else {
                                                //분배금액을 넣어준다.
                                                listViewItemList.get(i).setAssignedAmount(shareAmount);
                                                listViewItemList.get(i).setDistributedAmount(listViewItemList.get(i).getAssignedAmount());
                                            }
                                        }
                                        //입력값에 변동이 있었고
                                        else {
                                            //호스트라면
                                            if (listViewItemList.get(i).getHostID().equals(listViewItemList.get(i).getUserID())) {
                                                listViewItemList.get(i).setAssignedAmount(listViewItemList.get(i).getAssignedAmount() + (int) restAmount);
                                                //listViewItemList.get(i).setDistributedAmount(listViewItemList.get(i).getAssignedAmount());
                                            }
                                        }
                                    }
                                }

                                //금액이 나누어 떨어질때
                                else {
                                    for (int i = 0; i < listViewItemList.size(); i++) {
                                        //입력값에 변동이 없었다면
                                        if (listViewItemList.get(i).getAssignedAmount() == listViewItemList.get(i).getDistributedAmount()) {
                                            //분배금액을 넣어준다.
                                            listViewItemList.get(i).setAssignedAmount(shareAmount);
                                            listViewItemList.get(i).setDistributedAmount(listViewItemList.get(i).getAssignedAmount());
                                        }
                                    }
                                }

                                dataChange();
                                dialog.dismiss();     //닫기
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();     //닫기
                            }
                        })
                        .setCancelable(false)
                        .create();
                dialog.show();
            }
        });


        viewHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.check.isChecked()) {
                    listViewItemList.get(position).setPrePaymentCheck(true);
                    Log.d(listViewItemList.get(position).getAssignedAmount() +"", " : " + listViewItemList.get(position).getDistributedAmount());
                    dataChange();
                } else {
                    listViewItemList.get(position).setPrePaymentCheck(false);
                    Log.d(listViewItemList.get(position).getAssignedAmount() +"", " : " + listViewItemList.get(position).getDistributedAmount());
                    dataChange();
                }
            }
        });

        return convertView;
    }

    public void dataChange() {
        notifyDataSetChanged();
    }
}