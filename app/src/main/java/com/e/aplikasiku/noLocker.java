package com.e.aplikasiku;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.e.aplikasiku.models.Locker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class noLocker extends AppCompatActivity {

    private CardView S1, S2, S3,S4,S5, M1, M2, M3, M4, M5, L1, L2, L3, L4, L5;
    private ProgressBar loading;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String idusernya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_locker);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        final FirebaseUser user = firebaseAuth.getCurrentUser();
        idusernya = user.getUid();

        S1 = (CardView) findViewById(R.id.s1);
        S2 = (CardView) findViewById(R.id.s2);
        S3 = (CardView) findViewById(R.id.s3);
        S4 = (CardView) findViewById(R.id.s4);
        S5 = (CardView) findViewById(R.id.s5);

        M1 = (CardView) findViewById(R.id.m1);
        M2 = (CardView) findViewById(R.id.m2);
        M3 = (CardView) findViewById(R.id.m3);
        M4 = (CardView) findViewById(R.id.m4);
        M5 = (CardView) findViewById(R.id.m5);

        L1 = (CardView) findViewById(R.id.l1);
        L2 = (CardView) findViewById(R.id.l2);
        L3 = (CardView) findViewById(R.id.l3);
        L4 = (CardView) findViewById(R.id.l4);
        L5 = (CardView) findViewById(R.id.l5);

        // TODO HINT: Mengambil data yang dikirimkan dari rentLockerActivity
        final String size = getIntent().getExtras().getString("locker");
        Toast.makeText(this, "Size: "+ size, Toast.LENGTH_SHORT).show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // TODO HINT: Penambahan satu kondisi pada "IF" Untuk men-disable onClick saat UKURAN tidak sesuai
                if(dataSnapshot.child("lockers").child("small-1").child("isOccupied").getValue(Integer.class)==1
                        || !size.equals("small")){
                        S1.setCardBackgroundColor(getResources().getColor(R.color.abu));
                        // TODO HINT: Untuk men-disable onClick saat locker sudah ter-occupied
                        S1.setClickable(false);

                }

                if(dataSnapshot.child("lockers").child("small-2").child("isOccupied").getValue(Integer.class)==1
                        || !size.equals("small")){
                    S2.setCardBackgroundColor(getResources().getColor(R.color.abu));
                    S2.setClickable(false);
                }
                if(dataSnapshot.child("lockers").child("small-3").child("isOccupied").getValue(Integer.class)==1
                        || !size.equals("small")) {
                    S3.setCardBackgroundColor(getResources().getColor(R.color.abu));
                    S3.setClickable(false);

                }
                if(dataSnapshot.child("lockers").child("small-4").child("isOccupied").getValue(Integer.class)==1
                        || !size.equals("small")){
                    S4.setCardBackgroundColor(getResources().getColor(R.color.abu));
                    S4.setClickable(false);
                }
                if(dataSnapshot.child("lockers").child("small-5").child("isOccupied").getValue(Integer.class)==1
                        || !size.equals("small")) {
                    S5.setCardBackgroundColor(getResources().getColor(R.color.abu));
                    S5.setClickable(false);
                }

                if(dataSnapshot.child("lockers").child("medium-1").child("isOccupied").getValue(Integer.class)==1
                        || !size.equals("medium")){
                    M1.setCardBackgroundColor(getResources().getColor(R.color.abu));
                    M1.setClickable(false);
                }
                if(dataSnapshot.child("lockers").child("medium-2").child("isOccupied").getValue(Integer.class)==1
                        || !size.equals("medium")){
                    M2.setCardBackgroundColor(getResources().getColor(R.color.abu));
                    M2.setClickable(false);
                }
                if(dataSnapshot.child("lockers").child("medium-3").child("isOccupied").getValue(Integer.class)==1
                        || !size.equals("medium")){
                    M3.setCardBackgroundColor(getResources().getColor(R.color.abu));
                    M3.setClickable(false);
                }
                if(dataSnapshot.child("lockers").child("medium-4").child("isOccupied").getValue(Integer.class)==1
                        || !size.equals("medium")){
                    M4.setCardBackgroundColor(getResources().getColor(R.color.abu));
                    M4.setClickable(false);
                }
                if(dataSnapshot.child("lockers").child("medium-5").child("isOccupied").getValue(Integer.class)==1
                        || !size.equals("medium")){
                    M5.setCardBackgroundColor(getResources().getColor(R.color.abu));
                    M5.setClickable(false);
                }

                if(dataSnapshot.child("lockers").child("large-1").child("isOccupied").getValue(Integer.class)==1
                        || !size.equals("large")){
                    L1.setCardBackgroundColor(getResources().getColor(R.color.abu));
                    L1.setClickable(false);
                }
                if(dataSnapshot.child("lockers").child("large-2").child("isOccupied").getValue(Integer.class)==1
                        || !size.equals("large")){
                    L2.setCardBackgroundColor(getResources().getColor(R.color.abu));
                    L2.setClickable(false);
                }
                if(dataSnapshot.child("lockers").child("large-3").child("isOccupied").getValue(Integer.class)==1
                        || !size.equals("large")){
                    L3.setCardBackgroundColor(getResources().getColor(R.color.abu));
                    L3.setClickable(false);
                }
                if(dataSnapshot.child("lockers").child("large-4").child("isOccupied").getValue(Integer.class)==1
                        || !size.equals("large")){
                    L4.setCardBackgroundColor(getResources().getColor(R.color.abu));
                    L4.setClickable(false);
                }
                if(dataSnapshot.child("lockers").child("large-5").child("isOccupied").getValue(Integer.class)==1
                        || !size.equals("large")){
                    L5.setCardBackgroundColor(getResources().getColor(R.color.abu));
                    L5.setClickable(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        S1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(noLocker.this, confirmOrder.class);
                String idlocker = "S-1";
                String size= "Small";
                String cost = "6000";
                i.putExtra("id", idlocker);
                i.putExtra("sizelocker", size);
                i.putExtra("hourlycost", cost);
                startActivity(i);
            }
        });

        S2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(noLocker.this, confirmOrder.class);
                String idlocker = "S-2";
                String size= "Small";
                String cost = "6000";
                i.putExtra("id", idlocker);
                i.putExtra("sizelocker", size);
                i.putExtra("hourlycost", cost);
                startActivity(i);
            }
        });

        S3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(noLocker.this, confirmOrder.class);
                String idlocker = "S-3";
                String size= "Small";
                String cost = "6000";
                i.putExtra("id", idlocker);
                i.putExtra("sizelocker", size);
                i.putExtra("hourlycost", cost);
                startActivity(i);
            }
        });

        S4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(noLocker.this, confirmOrder.class);
                String idlocker = "S-4";
                String size= "Small";
                String cost = "6000";
                i.putExtra("id", idlocker);
                i.putExtra("sizelocker", size);
                i.putExtra("hourlycost", cost);
                startActivity(i);
            }
        });

        S5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(noLocker.this, confirmOrder.class);
                String idlocker = "S-5";
                String size= "Small";
                String cost = "6000";
                i.putExtra("id", idlocker);
                i.putExtra("sizelocker", size);
                i.putExtra("hourlycost", cost);
                startActivity(i);
            }
        });

        M1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(noLocker.this, confirmOrder.class);
                String idlocker = "M-1";
                String size= "Medium";
                String cost = "7500";
                i.putExtra("id", idlocker);
                i.putExtra("sizelocker", size);
                i.putExtra("hourlycost", cost);
                startActivity(i);
            }
        });

        M2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(noLocker.this, confirmOrder.class);
                String idlocker = "M-2";
                String size= "Medium";
                String cost = "7500";
                i.putExtra("id", idlocker);
                i.putExtra("sizelocker", size);
                i.putExtra("hourlycost", cost);
                startActivity(i);
            }
        });

        M3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(noLocker.this, confirmOrder.class);
                String idlocker = "M-3";
                String size= "Medium";
                String cost = "7500";
                i.putExtra("id", idlocker);
                i.putExtra("sizelocker", size);
                i.putExtra("hourlycost", cost);
                startActivity(i);
            }
        });

        M4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(noLocker.this, confirmOrder.class);
                String idlocker = "M-4";
                String size= "Medium";
                String cost = "7500";
                i.putExtra("id", idlocker);
                i.putExtra("sizelocker", size);
                i.putExtra("hourlycost", cost);
                startActivity(i);
            }
        });

        M5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(noLocker.this, confirmOrder.class);
                String idlocker = "M-5";
                String size = "Medium";
                String cost = "7500";
                i.putExtra("id", idlocker);
                i.putExtra("sizelocker", size);
                i.putExtra("hourlycost", cost);
                startActivity(i);
            }
        });

        L1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(noLocker.this, confirmOrder.class);
                String idlocker = "L-1";
                String size= "Large";
                String cost = "9000";
                i.putExtra("id", idlocker);
                i.putExtra("sizelocker", size);
                i.putExtra("hourlycost", cost);
                startActivity(i);
            }
        });

        L2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(noLocker.this, confirmOrder.class);
                String idlocker = "L-2";
                String size= "Large";
                String cost = "9000";
                i.putExtra("id", idlocker);
                i.putExtra("sizelocker", size);
                i.putExtra("hourlycost", cost);
                startActivity(i);
            }
        });

        L3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(noLocker.this, confirmOrder.class);
                String idlocker = "L-3";
                String size= "Large";
                String cost = "9000";
                i.putExtra("id", idlocker);
                i.putExtra("sizelocker", size);
                i.putExtra("hourlycost", cost);
            }
        });

        L4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(noLocker.this, confirmOrder.class);
                String idlocker = "L-4";
                String size= "Large";
                String cost = "9000";
                i.putExtra("id", idlocker);
                i.putExtra("sizelocker", size);
                i.putExtra("hourlycost", cost);
                startActivity(i);
            }
        });

        L5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(noLocker.this, confirmOrder.class);
                String idlocker = "L-5";
                String size= "Large";
                String cost = "9000";
                i.putExtra("id", idlocker);
                i.putExtra("sizelocker", size);
                i.putExtra("hourlycost", cost);
                startActivity(i);
            }
        });



    }
}
