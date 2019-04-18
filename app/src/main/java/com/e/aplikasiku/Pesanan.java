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

        Size = (TextView) findViewById(R.id.ukuran);
        no = (TextView) findViewById(R.id.noLocker);
        jam = (TextView) findViewById(R.id.waktu);
        Scan = (Button) findViewById(R.id.scan);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                waktunya = dataSnapshot.child(idnya).child("chart").child("SisaWaktu").getValue(String.class);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
                String currentDateandTime = sdf.format(new Date());
                String deadlineDateandTime = sdf.format(waktunya);

                Date dateAwal = null;
                Date dateAkhir = null;
                try {
                    dateAwal = sdf.parse(currentDateandTime);
                    Log.d("DEBUG", dateAwal.toString());
                    dateAkhir = sdf.parse(deadlineDateandTime);
                    Log.d("DEBUG", dateAkhir.toString());
                    long diff = dateAkhir.getTime() - dateAwal.getTime();

                    Log.d("DEBUG", diff+"");

                    jam.setText("" + (diff / 1000 / 60));
//                    new CountDownTimer(diff, 1000) {
//
//                        public void onTick(long millisUntilFinished) {
//                            jam.setText("" + millisUntilFinished / 1000);
//                        }
//
//                        public void onFinish() {
//                            jam.setText("Durasi habis!!");
//                        }
//                    }.start();

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Pesanan.this, Scan.class));
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Pesanan.this, MainActivity.class));
    }
}