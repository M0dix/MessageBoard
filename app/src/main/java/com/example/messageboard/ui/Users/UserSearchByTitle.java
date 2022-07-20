package com.example.messageboard.ui.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messageboard.Model.Products;
import com.example.messageboard.Prevalent.Prevalent;
import com.example.messageboard.R;
import com.example.messageboard.ViewHolders.ProductViewHolder;
import com.example.messageboard.ui.LoginActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class UserSearchByTitle extends AppCompatActivity {
    private EditText title;
    DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    private Query query;
    RecyclerView.LayoutManager layoutManager;
    private Button checkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search_by_title);
        checkBtn = findViewById(R.id.search_title_btn);
        title = findViewById(R.id.search_title_et);
        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }


    public void checkProducts(View view) {
        String titleFilter = title.getText().toString();
        if (TextUtils.isEmpty(titleFilter)) {
            Toast.makeText(this, "Вы ничего не ввели", Toast.LENGTH_SHORT).show();
            return;
        }
        query = FirebaseDatabase.getInstance().getReference().child("Products")
                .orderByChild("pname").equalTo(titleFilter);

        FirebaseRecyclerOptions<Products> options;

        options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(query, Products.class).build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                holder.txtProductDescription.setText(model.getDescription());
                holder.txtProductName.setText(model.getPname());
                holder.txtProductPrice.setText("Цена: " + model.getPrice() + " рублей");
                Picasso.get().load(model.getImage()).into(holder.imageView);
                holder.setOnClickListener(new ProductViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent nextProduct = new Intent(getApplicationContext(), ProductItemActivity.class);
                        nextProduct.putExtra("product_info", model);
                        Toast.makeText(UserSearchByTitle.this, model.getPhone(), Toast.LENGTH_SHORT).show();
                        startActivity(nextProduct);
                    }
                });
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}