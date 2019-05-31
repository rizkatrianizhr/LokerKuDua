package com.e.aplikasiku;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.EnumMap;

public class Ecash extends AppCompatActivity {

    private CardView Sepuluh, Duapuluh, Tigapuluh, Empatpuluh, Limapuluh, Tujuhlima, Seratus, Duaratus, Tigaratus;
    private Button Bayar, Back;
    private EditText Input;
    private TextView Total;
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


        Sepuluh = (CardView) findViewById(R.id.sepuluh);
        Duapuluh= (CardView) findViewById(R.id.duapuluh);
        Tigapuluh = (CardView) findViewById(R.id.tigapuluh);
        Empatpuluh = (CardView) findViewById(R.id.empatpuluh);
        Limapuluh = (CardView) findViewById(R.id.limapuluh);
        Tujuhlima = (CardView) findViewById(R.id.tujuhlima);
        Seratus = (CardView) findViewById(R.id.seratus);
        Duaratus = (CardView) findViewById(R.id.duaratus);
        Tigaratus = (CardView) findViewById(R.id.tigaratus);
        Total = (TextView) findViewById(R.id.total);
        Bayar = (Button) findViewById(R.id.bayar);
        Input = (EditText) findViewById(R.id.input);
        Back = (Button) findViewById(R.id.btnBack);

        Sepuluh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sepuluh.setCardBackgroundColor(getResources().getColor(R.color.abu));
                Duapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tigapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Empatpuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Limapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tujuhlima.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Seratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Duaratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tigaratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));

                databaseReference.child("Topup").child(iduser).child("jumlah").setValue("10000");

//                Intent i = getIntent();
//                i.putExtra("topup", "10000");

            }
        });

        Duapuluh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Duapuluh.setCardBackgroundColor(getResources().getColor(R.color.abu));
                Sepuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tigapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Empatpuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Limapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tujuhlima.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Seratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Duaratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tigaratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));

                databaseReference.child("Topup").child(iduser).child("jumlah").setValue("20000");



//                Intent i = getIntent();
//                i.putExtra("topup", "20000");

            }
        });
        Tigapuluh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tigapuluh.setCardBackgroundColor(getResources().getColor(R.color.abu));
                Sepuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Duapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Empatpuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Limapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tujuhlima.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Seratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Duaratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tigaratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));

                databaseReference.child("Topup").child(iduser).child("jumlah").setValue("30000");

//                Intent i = getIntent();
//                i.putExtra("topup", "30000");

            }
        });

        Empatpuluh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Empatpuluh.setCardBackgroundColor(getResources().getColor(R.color.abu));
                Sepuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Duapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tigapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Limapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tujuhlima.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Seratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Duaratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tigaratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));

                databaseReference.child("Topup").child(iduser).child("jumlah").setValue("40000");

//                Intent i = getIntent();
//                i.putExtra("topup", "40000");
            }
        });
        Limapuluh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Limapuluh.setCardBackgroundColor(getResources().getColor(R.color.abu));
                Sepuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Duapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tigapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Empatpuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tujuhlima.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Seratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Duaratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tigaratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));

                databaseReference.child("Topup").child(iduser).child("jumlah").setValue("50000");

//                Intent i = getIntent();
//                i.putExtra("topup", "50000");
            }
        });

        Tujuhlima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tujuhlima.setCardBackgroundColor(getResources().getColor(R.color.abu));
                Sepuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Duapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tigapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Empatpuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Limapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Seratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Duaratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tigaratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));

                databaseReference.child("Topup").child(iduser).child("jumlah").setValue("75000");
            }
        });
        Seratus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seratus.setCardBackgroundColor(getResources().getColor(R.color.abu));
                Sepuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Duapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tigapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Empatpuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Limapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tujuhlima.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Duaratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tigaratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));

                databaseReference.child("Topup").child(iduser).child("jumlah").setValue("100000");

//                Intent i = getIntent();
//                i.putExtra("topup", "75000");
            }
        });
        Duaratus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Duaratus.setCardBackgroundColor(getResources().getColor(R.color.abu));
                Sepuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Duapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tigapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Empatpuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Limapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tujuhlima.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Seratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tigaratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));

                databaseReference.child("Topup").child(iduser).child("jumlah").setValue("200000");

//                Intent i = getIntent();
//                i.putExtra("topup", "200000");
            }
        });
        Tigaratus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tigaratus.setCardBackgroundColor(getResources().getColor(R.color.abu));
                Sepuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Duapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tigapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Empatpuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Limapuluh.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Tujuhlima.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Seratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));
                Duaratus.setCardBackgroundColor(getResources().getColor(R.color.bluedark));

                databaseReference.child("Topup").child(iduser).child("jumlah").setValue("300000");

//                Intent i = getIntent();
//                i.putExtra("topup", "300000");

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String jumlah = dataSnapshot.child("Topup").child(iduser).child("jumlah").getValue(String.class);
                Input.setText(jumlah);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ecash.this, LamanUtama.class));
            }
        });

        Bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Ecash.this, Pilihanbank.class));
            }
        });





//        Sepuluh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                databaseReference.child(iduser).child("chart").child("Jumlah").setValue("10.000");
//                databaseReference.child(iduser).child("chart").child("Harga").setValue("10.000");
//
//            }
//        });
//
//        Tigapuluh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                databaseReference.child(iduser).child("chart").child("Jumlah").setValue("30.000");
//                databaseReference.child(iduser).child("chart").child("Harga").setValue("30.000");
//            }
//        });
//        Limapuluh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                databaseReference.child(iduser).child("chart").child("Jumlah").setValue("50.000");
//                databaseReference.child(iduser).child("chart").child("Harga").setValue("50.000");
//            }
//        });
//        Tujuhlima.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                databaseReference.child(iduser).child("chart").child("Jumlah").setValue("75.000");
//                databaseReference.child(iduser).child("chart").child("Harga").setValue("75.000");
//            }
//        });
//        Seratus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                databaseReference.child(iduser).child("chart").child("Jumlah").setValue("100.000");
//                databaseReference.child(iduser).child("chart").child("Harga").setValue("100.000");
//            }
//        });
//        Seratuslima.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                databaseReference.child(iduser).child("chart").child("Jumlah").setValue("150.000");
//                databaseReference.child(iduser).child("chart").child("Harga").setValue("150.000");
//            }
//        });
//        Duaratus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                databaseReference.child(iduser).child("chart").child("Jumlah").setValue("200.000");
//                databaseReference.child(iduser).child("chart").child("Harga").setValue("200.000");
//            }
//        });
//        Tigaratus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                databaseReference.child(iduser).child("chart").child("Jumlah").setValue("300.000");
//                databaseReference.child(iduser).child("chart").child("Harga").setValue("300.000");
//            }
//        });
//        Empatratus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                databaseReference.child(iduser).child("chart").child("Jumlah").setValue("400.000");
//                databaseReference.child(iduser).child("chart").child("Harga").setValue("400.000");
//            }
//        });


    }
}
