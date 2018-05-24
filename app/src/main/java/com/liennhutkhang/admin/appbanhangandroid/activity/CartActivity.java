package com.liennhutkhang.admin.appbanhangandroid.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.liennhutkhang.admin.appbanhangandroid.R;
import com.liennhutkhang.admin.appbanhangandroid.adapter.CartAdapter;
import com.liennhutkhang.admin.appbanhangandroid.ultil.CheckConnection;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {

    ListView listViewCart;
    static TextView textViewNotifaction, textViewTotalMoney;
    Button buttonPay, buttonBuy;
    Toolbar toolbarCart;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();
        ActionToolBar();
        CheckData();
        EventUltil();
        CatchOnItemListView();
        EventButton();
    }

    private void EventButton() {
        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.arrayListCart.size() > 0 ){
                    Intent intent = new Intent(getApplicationContext(),InformationUserActivity.class);
                    startActivity(intent);
                }else {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Your cart has no product.");
                }
            }
        });
    }

    private void CatchOnItemListView() {
        listViewCart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("Confirm product deletion");
                builder.setMessage("Do you want to delete product ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.arrayListCart.size() <= 0 ){
                            textViewNotifaction.setVisibility(View.VISIBLE);
                        }else {
                            MainActivity.arrayListCart.remove(position);
                            cartAdapter.notifyDataSetChanged();
                            EventUltil();
                            if (MainActivity.arrayListCart.size() <= 0){
                                textViewNotifaction.setVisibility(View.VISIBLE);
                            }else {
                                textViewNotifaction.setVisibility(View.INVISIBLE);
                                cartAdapter.notifyDataSetChanged();
                                EventUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cartAdapter.notifyDataSetChanged();
                        EventUltil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EventUltil() {
        long totalMoney = 0;
        for (int i = 0; i < MainActivity.arrayListCart.size(); i++){
            totalMoney += MainActivity.arrayListCart.get(i).getPriceCart();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        textViewTotalMoney.setText(decimalFormat.format(totalMoney) + " VND");
    }

    private void CheckData() {
        if (MainActivity.arrayListCart.size() <= 0){
            cartAdapter.notifyDataSetChanged();
            textViewNotifaction.setVisibility(View.VISIBLE);
            listViewCart.setVisibility(View.INVISIBLE);
        }else {
            cartAdapter.notifyDataSetChanged();
            textViewNotifaction.setVisibility(View.INVISIBLE);
            listViewCart.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarCart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void init() {
        listViewCart =(ListView) findViewById(R.id.listViewCart);
        textViewNotifaction = (TextView) findViewById(R.id.textViewNotifaction);
        textViewTotalMoney = (TextView) findViewById(R.id.textViewTotalMoney);
        buttonPay = (Button) findViewById(R.id.buttonPay);
        buttonBuy = (Button) findViewById(R.id.buttonContinuePay);
        toolbarCart = (Toolbar) findViewById(R.id.toolBarCart);
        cartAdapter = new CartAdapter(CartActivity.this,MainActivity.arrayListCart);
        listViewCart.setAdapter(cartAdapter);
    }
}
