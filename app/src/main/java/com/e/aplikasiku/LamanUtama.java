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
    private TextView WelcomeLogin, Welcome, Names, Balance, Text, WelcomeBack;
    private CardView NotLogin, cvSaldo;
    private Button Login, Regis, Order, Rent, Profile, Help, Settings, TopUp;


    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String idusernya;
    String occupiedBy;


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
        WelcomeLogin = (TextView) findViewById(R.id.welcomeLogin);
        Text = (TextView) findViewById(R.id.text1);
        Welcome = (TextView) findViewById(R.id.welcome);
        WelcomeBack = (TextView) findViewById(R.id.welcomeback);
        Names = (TextView) findViewById(R.id.names);
        Balance = (TextView) findViewById(R.id.balance);
        cvSaldo = (CardView) findViewById(R.id.cv_saldo);
        NotLogin = (CardView) findViewById(R.id.notLogin);


        if (auth.getCurrentUser() == null) {
            WelcomeLogin.setVisibility(View.GONE);
            Names.setVisibility(View.GONE);
            cvSaldo.setVisibility(View.GONE);
            WelcomeBack.setVisibility(View.GONE);
            Welcome.setVisibility(View.VISIBLE);
            NotLogin.setVisibility(View.VISIBLE);
            Text.setVisibility(View.VISIBLE);


        }
        if (auth.getCurrentUser() != null) {
            final FirebaseUser user = auth.getCurrentUser();
            idusernya = user.getUid();

            Welcome.setVisibility(View.GONE);
            Text.setVisibility(View.GONE);
            NotLogin.setVisibility(View.GONE);
            Names.setVisibility(View.VISIBLE);
            WelcomeBack.setVisibility(View.VISIBLE);
            WelcomeLogin.setVisibility(View.VISIBLE);
            cvSaldo.setVisibility(View.VISIBLE);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("users").child(idusernya).child("name").getValue(String.class);
                    String dompet = dataSnapshot.child("users").child(idusernya).child("balance").getValue(String.class);
//                    occupiedBy = dataSnapshot.child("users").child(auth.getCurrentUser().getUid()).child("order").getValue(String.class);
                    Names.setText(name);
                    Balance.setText(dompet);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        Rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LamanUtama.this, rentLocker.class));
            }
        });

        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (auth.getCurrentUser() == null) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(LamanUtama.this);
//                    builder.setMessage("Silahkan Login terlebih dahulu!");
//                    builder.setNeutralButton("tidak", null);
//                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(LamanUtama.this, Login.class));
//                        }
//                   });
//                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    //apakah sudah pesan
                    startActivity(new Intent(LamanUtama.this, Pesanan.class));
//                } else {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(LamanUtama.this);
//                    builder.setMessage("You don't have any orderd yet");
//                    builder.setPositiveButton("Ok!", null);
//                    builder.create().show();
//
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
//                    builder.create().show();
//                    }
//                }


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
                //startActivity(new Intent(LamanUtama.this, com.e.aplikasiku.Help.class));

                //Get an instance of NotificationManager//
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(LamanUtama.this)
                                .setSmallIcon(R.drawable.bcalogo)
                                .setContentTitle("My notification")
                                .setContentText("Hello World!");

                Intent resultIntent = new Intent(LamanUtama.this, Pesanan.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(LamanUtama.this);
                stackBuilder.addParentStack(Pesanan.class);

// Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(resultPendingIntent);

                // Gets an instance of the NotificationManager service//

                NotificationManager mNotificationManager =

                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                // When you issue multiple notifications about the same type of event,
                // it’s best practice for your app to try to update an existing notification
                // with this new information, rather than immediately creating a new notification.
                // If you want to update this notification at a later date, you need to assign it an ID.
                // You can then use this ID whenever you issue a subsequent notification.
                // If the previous notification is still visible, the system will update this existing notification,
                // rather than create a new one. In this example, the notification’s ID is 001//

                //NotificationManager.notify().

                mNotificationManager.notify(001, mBuilder.build());
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


