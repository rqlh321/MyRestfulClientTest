package com.example.sic.myrestfulclienttest;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Product> products = new ArrayList<>();
    private ProductsApi restClient;

    public ProductAdapter(Context context, ProductsApi restClient) {
        this.context = context;
        this.restClient = restClient;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText(products.get(position).getName());
        holder.cost.setText(String.valueOf(products.get(position).getCost()));
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Product product = products.get(holder.getAdapterPosition());
                final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setTitle(context.getString(R.string.product_edit));

                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View promptView = layoutInflater.inflate(R.layout.alert_product_edit, null);

                final EditText editName = (EditText) promptView.findViewById(R.id.name);
                editName.setText(product.getName());
                final EditText editCost = (EditText) promptView.findViewById(R.id.cost);
                editCost.setText(String.valueOf(product.getCost()));

                Button okButton = (Button) promptView.findViewById(R.id.apply);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        product.setName(editName.getText().toString());
                        product.setCost(Long.valueOf(editCost.getText().toString()));
                        update(product);
                        notifyDataSetChanged();
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
                ImageButton deleteButton = (ImageButton) promptView.findViewById(R.id.delete);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete(String.valueOf(product.getId()));
                        products.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                        alertDialog.dismiss();
                    }
                });
                alertDialog.setView(promptView);
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void add(Product product) {
        products.add(product);
        notifyDataSetChanged();
    }

    public void addAll(List<Product> products) {
        this.products.addAll(products);
        notifyDataSetChanged();
    }

    public void clear() {
        products.clear();
        notifyDataSetChanged();
    }

    private void update(final Product product) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                restClient.updateProduct(product);
            }
        }.start();
    }

    private void delete(final String id) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                restClient.deleteProduct(id);
            }
        }.start();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView cost;
        ImageButton edit;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            cost = (TextView) view.findViewById(R.id.cost);
            edit = (ImageButton) view.findViewById(R.id.edit);
        }
    }
}
