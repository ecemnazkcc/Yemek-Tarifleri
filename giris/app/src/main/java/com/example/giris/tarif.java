package com.example.giris;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class tarif extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarif);

    }
    // Taze Fasulye Butonuna Tıklanma Olayı
    public void fasulye(View view) {
        Intent intent = new Intent(tarif.this, fasulye.class);
        intent.putExtra("foodName", "Taze Fasulye");
        startActivity(intent);
    }

    // Nohut Butonuna Tıklanma Olayı
    public void nohut(View view) {
        Intent intent = new Intent(tarif.this, nohut.class);
        intent.putExtra("foodName", "Nohut");//veri ekleme
        startActivity(intent);
    }

    // Pilav Butonuna Tıklanma Olayı
    public void pilav(View view) {
        Intent intent = new Intent(tarif.this, pilav.class);
        intent.putExtra("foodName", "Pilav");
        startActivity(intent);
    }

    // Etli Kuru Fasulye Butonuna Tıklanma Olayı
    public void etlifasulye(View view) {
        Intent intent = new Intent(tarif.this, etlifasulye.class);
        intent.putExtra("foodName", "Etli Kuru Fasulye");
        startActivity(intent);
    }
}