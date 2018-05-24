package com.liennhutkhang.admin.appbanhangandroid.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.liennhutkhang.admin.appbanhangandroid.R;
import com.liennhutkhang.admin.appbanhangandroid.adapter.ProductAdapter;
import com.liennhutkhang.admin.appbanhangandroid.adapter.ProductTypeAdapter;
import com.liennhutkhang.admin.appbanhangandroid.model.Cart;
import com.liennhutkhang.admin.appbanhangandroid.model.Product;
import com.liennhutkhang.admin.appbanhangandroid.model.ProductType;
import com.liennhutkhang.admin.appbanhangandroid.ultil.CheckConnection;
import com.liennhutkhang.admin.appbanhangandroid.ultil.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbarMain;
    ViewFlipper viewFlipperMain;
    RecyclerView recyclerViewMain;
    NavigationView navigationViewMain;
    ListView listViewMain;
    DrawerLayout drawerLayoutMain;
    ArrayList<ProductType> arrayListProductType;
    ProductTypeAdapter productTypeAdapter;
    int id = 0;
    String namePT = "";
    String imagePT = "";
    ArrayList<Product> arrayListProduct;
    ProductAdapter productAdapter;
    public static ArrayList<Cart> arrayListCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            GetDataProductType();
            GetDataProductNew();
            OnClickItemListView();
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

    private void OnClickItemListView() {
        listViewMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplication())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Please check the connection again!");
                        }
                        drawerLayoutMain.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplication())){
                            Intent intent = new Intent(MainActivity.this,SmartPhoneActivity.class);
                            intent.putExtra("idProductType",arrayListProductType.get(position).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Please check the connection again!");
                        }
                        drawerLayoutMain.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplication())){
                            Intent intent = new Intent(MainActivity.this,LaptopActivity.class);
                            intent.putExtra("idProductType",arrayListProductType.get(position).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Please check the connection again!");
                        }
                        drawerLayoutMain.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplication())){
                            Intent intent = new Intent(MainActivity.this,ContactActivity.class);
                            intent.putExtra("idProductType",arrayListProductType.get(position).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Please check the connection again!");
                        }
                        drawerLayoutMain.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckConnection.haveNetworkConnection(getApplication())){
                            Intent intent = new Intent(MainActivity.this,InformationAppActivity.class);
                            intent.putExtra("idProductType",arrayListProductType.get(position).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Please check the connection again!");
                        }
                        drawerLayoutMain.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetDataProductNew() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.pathNew, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    int Id = 0;
                    String nameProduct = "";
                    Integer priceProduct = 0;
                    String imageProduct = "";
                    String descriptionProduct = "";
                    int IdProduct = 0;
                    for (int i = 0; i < response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Id = jsonObject.getInt("id");
                            nameProduct = jsonObject.getString("tensp");
                            priceProduct = jsonObject.getInt("giasp");
                            imageProduct = jsonObject.getString("hinhanhsp");
                            descriptionProduct = jsonObject.getString("motasp");
                            IdProduct = jsonObject.getInt("idsanpham");
                            arrayListProduct.add(new Product(Id,nameProduct,priceProduct,
                                    imageProduct,descriptionProduct,IdProduct));
                            productAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDataProductType() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.path, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    for (int i = 0; i < response.length(); i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            namePT = jsonObject.getString("tenloaisp");
                            imagePT = jsonObject.getString("hinhanhloaisp");
                            arrayListProductType.add(new ProductType(id,namePT,imagePT));
                            productTypeAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    arrayListProductType.add(3, new ProductType(0,"Liên Hệ","http://webiconspng.com/wp-content/uploads/2017/01/Blue-Mobile-Phone-Icon-PNG.png"));
                    arrayListProductType.add(4, new ProductType(0,"Thông Tin","https://i.pinimg.com/originals/7d/5b/67/7d5b67b70c060b1f4b1cac9d642d75ce.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {
        ArrayList<String> arrayListAdvertisement = new ArrayList<>();
        arrayListAdvertisement.add("https://cdn1.tgdd.vn/qcao/28_04_2018_11_02_18_BigOppo-800-300.png");
        arrayListAdvertisement.add("https://cdn4.tgdd.vn/qcao/02_05_2018_10_15_13_S9+-Normalsale2-800-300.png");
        arrayListAdvertisement.add("https://cdn2.tgdd.vn/qcao/30_04_2018_10_57_46_Huawai-Nova-3i-pink-800-300.png");
        for (int i = 0; i <arrayListAdvertisement.size(); i++){
            ImageView imageViewMain = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(arrayListAdvertisement.get(i)).into(imageViewMain);
            imageViewMain.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipperMain.addView(imageViewMain);
        }
        viewFlipperMain.setFlipInterval(5000);
        viewFlipperMain.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipperMain.setInAnimation(animation_slide_in);
        viewFlipperMain.setOutAnimation(animation_slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbarMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarMain.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbarMain.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutMain.openDrawer(GravityCompat.START);
            }
        });
    }

    private void init() {
        toolbarMain = (Toolbar) findViewById(R.id.toolBarMain);
        viewFlipperMain = (ViewFlipper) findViewById(R.id.viewFlipperMain);
        recyclerViewMain = (RecyclerView) findViewById(R.id.recyclerViewMain);
        navigationViewMain = (NavigationView) findViewById(R.id.navigationViewMain);
        listViewMain = (ListView) findViewById(R.id.listViewMain);
        drawerLayoutMain = (DrawerLayout) findViewById(R.id.drawerLayoutMain);
        arrayListProductType = new ArrayList<>();
        arrayListProductType.add(0, new ProductType(0, "Trang Chủ","http://www.vuahaisan.vn/uploads/Icon/icon-home.png"));
        productTypeAdapter = new ProductTypeAdapter(arrayListProductType,getApplicationContext());
        listViewMain.setAdapter(productTypeAdapter);
        arrayListProduct = new ArrayList<Product>();
        productAdapter = new ProductAdapter(getApplicationContext(),arrayListProduct);
        recyclerViewMain.setHasFixedSize(true);
        recyclerViewMain.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewMain.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(15), true));
        recyclerViewMain.setItemAnimator(new DefaultItemAnimator());
        recyclerViewMain.setAdapter(productAdapter);
        if (arrayListCart != null){

        }else {
            arrayListCart = new ArrayList<>();
        }
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
