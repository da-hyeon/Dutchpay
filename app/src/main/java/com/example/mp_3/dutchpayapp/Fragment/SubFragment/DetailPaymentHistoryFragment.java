package com.example.mp_3.dutchpayapp.Fragment.SubFragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mp_3.dutchpayapp.Class.Adapter.ListViewAdapter.TotalHistoryListViewAdapter;
import com.example.mp_3.dutchpayapp.Class.ListItemClass.ListViewItem_TotalHistory;
import com.example.mp_3.dutchpayapp.Class.SingletonClass.UserInfo;
import com.example.mp_3.dutchpayapp.Interface.DataListener;
import com.example.mp_3.dutchpayapp.R;
import com.ramotion.foldingcell.FoldingCell;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class DetailPaymentHistoryFragment extends Fragment {

    private DataListener dataListener;
    private TotalHistoryListViewAdapter totalHistoryListViewAdapter;

    private ArrayList<ListViewItem_TotalHistory> listViewItem_totalHistories;

    private ListView list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_payment_history, null);

        listViewItem_totalHistories = new ArrayList<>();

        new BackGroundTask().execute();

        list = (ListView) view.findViewById(R.id.lv_pay_history);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ((FoldingCell) view).toggle(false);

//                ListViewItem_TotalHistory item = (ListViewItem_TotalHistory) parent.getItemAtPosition(position);
//
//
//                //프래그먼트 전환
//                ArrayList<String> strings = new ArrayList<String>();
//                strings.add(item.getDate());
//                strings.add(item.getCompanyName());
//                strings.add(item.getParticipantName());
//                strings.add(item.getAmount() + "");
//
//                //액티비티 전환
//                dataListener.ArrayListenerSet(strings);

            }
        });

        return view;
    }

    private class BackGroundTask extends AsyncTask<Void, Void, String> {
        String target;

        int remittanceID;
        int remittanceAmount;
        String remittererID;
        String receiverID;
        String date;

        UserInfo userInfo;

        @Override
        protected void onPreExecute() {
            try {
                userInfo = UserInfo.getInstance();

                target = "http://kjg123kg.cafe24.com/DutchPay_RemittanceHistory_ListRequest.php?userID=" + URLEncoder.encode(userInfo.getUserID(), "UTF-8");
                Log.d("target", target);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;

                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);

                    remittanceID = object.getInt("remittanceID");
                    remittanceAmount = object.getInt("remittanceAmount");
                    remittererID = object.getString("remittererID");
                    receiverID = object.getString("receiverID");
                    date = object.getString("date");

                    //보냄
                    if(remittererID.equals(userInfo.getUserID())){
                        listViewItem_totalHistories.add(new ListViewItem_TotalHistory("보냄", date.substring(0,10), date.substring(11,date.length()) , remittererID , receiverID ,  remittanceAmount));
                    }
                    //받음
                    else {
                        listViewItem_totalHistories.add(new ListViewItem_TotalHistory("받음", date.substring(0,10), date.substring(11,date.length()) , remittererID , receiverID ,  remittanceAmount));
                    }
                    count++;
                }

                totalHistoryListViewAdapter = new TotalHistoryListViewAdapter(getContext(), listViewItem_totalHistories);
                list.setAdapter(totalHistoryListViewAdapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}