package com.liennhutkhang.admin.appbanhangandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.liennhutkhang.admin.appbanhangandroid.R;
import com.liennhutkhang.admin.appbanhangandroid.ultil.CheckConnection;
import com.liennhutkhang.admin.appbanhangandroid.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InformationUserActivity extends AppCompatActivity {
    EditText editTextNameCustomer, editTextPhoneCustomer, editTextEmailCustomer;
    Button buttonConfrim, buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_user);
        init();
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            EventButton();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Please check the connection again!");
            finish();
        }
    }
    private void EventButton() {
        buttonConfrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final String name = editTextNameCustomer.getText().toString().trim();
                    final String phone = editTextPhoneCustomer.getText().toString().trim();
                    final String email = editTextEmailCustomer.getText().toString().trim();
                    if (name.length() > 0 && phone.length() > 0 && email.length() > 0) {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                Server.pathOrder, new Response.Listener<String>() {
                            @Override
                            public void onResponse(final String madonhang) {


                                Log.d("madonhang",madonhang);
                                if (
                                       madonhang!=""
                                        ){
                                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                    StringRequest request = new StringRequest(Request.Method.POST,
                                            Server.pathOrderDetail, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if (response.equals("1")) {
                                                MainActivity.arrayListCart.clear();
                                                CheckConnection.ShowToast_Short(getApplicationContext(),
                                                        "Bạn đã gửi dữ liệu mua giỏ hàng thành công,Hãy tiếp tục mua sắm với chúng tôi! ");
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                                CheckConnection.ShowToast_Short(getApplicationContext(),
                                                        "Vui lòng tiếp tục mua hàng. ");
                                            } else {
                                                MainActivity.arrayListCart.clear();
                                                CheckConnection.ShowToast_Short(getApplicationContext(),
                                                        "Bạn đã gửi dữ liệu mua giỏ hàng thành công,Hãy tiếp tục mua sắm với chúng tôi!");

                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);

                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            JSONArray jsonArray = new JSONArray();
                                            for (int i = 0; i < MainActivity.arrayListCart.size(); i++) {
                                                JSONObject jsonObject = new JSONObject();
                                                try {
//                                                    jsonObject.put("madonhang", phone);
                                                    jsonObject.put("masanpham",""+MainActivity.arrayListCart.get(i).getIdCart()+"");
                                                    jsonObject.put("tensanpham", MainActivity.arrayListCart.get(i).getNameCart());
                                                    jsonObject.put("giasanpham",""+MainActivity.arrayListCart.get(i).getPriceCart()+"");
                                                    jsonObject.put("soluongsanpham",""+MainActivity.arrayListCart.get(i).getNumberCart()+"");


                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                jsonArray.put(jsonObject);
                                            }
                                            HashMap<String, String> hashMap = new HashMap<String, String>();
                                            hashMap.put("json", jsonArray.toString());
                                            //xuất dữ liệu kiểm tra thử
                                        Log.d("chuổi jsson",jsonArray.toString());
                                            return hashMap;
                                        }
                                    };
                                    queue.add(request);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {

                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                hashMap.put("tenkhachhang", name);
                                hashMap.put("sodienthoai", phone);
                                hashMap.put("email", email);
                                return hashMap;
                            }

                        };
                        requestQueue.add(stringRequest);

                    } else {
                        CheckConnection.ShowToast_Short(getApplicationContext(), "Please check again data!");
                    }
            }
        });


    }

    private void init() {
        editTextNameCustomer = (EditText) findViewById(R.id.editTextNameCustomer);
        editTextPhoneCustomer = (EditText) findViewById(R.id.editTextPhoneCustomer);
        editTextEmailCustomer = (EditText) findViewById(R.id.editTextEmailCustomer);
        buttonConfrim = (Button) findViewById(R.id.buttonConfrim);
        buttonBack = (Button) findViewById(R.id.buttonBack);
    }
}
