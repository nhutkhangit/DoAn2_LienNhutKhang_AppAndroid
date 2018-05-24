package com.liennhutkhang.admin.appbanhangandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liennhutkhang.admin.appbanhangandroid.R;
import com.liennhutkhang.admin.appbanhangandroid.activity.ProductDetailActivity;
import com.liennhutkhang.admin.appbanhangandroid.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Nhut Khang on 16/04/2018.
 */

public class PhoneAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arrayListProduct;

    public PhoneAdapter(Context context, ArrayList<Product> arrayListProduct) {
        this.context = context;
        this.arrayListProduct = arrayListProduct;
    }

    @Override
    public int getCount() {
        return arrayListProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListProduct.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView textViewNamePhone, textViewPricePhone, textViewDescriptionPhone;
        public ImageView imageViewPhone;
        public LinearLayout linearLayoutPhone;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_phone,null);
            viewHolder.textViewNamePhone = (TextView) view.findViewById(R.id.textViewNamePhone);
            viewHolder.textViewPricePhone = (TextView) view.findViewById(R.id.textViewPricePhone);
            viewHolder.textViewDescriptionPhone = (TextView) view.findViewById(R.id.textViewDescriptionPhone);
            viewHolder.imageViewPhone = (ImageView) view.findViewById(R.id.imageViewPhone);
            viewHolder.linearLayoutPhone = (LinearLayout) view.findViewById(R.id.linearLayoutPhone);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final Product product = (Product) getItem(position);
        viewHolder.textViewNamePhone.setText(product.getNameProduct());
        viewHolder.textViewNamePhone.setSelected(true);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.textViewPricePhone.setText("Giá : " + decimalFormat
                .format(product.getPriceProduct()) + " VND");
        viewHolder.textViewDescriptionPhone.setMaxLines(2);
        viewHolder.textViewDescriptionPhone.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.textViewDescriptionPhone.setText(product.getDescriptionProduct());
        Picasso.with(context).load(product.getImageProduct()).placeholder(R.drawable.images)
                .error(R.drawable.notavailable)
                .into(viewHolder.imageViewPhone);
        viewHolder.linearLayoutPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ProductDetailActivity.class);
                intent.putExtra("information",product);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
