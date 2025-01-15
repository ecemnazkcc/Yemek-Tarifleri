package com.example.giris;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {
    EditText etUsername, etPassword;
    Button btnLogin, btnRegister;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);//hangi xmli yükliyeceğimizi gösterir
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        etUsername = findViewById(R.id.kAdi);
        etPassword = findViewById(R.id.sifre);
        btnLogin = findViewById(R.id.giris);
        btnRegister = findViewById(R.id.kayit);

        dbHelper = new DatabaseHelper(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();//tanımlama
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Kullanıcı adı ve şifre giriniz", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (username.length() < 6 || password.length() < 6) {
                    Toast.makeText(MainActivity.this, "Kullanıcı adı ve şifre 6 karakter olmalıdır", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isInserted = dbHelper.insertUser(username, password);
                if (isInserted) {
                    Toast.makeText(MainActivity.this, "Kayıt başarılı", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Kullanıcı adı zaten var", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Kullanıcı adı ve şifre giriniz", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isAuthenticated = dbHelper.checkUser(username, password);
                if (isAuthenticated) {//giriş yapıldıysa tarif kısmına gitsin
                    Toast.makeText(MainActivity.this, "Giriş başarılı", Toast.LENGTH_SHORT).show();
                    // Geçiş yapılacak yeni bir sayfa (boş bir ekran) burada başlatılabilir
                    startActivity(new Intent(MainActivity.this, tarif.class));
                    finish();

                }
                else {
                    Toast.makeText(MainActivity.this, "Hatalı kullanıcı adı veya şifre", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class DatabaseHelper extends SQLiteOpenHelper {//veritabanı işlemleri

        private static final String DATABASE_NAME = "UserDB.db";
        private static final String TABLE_NAME = "users";
        private static final String COL_1 = "id";
        private static final String COL_2 = "username";
        private static final String COL_3 = "password";

        public DatabaseHelper(MainActivity context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {//veritabanı oluşturma
            String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_2 + " TEXT UNIQUE, " +
                    COL_3 + " TEXT)";
            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public boolean insertUser(String username, String password) {//veritabanına veri ekleme
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2, username);
            contentValues.put(COL_3, password);

            long result = db.insert(TABLE_NAME, null, contentValues);
            return result != -1;
        }

        public boolean checkUser(String username, String password) {//girilen bilgileri doğrulama
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                    COL_2 + "=? AND " + COL_3 + "=?", new String[]{username, password});
            boolean exists = cursor.getCount() > 0;
            cursor.close();
            return exists;
        }
    }
}