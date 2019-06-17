package com.e.aplikasiku;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.e.aplikasiku.models.Locker;
import com.e.aplikasiku.models.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Pesanan extends AppCompatActivity {

    private TextView name, Size, Cost, emaill, Id, Number, Date, Hours, Pay;
    private Button Scan, Back;
    private CardView Finish;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String idusernya;

    String orderDate = "";
    int hourlyCost = 0;
    long currentTime = 0;
    int totalPrice = 0;
    String currentdate;

    Handler handler = new Handler();
    Runnable timedTask = new Runnable(){
        @Override
        public void run() {

            calcDuration();
            handler.postDelayed(timedTask, 1000);
        }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesanan);

        Log.d("DEBUG", "Pesanan Activity");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        idusernya = user.getUid();

        Hours  = (TextView) findViewById(R.id.hour);
        Pay = (TextView) findViewById(R.id.pay);
        Date = (TextView) findViewById(R.id.date);
        Finish = (CardView) findViewById(R.id.finish);
        name = (TextView) findViewById(R.id.nameUser);
        emaill = (TextView) findViewById(R.id.emailUser);
        Size = (TextView) findViewById(R.id.size);
        Id = (TextView) findViewById(R.id.idloker);
        Cost = (TextView) findViewById(R.id.cost);
        Scan = (Button) findViewById(R.id.scan);
        Back = (Button) findViewById(R.id.btnBack);

        handler.post(timedTask);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                DataSnapshot orderDs = dataSnapshot.child("users").child(idusernya).child("order");

                final String idLocker = orderDs.child("idLocker").getValue(String.class);
                orderDate = orderDs.child("oorderDate").getValue(String.class);

                hourlyCost = dataSnapshot.child("lockers").child(idLocker).child("cost").getValue(Integer.class);

                String size = "";
                if (idLocker.substring(0, 1) == "s") size = "Small";
                else if (idLocker.substring(0, 1) == "m") size = "Medium";
                else size = "Large";

                Size.setText(size);
                Id.setText(idLocker);
                Cost.setText(hourlyCost + "");

                emaill.setText(firebaseAuth.getCurrentUser().getEmail());

                Date.setText(orderDate);

                calcDuration();

                Scan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Pesanan.this, scanPesanan.class);
                        intent.putExtra("id",idLocker);
                        databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid()).child("order").child("bill").setValue(totalPrice);
                        startActivity(intent);

                    }
                });

                Finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        calcDuration();
                        int balance = Integer.parseInt(dataSnapshot.child("users").child(firebaseAuth.getCurrentUser().getUid()).child("balance").getValue(String.class));

                        if(totalPrice > balance){
                            Toast.makeText(Pesanan.this, "Saldo tidak cukup", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        balance = balance - totalPrice;

                        databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid()).child("order").removeValue();
                        databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid()).child("balance").setValue(balance+"");

                        databaseReference.child("lockers").child(idLocker).child("isOccupied").setValue(0);
                        databaseReference.child("lockers").child(idLocker).child("occupiedBy").setValue("");

                        Order order = new Order(idLocker, firebaseAuth.getCurrentUser().getEmail(), currentdate.toString(), totalPrice);

                        databaseReference.child("orders").push().setValue(order);

                        Toast.makeText(Pesanan.this, "Selesai", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Pesanan.this, LamanUtama.class);
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        Scan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Pesanan.this, Scan.class));
//            }
//        });
//
//        Finish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//        Scan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            startActivity(new Intent(Pesanan.this, com.e.aplikasiku.Scan.class));
//            }
//        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    private void calcDuration(){
        if (orderDate != "") {
            Calendar thatDay = Calendar.getInstance();
            thatDay.setTime(new Date(orderDate));
            currentdate = DateFormat.getDateInstance().format(thatDay.getTimeInMillis());
            Date.setText(currentdate);

            Calendar today = Calendar.getInstance();
            currentTime = today.getTimeInMillis();

            long diff = (currentTime - thatDay.getTimeInMillis()) / 1000;
            long hours = diff / 3600;
            long minutes = (diff % 3600) / 60;
            long seconds = (diff % 3600) % 60;
//                long hours = diff / (60*60*1000);

            totalPrice = (int) ((hours + 1) * hourlyCost);
            Hours.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));

//                long totalPrice = hourlyCost * diff;
//                Hours.setText(diff + "");

            Pay.setText(totalPrice + "");

//                Toast.makeText(Pesanan.this, "Hours: "+ hours+" | Total: "+totalPrice, Toast.LENGTH_SHORT).show();
////
//////                Hours.setText(hours+"");
//                Toast.makeText(Pesanan.this, "Total Price: "+totalPrice, Toast.LENGTH_SHORT).show();
        }
    }
}