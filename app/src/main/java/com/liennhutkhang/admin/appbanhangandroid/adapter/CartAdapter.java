package com.liennhutkhang.admin.appbanhangandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.liennhutkhang.admin.appbanhangandroid.R;
import com.liennhutkhang.admin.appbanhangandroid.activity.CartActivity;
import com.liennhutkhang.admin.appbanhangandroid.activity.MainActivity;
import com.liennhutkhang.admin.appbanhangandroid.model.Cart;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Nhut Khang on 15/04/2018.
 */

public class CartAdapter extends BaseAdapter{
    Context context;
    ArrayList<Cart> arrayListCart;

    public CartAdapter(Context context, ArrayList<Cart> arrayListCart) {
        this.context = context;
        this.arrayListCart = arrayListCart;
    }

    @Override
    public int getCount() {
        return arrayListCart.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListCart.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView textViewNameCart, textViewPriceCart;
        public ImageView imageViewCart;
        public Button buttonMinus, buttonValues, buttonPlus;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_cart,null);
            viewHolder.textViewNameCart = (TextView) convertView.findViewById(R.id.textViewNameCart);
            viewHolder.textViewPriceCart = (TextView) convertView.findViewById(R.id.textViewPriceCart);
            viewHolder.imageViewCart = (ImageView) convertView.findViewById(R.id.imageViewCart);
            viewHolder.buttonMinus = (Button) convertView.findViewById(R.id.buttonMinusCart);
            viewHolder.buttonValues = (Button) convertView.findViewById(R.id.buttonValuesCart);
            viewHolder.buttonPlus = (Button) convertView.findViewById(R.id.buttonPlusCart);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Cart cart = (Cart) getItem(position);
        viewHolder.textViewNameCart.setText(cart.getNameCart());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.textViewPriceCart.setText("Giá : " + decimalFormat
                .format(cart.getPriceCart()) + " VND");
        Picasso.with(context).load(cart.getImageCart()).placeholder(R.drawable.images)
                .error(R.drawable.notavailable)
                .into(viewHolder.imageViewCart);
        viewHolder.buttonValues.setText(cart.getNumberCart() + "");
        int sl = Integer.parseInt(viewHolder.buttonValues.getText().toString());
        if (sl >= 10){
            viewHolder.buttonPlus.setVisibility(View.INVISIBLE);
            viewHolder.buttonMinus.setVisibility(View.VISIBLE);
        }else if (sl <=1){
            viewHolder.buttonPlus.setVisibility(View.VISIBLE);
            viewHolder.buttonMinus.setVisibility(View.INVISIBLE);
        }else if (sl >=1){
            viewHolder.buttonPlus.setVisibility(View.VISIBLE);
            viewHolder.buttonMinus.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slm = Integer.parseInt(finalViewHolder.buttonValues.getText().toString()) + 1;
                int slht = MainActivity.arrayListCart.get(position).getNumberCart();
                long priceNow = MainActivity.arrayListCart.get(position).getPriceCart();
                MainActivity.arrayListCart.get(position).setNumberCart(slm);
                long priceNew = (priceNow * slm)/ slht;
                MainActivity.arrayListCart.get(position).setPriceCart(priceNew);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.textViewPriceCart.setText("Giá : " + decimalFormat
                        .format(priceNew) + " VND");
                CartActivity.EventUltil();
                if (slm > 9){
                    finalViewHolder.buttonPlus.setVisibility(View.INVISIBLE);
                    finalViewHolder.buttonMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.buttonValues.setText(String.valueOf(slm));
                }else {
                    finalViewHolder.buttonPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.buttonMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.buttonValues.setText(String.valueOf(slm));
                }
            }
        });
        viewHolder.buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slm = Integer.parseInt(finalViewHolder.buttonValues.getText().toString()) - 1;
                int slht = MainActivity.arrayListCart.get(position).getNumberCart();
                long priceNow = MainActivity.arrayListCart.get(position).getPriceCart();
                MainActivity.arrayListCart.get(position).setNumberCart(slm);
                long priceNew = (priceNow * slm)/ slht;
                MainActivity.arrayListCart.get(position).setPriceCart(priceNew);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.textViewPriceCart.setText("Giá : " + decimalFormat
                        .format(priceNew) + " VND");
                CartActivity.EventUltil();
                if (slm < 2){
                    finalViewHolder.buttonPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.buttonMinus.setVisibility(View.INVISIBLE);
                    finalViewHolder.buttonValues.setText(String.valueOf(slm));
                }else {
                    finalViewHolder.buttonPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.buttonMinus.setVisibility(View.VISIBLE);
                    finalViewHolder.buttonValues.setText(String.valueOf(slm));
                }
            }
        });
        return convertView;
    }
}
