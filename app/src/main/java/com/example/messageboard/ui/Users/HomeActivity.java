package com.example.messageboard.ui.Users;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.messageboard.Interface.ItemClickListner;
import com.example.messageboard.Model.Products;
import com.example.messageboard.Prevalent.Prevalent;
import com.example.messageboard.R;
import com.example.messageboard.ViewHolders.ProductViewHolder;
import com.example.messageboard.databinding.ActivityHomeBinding;
import com.example.messageboard.ui.LoginActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    private Query query;
    RecyclerView.LayoutManager layoutManager;
    private String categoryFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_home);

        categoryFilter = getIntent().getStringExtra("categoryFilter");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        if (categoryFilter != null) {
            query = ProductsRef.orderByChild("category").equalTo(categoryFilter);
        }

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Меню");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, UserOrderListActivity.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);

        userNameTextView.setText(Prevalent.currentOnlineUser.getName());
        Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Products> options;

        if (categoryFilter != null) {
            options = new FirebaseRecyclerOptions.Builder<Products>()
                    .setQuery(query, Products.class).build();
        } else {
            options = new FirebaseRecyclerOptions.Builder<Products>()
                    .setQuery(ProductsRef, Products.class).build();
        }
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
                            Toast.makeText(HomeActivity.this, model.getPhone(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_cart){
            Intent cartIntent = new Intent(HomeActivity.this, UserOrderListActivity.class);
            startActivity(cartIntent);
        } else if (id == R.id.nav_title) {
            Intent titleIntent = new Intent(HomeActivity.this, UserSearchByTitle.class);
            startActivity(titleIntent);
        } else if (id == R.id.nav_cities) {
            Intent cityIntent = new Intent(HomeActivity.this, UserSearchByCity.class);
            startActivity(cityIntent);
        } else if (id == R.id.nav_categories) {
            Intent categoryIntent = new Intent(HomeActivity.this, UserCategoryActivity.class);
            startActivity(categoryIntent);
        } else if (id == R.id.nav_settings) {
            Intent settingsIntent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        } else if (id == R.id.nav_logout) {
            Paper.book().destroy();
            Intent loginIntent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}