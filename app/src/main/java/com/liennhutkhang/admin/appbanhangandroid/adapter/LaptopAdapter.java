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
 * Created by Nhut Khang on 15/04/2018.
 */

public class LaptopAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arrayListLaptopProduct;

    public LaptopAdapter(Context context, ArrayList<Product> arrayListProduct) {
        this.context = context;
        this.arrayListLaptopProduct = arrayListProduct;
    }

    @Override
    public int getCount() {
        return arrayListLaptopProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListLaptopProduct.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView textViewNameLaptop, textViewPriceLaptop, textViewDescriptionLaptop;
        public ImageView imageViewLaptop;
        public LinearLayout linearLayoutLaptop;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_laptop,null);
            viewHolder.textViewNameLaptop = (TextView) view.findViewById(R.id.textViewNameLaptop);
            viewHolder.textViewPriceLaptop = (TextView) view.findViewById(R.id.textViewPriceLaptop);
            viewHolder.textViewDescriptionLaptop = (TextView) view.findViewById(R.id.textViewDescriptionLaptop);
            viewHolder.imageViewLaptop = (ImageView) view.findViewById(R.id.imageViewLaptop);
            viewHolder.linearLayoutLaptop = (LinearLayout) view.findViewById(R.id.linearLayoutLaptop);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final Product product = (Product) getItem(position);
        viewHolder.textViewNameLaptop.setText(product.getNameProduct());
        viewHolder.textViewNameLaptop.setSelected(true);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.textViewPriceLaptop.setText("Giá : " + decimalFormat
                .format(product.getPriceProduct()) + " VND");
        viewHolder.textViewDescriptionLaptop.setMaxLines(2);
        viewHolder.textViewDescriptionLaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.textViewDescriptionLaptop.setText(product.getDescriptionProduct());
        Picasso.with(context).load(product.getImageProduct()).placeholder(R.drawable.images)
                .error(R.drawable.notavailable)
                .into(viewHolder.imageViewLaptop);
        viewHolder.linearLayoutLaptop.setOnClickListener(new View.OnClickListener() {
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
