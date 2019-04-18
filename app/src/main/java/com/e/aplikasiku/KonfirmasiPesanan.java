package com.e.aplikasiku;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class KonfirmasiPesanan extends AppCompatActivity {

    private TextView location, size, hours, total;
    private Button order;
    private ProgressBar loading;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    String idnya, ukurannya, durasinya;
    private Integer harganya, saldonya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.konfirm_pesanan);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        idnya = user.getUid();
        loading = (ProgressBar) findViewById(R.id.proses);
        loading.setVisibility(View.GONE);
        location = (TextView) findViewById(R.id.lokasi);
        size = (TextView) findViewById(R.id.ukuran);
        hours = (TextView) findViewById(R.id.waktu);
        total = (TextView) findViewById(R.id.total);
        order = (Button) findViewById(R.id.pesan);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ukurannya = dataSnapshot.child(idnya).child("chart").child("Ukuran").getValue(String.class);
                durasinya = dataSnapshot.child(idnya).child("chart").child("Durasi").getValue(String.class);
                harganya = dataSnapshot.child(idnya).child("chart").child("Harga").getValue(Integer.class);
                saldonya = dataSnapshot.child(idnya).child("saldo").getValue(Integer.class);
                size.setText(ukurannya);
                hours.setText(durasinya);
                total.setText(Integer.toString(harganya));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( saldonya < harganya) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(KonfirmasiPesanan.this);
                        builder.setMessage("Maaf saldo anda tidak mencukupi " + "Apakah anda ingin menambah saldo?");
                            builder.setNeutralButton("Tidak", null);
                            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(KonfirmasiPesanan.this, TopUp.class));
                                }
                            });
                            builder.create().show();
                            loading.setVisibility(View.GONE);

                } else {
                    databaseReference.child(idnya).child("saldo").setValue(saldonya - harganya);
                    Toast.makeText(KonfirmasiPesanan.this, "Pesanan berhasil dibuat", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(KonfirmasiPesanan.this, Pesanan.class));
                }

            }

        });



    }
}


