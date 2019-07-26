package com.e.aplikasiku;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Bank extends AppCompatActivity {

    private Button Back, Bca, Bri, Mandiri, Bni, Upload;
    private CardView cvBCA, cvBRI, cvMandiri, cvBNI;
    private TextView Total;

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase userDatabase;
    private FirebaseUser user;
    String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        iduser = user.getUid();
        userDatabase = FirebaseDatabase.getInstance();
        databaseReference = userDatabase.getReference();

        Total = (TextView) findViewById(R.id.totall);
        Back = (Button) findViewById(R.id.btnBack);
        Bca = (Button) findViewById(R.id.bca);
        Bri = (Button)  findViewById(R.id.bri);
        Upload = (Button) findViewById(R.id.upload);
        Mandiri = (Button) findViewById(R.id.mandiri);
        Bni = ( Button) findViewById(R.id.bni);
        cvBCA = (CardView) findViewById(R.id.cvbca);
        cvBNI = (CardView) findViewById(R.id.cvbni);
        cvMandiri = (CardView) findViewById(R.id.cvmandiri);
        cvBRI = (CardView) findViewById(R.id.cvbri);

        cvBRI.setVisibility(View.GONE);
        cvMandiri.setVisibility(View.GONE);
        cvBNI.setVisibility(View.GONE);
        cvBCA.setVisibility(View.GONE);
        Bca.setVisibility(View.VISIBLE);
        Bri.setVisibility(View.VISIBLE);
        Mandiri.setVisibility(View.VISIBLE);
        Bni.setVisibility(View.VISIBLE);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databaseReference.child("users").child(auth.getCurrentUser().getUid()).child("isVerified").setValue(false);
                String jumlah = dataSnapshot.child("users").child(auth.getCurrentUser().getUid()).child("balanceSuspend").getValue(String.class);
                Total.setText(jumlah);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Bca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvBCA.setVisibility(View.VISIBLE);
                cvBRI.setVisibility(View.GONE);
                cvBNI.setVisibility(View.GONE);
                cvMandiri.setVisibility(View.GONE);

            }
        });
        Bri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvBRI.setVisibility(View.VISIBLE);
                cvBCA.setVisibility(View.GONE);
                cvMandiri.setVisibility(View.GONE);
                cvBNI.setVisibility(View.GONE);
            }
        });
        Mandiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvMandiri.setVisibility(View.VISIBLE);
                cvBCA.setVisibility(View.GONE);
                cvBNI.setVisibility(View.GONE);
                cvBRI.setVisibility(View.GONE);

            }
        });
        Bni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvBNI.setVisibility(View.VISIBLE);
                cvBCA.setVisibility(View.GONE);
                cvBRI.setVisibility(View.GONE);
                cvMandiri.setVisibility(View.GONE);
            }
        });
        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Bank.this, Uploadbukti.class));
            }
        });

       Back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(Bank.this, LamanUtama.class));
           }
       });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Bank.this, LamanUtama.class));
    }
}
