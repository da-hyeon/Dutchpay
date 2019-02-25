package com.example.mp_3.dutchpayapp.Activity.SendMoneyActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mp_3.dutchpayapp.Activity.MainActivity;
import com.example.mp_3.dutchpayapp.Activity.StartDutchPayActivity.QRCodeCreateActivity;
import com.example.mp_3.dutchpayapp.Class.Adapter.ListViewAdapter.DutchStartListViewAdapter;
import com.example.mp_3.dutchpayapp.Class.Adapter.ListViewAdapter.TelephoneDirectoryListViewAdapter;
import com.example.mp_3.dutchpayapp.Class.ListItemClass.ListViewItem_TelephoneDirectory;
import com.example.mp_3.dutchpayapp.Class.RequestClass.QRCancel_DBDeleteRequest;
import com.example.mp_3.dutchpayapp.Fragment.SendMoneyFragment;
import com.example.mp_3.dutchpayapp.R;
import com.google.android.gms.analytics.ecommerce.Product;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FindRemittancePartnerActivity extends AppCompatActivity {

    public String amount;
    public TextView txt_title;
    public EditText edit_searchTelephoneDirectories;

    private ListView mListview;
    private TelephoneDirectoryListViewAdapter telephoneDirectoryListViewAdapter;
    private List<ListViewItem_TelephoneDirectory> telephoneDirectories;
    private List<ListViewItem_TelephoneDirectory> saveList;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_remittance_partner);

        amount = getIntent().getExtras().getString("data");
        mListview = (ListView) findViewById(R.id.list);
        txt_title = (TextView) findViewById(R.id.txt_title);
        edit_searchTelephoneDirectories = (EditText) findViewById(R.id.edit_searchTelephoneDirectories);
        edit_searchTelephoneDirectories.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchTelephoneDirectories(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txt_title.setText(String.format("%,d", Integer.parseInt(amount)) + "원 송금하기");

        telephoneDirectories = new ArrayList<>();
        saveList = new ArrayList<>();
        try {
            Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null,
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " asc");

            while (c.moveToNext()) {
                // 연락처 id 값
                String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                // 연락처 대표 이름
                String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));

                // ID로 전화 정보 조회
                Cursor phoneCursor = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                        null, null);

                // 데이터가 있는 경우
                if (phoneCursor.moveToFirst()) {
                    String number = phoneCursor.getString(phoneCursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER));
                    telephoneDirectories.add(new ListViewItem_TelephoneDirectory(id, name, PhoneNumberUtils.formatNumber(number, Locale.getDefault().getCountry())));
                    saveList.add(new ListViewItem_TelephoneDirectory(id, name, number.replace("-" , "")));
                }

                phoneCursor.close();
            }// end while
            c.close();
        } catch (SecurityException e){
            AlertDialog.Builder builder = new AlertDialog.Builder(FindRemittancePartnerActivity.this);
            AlertDialog dialog = builder.setMessage("저장된 연락처가 없습니다.")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setCancelable(false)
                    .create();
            dialog.show();
        }

        telephoneDirectoryListViewAdapter = new TelephoneDirectoryListViewAdapter(getApplicationContext(), telephoneDirectories);
        mListview.setAdapter(telephoneDirectoryListViewAdapter);

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FindRemittancePartnerActivity.this , SendMoneyPasswordCheckActivity.class);
                intent.putExtra("amount",amount);
                startActivity(intent);
                finish();
                //Toast.makeText(FindRemittancePartnerActivity.this, PhoneNumberUtils.formatNumber(telephoneDirectories.get(position).getUserPhoneNumber(), Locale.getDefault().getCountry()), Toast.LENGTH_SHORT).show();
            }
        });

        MainActivity.customProgressDialog.dismiss();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void searchTelephoneDirectories(String search) {
        telephoneDirectories.clear();
        for (int i = 0; i < saveList.size(); i++) {
            if (isStringDouble(search)) {
                if (saveList.get(i).getUserPhoneNumber().contains(search)) {
                    telephoneDirectories.add(saveList.get(i));
                }
            } else {
                if (saveList.get(i).getUserName().contains(search)) {
                    telephoneDirectories.add(saveList.get(i));
                }
            }
        }

        //입력한값이 리스트에 없을때
        if(telephoneDirectories.size() == 0){
            if (isStringDouble(search)) {
                telephoneDirectories.add(new ListViewItem_TelephoneDirectory("0", PhoneNumberUtils.formatNumber(search, Locale.getDefault().getCountry()), "위의 연락처로 송금하기"));
            } else {
                telephoneDirectories.add(new ListViewItem_TelephoneDirectory("0", "입력한 결과를 찾지 못했습니다.", "연락처로 검색하세요."));
            }
        }
        telephoneDirectoryListViewAdapter.notifyDataSetChanged();
    }

    public static boolean isStringDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
