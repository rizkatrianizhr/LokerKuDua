package com.e.aplikasiku;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Delayed;

public class Pesanan extends AppCompatActivity {

    private Button Scan, Lanjut, Selesai;
    private TextView Size, no, jam;



    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;

    String idnya;
    String waktunya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesanan);

        Log.d("DEBUG", "Pesanan Activity");

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        idnya = user.getUid();

    }
}