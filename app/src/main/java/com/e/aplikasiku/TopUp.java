package com.e.aplikasiku;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TopUp extends AppCompatActivity {

    private Button Atms, Tambah;
    private ProgressBar Loading;
    private EditText Inputsaldo;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase userDatabase;
    private FirebaseUser user;
    String iduser;
    Integer saldonya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topup);
        Loading = (ProgressBar) findViewById(R.id.loading);
        Loading.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        iduser = user.getUid();
        userDatabase = FirebaseDatabase.getInstance();
        databaseReference = userDatabase.getReference();
        Atms = (Button) findViewById(R.id.atm);
        Inputsaldo = (EditText) findViewById(R.id.inputsaldo);
        Tambah = (Button) findViewById(R.id.tambah);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                saldonya = dataSnapshot.child(iduser).child("saldo").getValue(Integer.class);
                Log.d(" saldo ", "topup saldo fbase : " + saldonya);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        Atms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TopUp.this, Atm.class));
            }
        });

        Tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String saldoInput = Inputsaldo.getText().toString().trim();

                Log.d(" saldo tambah", "topup saldo input : " + saldoInput);
                Log.d(" saldo ", "topup saldo : " + saldonya);

                Loading.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(saldoInput)) {
                    Toast.makeText(getApplicationContext(), "Masukan Nominal", Toast.LENGTH_SHORT).show();
                }
                if (!TextUtils.isEmpty(saldoInput)) {
                    databaseReference.child("users").child(iduser).child("balance").setValue(Integer.parseInt(saldoInput) + saldonya);
                    Toast.makeText(TopUp.this, "Pengisian Saldo Berhasil", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TopUp.this, ProfilActivity.class));
                }




            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TopUp.this, ProfilActivity.class));
    }
}