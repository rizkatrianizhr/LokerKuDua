package com.e.aplikasiku;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.InputStream;

public class addsaldo extends AppCompatActivity {

    private TextView Balance;
    private EditText Topup;
    private Button Bayar;
    private ProgressBar Proses;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase userDatabase;
    private FirebaseUser user;
    String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsaldo);

        Proses = (ProgressBar) findViewById(R.id.proses);
        Proses.setVisibility(View.GONE);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        iduser = user.getUid();
        userDatabase = FirebaseDatabase.getInstance();
        databaseReference = userDatabase.getReference();

        Balance = (TextView) findViewById(R.id.balance);
        Bayar = (Button) findViewById(R.id.bayar);
        Topup = (EditText) findViewById(R.id.topup);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String balance = dataSnapshot.child("users").child(iduser).child("balance").getValue(String.class);
                Balance.setText(balance);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Proses.setVisibility(View.VISIBLE);
                final String jumlah = Topup.getText().toString().trim();

                if (TextUtils.isEmpty(jumlah)) {
                    Bayar.setClickable(false);
                    Proses.setVisibility(View.GONE);
                    return;
                }

                if (!TextUtils.isEmpty(jumlah)) {
                    databaseReference.child("users")
                            .child(auth.getCurrentUser().getUid())
                            .child("balanceSuspend").setValue(jumlah);
                }

                startActivity(new Intent(addsaldo.this, Bank.class));
                Proses.setVisibility(View.GONE);

            }
        });

    }
}
