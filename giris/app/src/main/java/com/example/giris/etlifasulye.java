package com.example.giris;//kodun hangi projeye ait olduğunu gösteririr

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class etlifasulye extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { //ekran oluşturulurken çağrılır
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);//tam ekran
        setContentView(R.layout.activity_etlifasulye);//Kullanıcı arayüzü bileşenlerini belirten XML dosyasını bu Activity'ye bağlar.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}