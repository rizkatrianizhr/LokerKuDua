package com.e.aplikasiku;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.e.aplikasiku.models.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Set;


public class LamanUtama extends AppCompatActivity {

    private ImageView Notif;
    private TextView WelcomeLogin, Welcome, Names, Balance, Text, WelcomeBack, Text1, Text3, Text4;
    private CardView NotLogin, cvSaldo;
    private Button Login, Regis, Order, Rent, Profile, Help, Settings, TopUp;

    private boolean isOrdering;

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String idusernya;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laman_utama);


        auth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        TopUp = (Button) findViewById(R.id.topup);
        Settings = (Button) findViewById(R.id.settings);
        Profile = (Button) findViewById(R.id.btnProfile);
        Order = (Button) findViewById(R.id.btnOrder);
        Login = (Button) findViewById(R.id.btn_login);
        Rent = (Button) findViewById(R.id.btnRent);
        Help = (Button) findViewById(R.id.btnHelp);
        Regis = (Button) findViewById(R.id.btnRegis);
        Text = (TextView) findViewById(R.id.text1);
        Text1 = (TextView) findViewById(R.id.text2);
        Text3 = (TextView) findViewById(R.id.text3);
        Text4 = (TextView) findViewById(R.id.text4);
        Welcome = (TextView) findViewById(R.id.welcome);
        WelcomeBack = (TextView) findViewById(R.id.welcomeback);
        Names = (TextView) findViewById(R.id.names);
        Balance = (TextView) findViewById(R.id.balance);
        cvSaldo = (CardView) findViewById(R.id.cv_saldo);
        NotLogin = (CardView) findViewById(R.id.notLogin);


        if (auth.getCurrentUser() == null) {
            Names.setVisibility(View.GONE);
            cvSaldo.setVisibility(View.GONE);
            WelcomeBack.setVisibility(View.GONE);
            Welcome.setVisibility(View.VISIBLE);
            NotLogin.setVisibility(View.VISIBLE);
            Text.setVisibility(View.GONE);
            Text1.setVisibility(View.GONE);
            Text3.setVisibility(View.VISIBLE);
            Text4.setVisibility(View.VISIBLE);
        }

        if (auth.getCurrentUser() != null && !auth.getCurrentUser().isEmailVerified()) {
            auth.signOut();
            Names.setVisibility(View.GONE);
            cvSaldo.setVisibility(View.GONE);
            WelcomeBack.setVisibility(View.GONE);
            Welcome.setVisibility(View.VISIBLE);
            NotLogin.setVisibility(View.VISIBLE);
            Text.setVisibility(View.GONE);
            Text1.setVisibility(View.GONE);
            Text3.setVisibility(View.VISIBLE);
            Text4.setVisibility(View.VISIBLE);
            Toast.makeText(LamanUtama.this, "Email belum terverifikasi", Toast.LENGTH_LONG).show();
        }


        if (auth.getCurrentUser() != null ) {
            final FirebaseUser user = auth.getCurrentUser();
            idusernya = user.getUid();

            Welcome.setVisibility(View.GONE);
            Text.setVisibility(View.VISIBLE);
            Text1.setVisibility(View.VISIBLE);
            NotLogin.setVisibility(View.GONE);
            Names.setVisibility(View.VISIBLE);
            WelcomeBack.setVisibility(View.VISIBLE);
            cvSaldo.setVisibility(View.VISIBLE);
            Text3.setVisibility(View.GONE);
            Text4.setVisibility(View.GONE);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("users").child(idusernya).child("name").getValue(String.class);
                    String dompet = dataSnapshot.child("users").child(idusernya).child("balance").getValue(String.class);

                    Names.setText(name);
                    Balance.setText(dompet);

                    Order pesanan = dataSnapshot.child("users").child(idusernya).child("order").getValue(Order.class);
                    if (pesanan == null){
                        isOrdering = false;
                    }else {
                        isOrdering = true;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        Rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOrdering){
                    AlertDialog.Builder builder = new AlertDialog.Builder(LamanUtama.this);
                    builder.setTitle("Ups Sorry!");
                    builder.setMessage("You already ordered a locker");
                    builder.setPositiveButton("Ok!", null);
                    builder.create().show();
                }else {
                    startActivity(new Intent(LamanUtama.this, rentLocker.class));
                }
            }
        });

        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    startActivity(new Intent(LamanUtama.this, Login.class));

                }
                if (auth.getCurrentUser() != null  ) {
                    //apakah sudah pesan
                    if(isOrdering){
                        startActivity(new Intent(LamanUtama.this, Pesanan.class));
                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LamanUtama.this);
                        builder.setMessage("You don't have any ordered yet");
                        builder.setPositiveButton("Ok!", null);
                        builder.create().show();
                    }

                }
            }


//                    }else{
//                    AlertDialog.Builder builder = new AlertDialog.Builder(LamanUtama.this);
//                    builder.setMessage("Silahkan pesan");
//                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                        }
        });

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    startActivity(new Intent(LamanUtama.this, com.e.aplikasiku.Login.class));
                }
                if (auth.getCurrentUser() != null) {
                    startActivity(new Intent(LamanUtama.this, ProfilActivity.class));
                }
            }
        });

        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (auth.getCurrentUser() == null) {
                    startActivity(new Intent(LamanUtama.this, com.e.aplikasiku.Login.class));
                }
                if (auth.getCurrentUser() != null) {
                    startActivity(new Intent(LamanUtama.this, helpCenter.class));
                }
            }
        });

        Regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LamanUtama.this, Register.class));
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LamanUtama.this, com.e.aplikasiku.Login.class));
            }
        });

        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    startActivity(new Intent(LamanUtama.this, Login.class));
                }
                if (auth.getCurrentUser() != null) {
                    startActivity(new Intent(LamanUtama.this, com.e.aplikasiku.Settings.class));
                }
            }
        });

        TopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    startActivity(new Intent(LamanUtama.this, Login.class));
                }
                if (auth.getCurrentUser() != null) {
                    startActivity(new Intent(LamanUtama.this, addsaldo.class));
                }

            }
        });

//
    }
}


