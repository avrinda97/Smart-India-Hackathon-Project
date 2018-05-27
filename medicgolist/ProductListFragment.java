package com.example.shubhanshu.medicgolist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends Fragment implements
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    public static final String ARG_ITEM_ID = "product_list";

    Activity activity;
    ListView productListView;
    List<Product> products;
    ProductListAdapter productListAdapter;

    SharedPreference sharedPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        sharedPreference = new SharedPreference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container,
                false);
        findViewsById(view);

        setProducts();

        productListAdapter = new ProductListAdapter(activity, products);
        productListView.setAdapter(productListAdapter);
        productListView.setOnItemClickListener(this);
        productListView.setOnItemLongClickListener(this);
        return view;
    }

    private void setProducts() {



        Product product1 = new Product(1, "Paracetamol", "150mg", 50);
        Product product2 = new Product(2, "Calpol",
                "650 mg", 20);
        Product product3 = new Product(3, "Nicip Nimosulide",
                "300mg", 45);
        Product product4 = new Product(4, "Ciplox TZ",
                "500mg", 100);
        Product product5 = new Product(5, "Brufin",
                "100 mg", 100);
        Product product6 = new Product(6, "Disprin", "100mg",
                50);
        Product product7 = new Product(7, "Digene",
                "10mg", 40);
        Product product8 = new Product(8, "Aciloc",
                "100mg", 38);
        Product product9 = new Product(9, "Vomikind",
                "10mg", 10);
        Product product10 = new Product(10, "Crocin",
                "350mg", 50);

        products = new ArrayList<Product>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);
        products.add(product6);
        products.add(product7);
        products.add(product8);
        products.add(product9);
        products.add(product10);
    }

    private void findViewsById(View view) {
        productListView = (ListView) view.findViewById(R.id.list_product);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Product product = (Product) parent.getItemAtPosition(position);
        Toast.makeText(activity, product.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View view,
                                   int position, long arg3) {
        ImageView button = (ImageView) view.findViewById(R.id.imgbtn_favorite);

        String tag = button.getTag().toString();
        if (tag.equalsIgnoreCase("grey")) {
            sharedPreference.addFavorite(activity, products.get(position));
            Toast.makeText(activity,
                    activity.getResources().getString(R.string.add_favr),
                    Toast.LENGTH_SHORT).show();

            button.setTag("red");
            button.setImageResource(R.drawable.heart);
        } else {
            sharedPreference.removeFavorite(activity, products.get(position));
            button.setTag("grey");
            button.setImageResource(R.drawable.heart_gray);
            Toast.makeText(activity,
                    activity.getResources().getString(R.string.remove_favr),
                    Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    @Override
    public void onResume() {
        getActivity().setTitle(R.string.app_name);
        // getActivity().getActionBar().setTitle(R.string.app_name);
        super.onResume();
    }
}
