package com.example.messageboard.ui.Users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.messageboard.R;
import com.example.messageboard.ui.Admin.AdminAddNewProductActivity;
import com.example.messageboard.ui.Admin.AdminCategoryActivity;

public class UserCategoryActivity extends AppCompatActivity {
    private ImageView car, moto, service, clothes;
    private ImageView shoes, phone, pc, photo;
    private ImageView bicycle, books, collecting, music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_category);

        init();
        setOnClickListeners();
    }

    private void init() {
        car = findViewById(R.id.car);
        moto = findViewById(R.id.moto);
        service = findViewById(R.id.service);
        clothes = findViewById(R.id.clothes);

        shoes = findViewById(R.id.shoes);
        phone = findViewById(R.id.phone);
        pc = findViewById(R.id.pc);
        photo = findViewById(R.id.photo);

        bicycle = findViewById(R.id.bicycle);
        books = findViewById(R.id.books);
        collecting = findViewById(R.id.collecting);
        music = findViewById(R.id.music);
    }

    private void setOnClickListeners() {
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCategoryActivity.this, HomeActivity.class);
                intent.putExtra("categoryFilter", "car");
                startActivity(intent);
            }
        });

        moto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCategoryActivity.this, HomeActivity.class);
                intent.putExtra("categoryFilter", "moto");
                startActivity(intent);
            }
        });

        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCategoryActivity.this, HomeActivity.class);
                intent.putExtra("categoryFilter", "service");
                startActivity(intent);
            }
        });

        clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCategoryActivity.this, HomeActivity.class);
                intent.putExtra("categoryFilter", "clothes");
                startActivity(intent);
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCategoryActivity.this, HomeActivity.class);
                intent.putExtra("categoryFilter", "shoes");
                startActivity(intent);
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCategoryActivity.this, HomeActivity.class);
                intent.putExtra("categoryFilter", "phone");
                startActivity(intent);
            }
        });

        pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCategoryActivity.this, HomeActivity.class);
                intent.putExtra("categoryFilter", "pc");
                startActivity(intent);
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCategoryActivity.this, HomeActivity.class);
                intent.putExtra("categoryFilter", "photo");
                startActivity(intent);
            }
        });

        bicycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCategoryActivity.this, HomeActivity.class);
                intent.putExtra("categoryFilter", "bicycle");
                startActivity(intent);
            }
        });

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCategoryActivity.this, HomeActivity.class);
                intent.putExtra("categoryFilter", "books");
                startActivity(intent);
            }
        });

        collecting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCategoryActivity.this, HomeActivity.class);
                intent.putExtra("categoryFilter", "collecting");
                startActivity(intent);
            }
        });

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCategoryActivity.this, HomeActivity.class);
                intent.putExtra("categoryFilter", "music");
                startActivity(intent);
            }
        });
    }
}