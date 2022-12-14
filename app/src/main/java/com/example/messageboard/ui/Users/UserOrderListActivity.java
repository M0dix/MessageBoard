package com.example.messageboard.ui.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.messageboard.Model.Orders;
import com.example.messageboard.Prevalent.Prevalent;
import com.example.messageboard.R;
import com.example.messageboard.ViewHolders.OrderViewHolder;
import com.example.messageboard.ui.Admin.AdminCategoryActivity;
import com.example.messageboard.ui.Admin.AdminOrderListActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class UserOrderListActivity extends AppCompatActivity {
    DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    private Query query;
    RecyclerView.LayoutManager layoutManager;
    private Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_list);

        backButton = findViewById(R.id.activity_user_order_list_back_btn);
        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserOrderListActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        query = FirebaseDatabase.getInstance().getReference().child("Orders").orderByChild("userPhoneNumber")
                .equalTo(Prevalent.currentOnlineUser.getPhone());

        FirebaseRecyclerOptions<Orders> options;

        options = new FirebaseRecyclerOptions.Builder<Orders>()
                .setQuery(query, Orders.class).build();

        FirebaseRecyclerAdapter<Orders, OrderViewHolder> adapter = new FirebaseRecyclerAdapter<Orders, OrderViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Orders model) {
                holder.txtProductDate.setText(model.getDate());
                holder.txtProductUserName.setText("?????? ????????????????????????: " + model.getUserName());
                holder.txtProductUserPhone.setText("?????????????? ????????????????????????: " + model.getUserPhoneNumber());
                holder.txtProductName.setText("???????????????? ????????????: " + model.getProductName());
                holder.txtUserPhone.setText("?????????????? ????????????????: "  + model.getProductPhoneNumber());
                holder.txtProductCount.setText("????????????????????: " + model.getCount() + " ????.");
                holder.txtProductPrice.setText("???????????????? ???????? :" + model.getPrice() + " ????????????");
            }

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_layout, parent, false);
                OrderViewHolder holder = new OrderViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}