package com.e.aplikasiku;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class Ecash extends AppCompatActivity {

    private Button Sepuluh, Tigapuluh, Limapuluh, Tujuhlima, Seratus, Seratuslima, Duaratus, Tigaratus, Empatratus, Bayar;
    private TextView Jumlah, Bayartopup;
    private ProgressBar Proses;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase userDatabase;
    private FirebaseUser user;
    String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecash);
        Proses = (ProgressBar) findViewById(R.id.proses);
        Proses.setVisibility(View.GONE);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        iduser = user.getUid();
        userDatabase = FirebaseDatabase.getInstance();
        databaseReference = userDatabase.getReference();


        Sepuluh = (Button) findViewById(R.id.sepuluh);
        Tigapuluh = (Button) findViewById(R.id.tigapuluh);
        Limapuluh = (Button) findViewById(R.id.limapuluh);
        Tujuhlima = (Button) findViewById(R.id.tujuhlima);
        Seratus = (Button) findViewById(R.id.seratus);
        Seratuslima = (Button) findViewById(R.id.seratuslima);
        Duaratus = (Button) findViewById(R.id.duaratus);
        Tigaratus = (Button) findViewById(R.id.tigaratus);
        Empatratus = (Button) findViewById(R.id.empatratus);
        Jumlah = (TextView) findViewById(R.id.jumlah);
        Bayartopup = (TextView) findViewById(R.id.bayartopup);
        Bayar = (Button) findViewById(R.id.bayar);

        Sepuluh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(iduser).child("chart").child("Jumlah").setValue("10.000");
                databaseReference.child(iduser).child("chart").child("Harga").setValue("10.000");

            }
        });

        Tigapuluh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(iduser).child("chart").child("Jumlah").setValue("30.000");
                databaseReference.child(iduser).child("chart").child("Harga").setValue("30.000");
            }
        });
        Limapuluh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(iduser).child("chart").child("Jumlah").setValue("50.000");
                databaseReference.child(iduser).child("chart").child("Harga").setValue("50.000");
            }
        });
        Tujuhlima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(iduser).child("chart").child("Jumlah").setValue("75.000");
                databaseReference.child(iduser).child("chart").child("Harga").setValue("75.000");
            }
        });
        Seratus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(iduser).child("chart").child("Jumlah").setValue("100.000");
                databaseReference.child(iduser).child("chart").child("Harga").setValue("100.000");
            }
        });
        Seratuslima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(iduser).child("chart").child("Jumlah").setValue("150.000");
                databaseReference.child(iduser).child("chart").child("Harga").setValue("150.000");
            }
        });
        Duaratus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(iduser).child("chart").child("Jumlah").setValue("200.000");
                databaseReference.child(iduser).child("chart").child("Harga").setValue("200.000");
            }
        });
        Tigaratus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(iduser).child("chart").child("Jumlah").setValue("300.000");
                databaseReference.child(iduser).child("chart").child("Harga").setValue("300.000");
            }
        });
        Empatratus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(iduser).child("chart").child("Jumlah").setValue("400.000");
                databaseReference.child(iduser).child("chart").child("Harga").setValue("400.000");
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Jumlahnya = dataSnapshot.child(iduser).child("chart").child("Jumlah").getValue(String.class);
                String Harganyaa = dataSnapshot.child(iduser).child("chart").child("Harga").getValue(String.class);
                Jumlah.setText(Jumlahnya);
                Bayartopup.setText(Harganyaa);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
