package com.liennhutkhang.admin.appbanhangandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.liennhutkhang.admin.appbanhangandroid.R;
import com.liennhutkhang.admin.appbanhangandroid.adapter.PhoneAdapter;
import com.liennhutkhang.admin.appbanhangandroid.model.Product;
import com.liennhutkhang.admin.appbanhangandroid.ultil.CheckConnection;
import com.liennhutkhang.admin.appbanhangandroid.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SmartPhoneActivity extends AppCompatActivity {
    Toolbar toolbarPhone;
    ListView listViewPhone;
    PhoneAdapter phoneAdapter;
    ArrayList<Product> arrayListPhone;
    int idPhone =0;
    int page =1;
    View footerViewPhone;
    boolean isLoading = false;
    boolean limitData = false;
    mHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_phone);
        init();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetIdProductType();
            ActionToolBar();
            GetDataPhone(page);
            LoadMoreData();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Please check the connection again!");
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mennu_cart :
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void LoadMoreData() {


        listViewPhone.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 &&
                        isLoading == false && limitData == false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
//        listViewPhone.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(SmartPhoneActivity.this,ProductDetailActivity.class);
//                intent.putExtra("information",arrayListPhone.get(position));
//                startActivity(intent);
//            }
//        });
    }

    private void GetDataPhone(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String pathPhone = Server.pathPhone + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, pathPhone, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int idPhone = 0;
                String namePhone = "";
                int pricePhone = 0;
                String imagePhone = "";
                String descriptionPhone = "";
                int idProductPhone =0;
                if (response !=null && response.length() != 2){
                    listViewPhone.removeFooterView(footerViewPhone);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject= jsonArray.getJSONObject(i);
                            idPhone = jsonObject.getInt("id");
                            namePhone = jsonObject.getString("tensp");
                            pricePhone = jsonObject.getInt("giasp");
                            imagePhone=jsonObject.getString("hinhanhsp");
                            descriptionPhone = jsonObject.getString("motasp");
                            idProductPhone = jsonObject.getInt("idsanpham");
                            arrayListPhone.add(new Product(idPhone,namePhone,pricePhone,imagePhone,descriptionPhone,idProductPhone));
                            phoneAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    limitData =true;
                    listViewPhone.removeFooterView(footerViewPhone);
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Expected data.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("idsanpham",String.valueOf(idPhone));
                return super.getParams();
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarPhone);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarPhone.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIdProductType() {
        idPhone = getIntent().getIntExtra("idProductType",-1);
        Log.d("giatrsp",idPhone +"");
    }

    private void init() {
        toolbarPhone = (Toolbar) findViewById(R.id.toolBarPhone);
        listViewPhone = (ListView) findViewById(R.id.listViewPhone);
        arrayListPhone = new ArrayList<>();
        phoneAdapter = new PhoneAdapter(getApplicationContext(),arrayListPhone);
        listViewPhone.setAdapter(phoneAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerViewPhone = inflater.inflate(R.layout.progress_bar,null);
        mHandler = new mHandler();
    }

    public class mHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    listViewPhone.addFooterView(footerViewPhone);
                    break;
                case 1:
//                    page++;
//                    GetDataPhone(page);
                    GetDataPhone(++page);
                    isLoading =false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
