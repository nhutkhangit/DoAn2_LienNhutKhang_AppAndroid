package com.liennhutkhang.admin.appbanhangandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.liennhutkhang.admin.appbanhangandroid.R;
import com.liennhutkhang.admin.appbanhangandroid.model.Cart;
import com.liennhutkhang.admin.appbanhangandroid.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ProductDetailActivity extends AppCompatActivity {
    Toolbar toolbarProductDetail;
    ImageView imageViewProductDetail;
    TextView textViewNameProductDetail,textViewPriceProductDetail,textViewDescriptionProductDetail;
    Spinner spinnerProductDetail;
    Button buttonAddCartProductDetail;
    int id = 0;
    String name = "";
    int price = 0;
    String image = "";
    String description = "";
    int idProduct = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        init();
        ActionToolBar();
        GetDataProductDetail();
        CatchEvenSpinner();
        EventButton();
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

    private void EventButton() {
        buttonAddCartProductDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.arrayListCart.size() > 0){
                    int sl = Integer.parseInt(spinnerProductDetail.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i = 0; i < MainActivity.arrayListCart.size(); i++){
                        if (MainActivity.arrayListCart.get(i).getIdCart() == id){
                            MainActivity.arrayListCart.get(i).setNumberCart(MainActivity.
                                    arrayListCart.get(i).getNumberCart() + sl);
                            if (MainActivity.arrayListCart.get(i).getNumberCart() > 9){
                                MainActivity.arrayListCart.get(i).setNumberCart(10);
                            }
                            MainActivity.arrayListCart.get(i).setPriceCart(price * MainActivity.
                                    arrayListCart.get(i).getNumberCart());
                            exists = true;
                        }
                    }
                    if (exists == false){
                        int number = Integer.parseInt(spinnerProductDetail.getSelectedItem().toString());
                        long priceNew = number * price;
                        MainActivity.arrayListCart.add(new Cart(id,name,priceNew,image,number));
                    }
                }else {
                    int number = Integer.parseInt(spinnerProductDetail.getSelectedItem().toString());
                    long priceNew = number * price;
                    MainActivity.arrayListCart.add(new Cart(id,name,priceNew,image,number));
                }
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEvenSpinner() {
        Integer[] number = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapterProductDetail = new ArrayAdapter<Integer>
                (this,R.layout.spinner_item,number);
        arrayAdapterProductDetail.setDropDownViewResource(R.layout.spinner_item);
        spinnerProductDetail.setAdapter(arrayAdapterProductDetail);
    }

    private void GetDataProductDetail() {

        Product product = (Product) getIntent().getSerializableExtra("information");
        id = product.getId();
        name = product.getNameProduct();
        price = product.getPriceProduct();
        image = product.getImageProduct();
        description = product.getDescriptionProduct();
        idProduct = product.getIdProduct();

        textViewNameProductDetail.setText(name);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        textViewPriceProductDetail.setText("Giá : " + decimalFormat.format(price) + " VND ");
        textViewDescriptionProductDetail.setText(description);
        Picasso.with(getApplicationContext()).load(image)
                .placeholder(R.drawable.images)
                .error(R.drawable.notavailable)
                .into(imageViewProductDetail);

    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarProductDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarProductDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        toolbarProductDetail = (Toolbar) findViewById(R.id.toolbarProductDetail);
        imageViewProductDetail = (ImageView) findViewById(R.id.imageViewProductDetail);
        textViewNameProductDetail = (TextView) findViewById(R.id.textViewNameProductDetail);
        textViewPriceProductDetail = (TextView) findViewById(R.id.textViewPriceProductDetail);
        textViewDescriptionProductDetail = (TextView) findViewById(R.id.textViewDescriptionProductDetail);
        spinnerProductDetail = (Spinner) findViewById(R.id.spinnerProductDetail);
        buttonAddCartProductDetail= (Button) findViewById(R.id.buttonAddCart);
    }
}
