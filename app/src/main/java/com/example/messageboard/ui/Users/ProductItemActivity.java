package com.example.messageboard.ui.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messageboard.Model.Products;
import com.example.messageboard.Prevalent.Prevalent;
import com.example.messageboard.R;
import com.example.messageboard.ui.LoginActivity;
import com.example.messageboard.ui.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import io.paperdb.Paper;

public class ProductItemActivity extends AppCompatActivity {
    private TextView productNameTV, categoryTV, dateTV, descriptionTV, priceTV, cityTV, phoneNumberTV;
    private EditText countET;
    private Button backBtn, callBtn, makeOrderBtn;
    private ImageView productIV;
    private Products currentProduct;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_item);
        init();
        getExtras();


        categoryTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent categoryIntent = new Intent(ProductItemActivity.this, HomeActivity.class);
                categoryIntent.putExtra("categoryFilter", currentProduct.getCategory());
                startActivity(categoryIntent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(ProductItemActivity.this, HomeActivity.class);
                startActivity(backIntent);
            }
        });

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissionCheck = ContextCompat.checkSelfPermission(ProductItemActivity.this, Manifest.permission.CALL_PHONE);

                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            ProductItemActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 123
                    );
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel: " + phoneNumberTV.getText().toString()));
                    startActivity(intent);
                }
            }
        });

        makeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String count = countET.getText().toString();
                if (!TextUtils.isEmpty(count)) {
                    StoreOrderInformation();
                } else {
                    Toast.makeText(ProductItemActivity.this, "Вы не ввели количество", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void StoreOrderInformation() {
        loadingBar.setTitle("Сохранение заказа");
        loadingBar.setMessage("Пожалуйста, подождите");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd,MM,yyyy");
        String saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        String saveCurrentTime = currentTime.format(calendar.getTime());

        String date = saveCurrentDate + " " + saveCurrentTime;

        HashMap<String, Object> orderDataMap = new HashMap<>();
        orderDataMap.put("date", date);
        orderDataMap.put("userName", Prevalent.currentOnlineUser.getName());
        orderDataMap.put("userPhoneNumber", Prevalent.currentOnlineUser.getPhone());
        orderDataMap.put("productName", currentProduct.getPname());
        orderDataMap.put("count", countET.getText().toString());
        orderDataMap.put("productPhoneNumber", currentProduct.getPhone());
        orderDataMap.put("price", Integer.toString(Integer.parseInt(currentProduct.getPrice()) * Integer.parseInt(countET.getText().toString())));


        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    rootRef.child("Orders").child(date).updateChildren(orderDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            loadingBar.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(ProductItemActivity.this, "Заказ создан", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(ProductItemActivity.this, "Что-то пошло не так", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void init() {
        loadingBar = new ProgressDialog(this);
        countET = findViewById(R.id.activity_product_count);
        makeOrderBtn = findViewById(R.id.activity_product_count_btn);
        productNameTV = findViewById(R.id.activity_product_name);
        categoryTV = findViewById(R.id.activity_product_category);
        dateTV = findViewById(R.id.activity_product_date);
        descriptionTV = findViewById(R.id.activity_product_description);
        priceTV = findViewById(R.id.activity_product_price);
        cityTV = findViewById(R.id.activity_product_city);
        phoneNumberTV = findViewById(R.id.activity_product_number);

        backBtn = findViewById(R.id.activity_product_back_button);
        callBtn = findViewById(R.id.activity_product_call_button);

        productIV = findViewById(R.id.activity_product_image);
    }

    private void getExtras() {
        currentProduct = (Products) getIntent().getSerializableExtra("product_info");
        productNameTV.setText(currentProduct.getPname());
        categoryTV.setText(getCategoryName(currentProduct.getCategory()));
        dateTV.setText(currentProduct.getDate());
        Picasso.get().load(currentProduct.getImage()).into(productIV);
        descriptionTV.setText("Описание: " + currentProduct.getDescription());
        priceTV.setText("Цена: " + currentProduct.getPrice() + " рублей");
        cityTV.setText("Город: " + currentProduct.getCity());
        phoneNumberTV.setText(currentProduct.getPhone());
    }

    public String getCategoryName(String enCategory){
        String ruCategoryName;
        if (enCategory.equals("car")) { ruCategoryName = "Машины"; } else
        if (enCategory.equals("moto")) { ruCategoryName = "Мотоциклы"; } else
        if (enCategory.equals("service")) { ruCategoryName = "Услуги"; } else
        if (enCategory.equals("clothes")) { ruCategoryName = "Одежда"; } else
        if (enCategory.equals("shoes")) { ruCategoryName = "Обувь"; } else
        if (enCategory.equals("phone")) { ruCategoryName = "Телефоны"; } else
        if (enCategory.equals("pc")) { ruCategoryName = "ПК"; } else
        if (enCategory.equals("photo")) { ruCategoryName = "Камеры"; } else
        if (enCategory.equals("bicycle")) { ruCategoryName = "Велосипеды"; } else
        if (enCategory.equals("books")) { ruCategoryName = "Книги"; } else
        if (enCategory.equals("collecting")) { ruCategoryName = "Коллеционирование"; } else
        if (enCategory.equals("music")) { ruCategoryName = "Музыка"; } else
            ruCategoryName = "";
        return ruCategoryName;
    }
}