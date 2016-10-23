package com.example.sic.myrestfulclienttest;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.AnimationRes;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    ProductAdapter adapter;

    @ViewById(R.id.list)
    RecyclerView recyclerView;

    @RestService
    ProductsApi restClient;

    @AnimationRes
    Animation fadeIn;

    @AfterViews
    void initList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProductAdapter(this,restClient);
        recyclerView.setAdapter(adapter);
        getProducts();
    }

    @Click(R.id.add)
    void addProductClick() {
        final Product product = new Product();
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setTitle(getString(R.string.product_add));

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.alert_product_add, null);

        final EditText editName = (EditText) promptView.findViewById(R.id.name);
        final EditText editCost = (EditText) promptView.findViewById(R.id.cost);

        Button okButton = (Button) promptView.findViewById(R.id.apply);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product.setName(editName.getText().toString());
                product.setCost(Long.valueOf(editCost.getText().toString()));
                addProduct(product);
                alertDialog.dismiss();
            }
        });
        Button cancelButton = (Button) promptView.findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(promptView);
        alertDialog.show();
    }

    @Background
    void getProducts() {
        updateList(restClient.getProducts());
    }

    @Background
    void addProduct(Product product) {
        restClient.addProduct(product);
        updateList(restClient.getProducts());
    }

    @UiThread
    void updateList(List<Product> products) {
        adapter.clear();
        adapter.addAll(products);
        recyclerView.startAnimation(fadeIn);
    }
}
