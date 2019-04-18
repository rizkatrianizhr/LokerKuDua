package com.e.aplikasiku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Ukuranlocker extends AppCompatActivity {


    private Button Small, Medium, Large;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase userDatabase;
    private FirebaseUser user;
    String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        userDatabase = FirebaseDatabase.getInstance();
        databaseReference = userDatabase.getReference();
        setContentView(R.layout.activity_ukuranlocker);

        Small = (Button) findViewById(R.id.btnSmall);
        Medium = (Button) findViewById(R.id.btnMedium);
        Large = (Button) findViewById(R.id.btnLarge);

        Small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null);
                startActivity(new Intent(Ukuranlocker.this, Scan.class));

                if (auth.getCurrentUser() != null);
                FirebaseUser user = auth.getCurrentUser();
                iduser = user.getUid();
                databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Small");
                databaseReference.child(iduser).child("chart").child("Harga").setValue(40000);
                startActivity(new Intent(Ukuranlocker.this, Scan.class));
            }
        });
        Medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null);
                startActivity(new Intent(Ukuranlocker.this, Scan.class));

                if (auth.getCurrentUser() != null);
                FirebaseUser user = auth.getCurrentUser();
                iduser = user.getUid();
                databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Medium");
                databaseReference.child(iduser).child("chart").child("Harga").setValue(50000);
                startActivity(new Intent(Ukuranlocker.this, Scan.class));
            }
        });

        Large.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null);
                startActivity(new Intent(Ukuranlocker.this, Scan.class));

                if (auth.getCurrentUser() != null);
                FirebaseUser user = auth.getCurrentUser();
                iduser = user.getUid();
                databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Large");
                databaseReference.child(iduser).child("chart").child("Harga").setValue(60000);
                startActivity(new Intent(Ukuranlocker.this, Scan.class));
            }
        });
    }
}
