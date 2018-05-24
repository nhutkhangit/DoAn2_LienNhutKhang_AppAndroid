package com.liennhutkhang.admin.appbanhangandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by Nhut Khang on 17/04/2018.
 */

//public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemHolder> {
//    Context context;
//    ArrayList<Product> arrayListProduct;
//
//    public ProductAdapter(Context context, ArrayList<Product> arrayListProduct) {
//        this.context = context;
//        this.arrayListProduct = arrayListProduct;
//    }
//
//    @Override
//    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_new,null);
//        ItemHolder itemHolder = new ItemHolder(v);
//        return itemHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(ItemHolder holder, int position) {
//        Product product = arrayListProduct.get(position);
//        holder.textViewNameProduct.setText(product.getNameProduct());
//        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//        holder.textViewPriceProduct.setText("Price : " + decimalFormat
//                .format(product.getPriceProduct()) + "VND");
//        Picasso.with(context).load(product.getImageProduct()).placeholder(R.drawable.images)
//                .error(R.drawable.notavailable)
//                .into(holder.imageViewProduct);
//    }
//
//    @Override
//    public int getItemCount() {
//        return arrayListProduct.size();
//    }
//
//    public class ItemHolder extends RecyclerView.ViewHolder{
//        public ImageView imageViewProduct;
//        public TextView textViewNameProduct,textViewPriceProduct;
//
//        public ItemHolder(View itemview){
//            super(itemview);
//            imageViewProduct = (ImageView) itemview.findViewById(R.id.imageViewProduct);
//            textViewNameProduct = (TextView) itemview.findViewById(R.id.textViewNameProduct);
//            textViewPriceProduct = (TextView) itemview.findViewById(R.id.textViewPriceProduct);
//        }
//    }
//}
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Product> list_data;
    public ProductAdapter(Context context, ArrayList<Product> listWorkers) {
        this.context = context;
        this.list_data = listWorkers;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_new,parent,false);
        return new ViewHolder(itemView,context,list_data);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Product product = list_data.get(position);
        holder.textViewNameProduct.setText(product.getNameProduct());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.textViewPriceProduct.setText("Giá : " + decimalFormat
                .format(product.getPriceProduct()) + "VND");
        Picasso.with(context).load(product.getImageProduct()).placeholder(R.drawable.images)
                .error(R.drawable.notavailable)
                .into(holder.imageViewProduct);
        holder.linearLayoutProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ProductDetailActivity.class);
                intent.putExtra("information",product);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }
    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public ImageView imageViewProduct;
        public TextView textViewNameProduct,textViewPriceProduct;
        public LinearLayout linearLayoutProduct;
        ArrayList<Product> list_data = new ArrayList<Product>();

        Context context;

        public ViewHolder(View itemView , Context context, ArrayList<Product> list_data) {
            super(itemView);
            this.list_data = list_data;
            this.context = context;
            imageViewProduct = (ImageView) itemView.findViewById(R.id.imageViewProduct);
            textViewNameProduct = (TextView) itemView.findViewById(R.id.textViewNameProduct);
            textViewPriceProduct = (TextView) itemView.findViewById(R.id.textViewPriceProduct);
            linearLayoutProduct = (LinearLayout) itemView.findViewById(R.id.linearLayoutProduct);
        }
    }

    @Override
    public int getItemCount() {
        if (null == list_data) return 0;
        return list_data.size();
    }
}
