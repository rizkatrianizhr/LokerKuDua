package com.e.aplikasiku;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class Pesanan extends AppCompatActivity {

    private TextView name, Size, Cost, emaill, Id, Status, Date, Hours, Pay;
    private Button Scan, Back;
    private CardView Finish;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String idusernya;
    private ValueEventListener valueEventListener;


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
        Status = (TextView)  findViewById(R.id.status);
        Id = (TextView) findViewById(R.id.idloker);
        Cost = (TextView) findViewById(R.id.cost);
        Scan = (Button) findViewById(R.id.scan);
        Back = (Button) findViewById(R.id.btnBack);


        handler.post(timedTask);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                DataSnapshot orderDs = dataSnapshot.child("users").child(idusernya).child("order");

                final String idLocker = orderDs.child("idLocker").getValue(String.class);
                orderDate = orderDs.child("oorderDate").getValue(String.class);
                Log.d("orderDate>>:", orderDate);

                hourlyCost = dataSnapshot.child("lockers").child(idLocker).child("cost").getValue(Integer.class);

                String size = "";
                if (idLocker.substring(0, 1) == "s") size = "Small";
                else if (idLocker.substring(0, 1) == "m") size = "Medium";
                else size = "Large";

                Size.setText(size);
                Id.setText(idLocker);
                Cost.setText(hourlyCost + "");

                if (dataSnapshot.child("lockers").child(idLocker).child("notif").getValue(Integer.class) == 1) {
                    Status.setText("Opened");
                    Status.setTextColor(getResources().getColor(R.color.indigo));

                    NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(Pesanan.this)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setSmallIcon(R.drawable.ic_account)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_waktu))
                            .setContentTitle("Locker Status")
                            .setContentText("The door successfully opened");

                    Intent resultIntent = new Intent(Pesanan.this, Pesanan.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(Pesanan.this);
                    stackBuilder.addParentStack(Pesanan.class);

                    // Adds the Intent that starts the Activity to the top of the stack
                    stackBuilder.addNextIntent(resultIntent);
                    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(resultPendingIntent);

                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(1, builder.build());
                } else {
                    Status.setText("Closed");
                    Status.setTextColor(getResources().getColor(R.color.bluedark));
                }

                emaill.setText(firebaseAuth.getCurrentUser().getEmail());

                Date.setText(orderDate);


                calcDuration();

                Scan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("scanclick>>:", idLocker);
                        Intent intent = new Intent(Pesanan.this, scanPesanan.class);
                        intent.putExtra("id",idLocker);
                        databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid()).child("order").child("bill").setValue(totalPrice);
                        startActivity(intent);

                    }
                });

                Finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(Pesanan.this);
                        builder.setTitle("Are you sure?");
                        builder.setMessage("Are you really want to finish this order?");
                        builder.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                calcDuration();
                                int balance = Integer.parseInt(dataSnapshot.child("users").child(firebaseAuth.getCurrentUser().getUid()).child("balance").getValue(String.class));

                                if(totalPrice > balance){
                                    Toast.makeText(Pesanan.this, "Your balance is not enough", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                balance = balance - totalPrice;


                                databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid()).child("balance").setValue(balance+"");
                                databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid()).child("order").removeValue();

//                                Order order = new Order(idLocker, firebaseAuth.getCurrentUser().getEmail(), currentdate.toString(), totalPrice);
//                                databaseReference.child("Orders").push().setValue(order);

                                databaseReference.child("lockers").child(idLocker).child("isOccupied").setValue(0);
                                databaseReference.child("lockers").child(idLocker).child("occupiedBy").setValue("");

                                Order order = new Order(idLocker, firebaseAuth.getCurrentUser().getEmail(), currentdate.toString(), totalPrice);
                                databaseReference.child("Orders").push().setValue(order);

                                Toast.makeText(Pesanan.this, "Finish", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Pesanan.this, Promo.class);
                                startActivity(intent);
                            }
                        });
                        builder.setNeutralButton("Dismiss", null);

                        builder.create().show();
                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        databaseReference.addValueEventListener(valueEventListener);

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
            currentdate = DateFormat.getDateTimeInstance().format(thatDay.getTimeInMillis());
            Date.setText(currentdate);

            Calendar today = Calendar.getInstance();
            currentTime = today.getTimeInMillis();

            long diff = (currentTime - thatDay.getTimeInMillis()) / 1000;
            long hours = diff / 3600;
            long minutes = (diff % 3600) / 60;
            long seconds = (diff % 3600) % 60;

            totalPrice = (int) ((hours + 1) * hourlyCost);
            Hours.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));

            Pay.setText(totalPrice + "");

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(valueEventListener);
    }
}