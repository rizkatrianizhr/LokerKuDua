package com.e.aplikasiku;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class rentLocker extends AppCompatActivity {

    private Button Small, Medium, Large, Back;
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
        setContentView(R.layout.activity_rent_locker);

        Small = (Button) findViewById(R.id.btnSmall);
        Medium = (Button) findViewById(R.id.btnMedium);
        Large = (Button) findViewById(R.id.btnLarge);
        Back = (Button) findViewById(R.id.btnBack);

        Small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(rentLocker.this);
                    builder.setMessage("Please, login first to continue the order process ");
                    builder.setNeutralButton("No", null);
                    builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(rentLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }

                if (auth.getCurrentUser() != null) {
                    FirebaseUser user = auth.getCurrentUser();
                    iduser = user.getUid();
                    Intent i = new Intent(rentLocker.this, noLocker.class);

                    //TODO HINT: Perlu dingat data ini akan dikirim kan ke noLockerActivity
                    i.putExtra("locker", "small");

                    startActivity(i);
                }
            }

        });

        Medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(rentLocker.this);
                    builder.setMessage("Please, login first to continue the order process");
                    builder.setNeutralButton("No", null);
                    builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(rentLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    FirebaseUser user = auth.getCurrentUser();
                    iduser = user.getUid();


                    Intent i = new Intent(rentLocker.this, noLocker.class);

                    //TODO HINT: Perlu dingat data ini akan dikirim kan ke noLockerActivity
                    i.putExtra("locker", "medium");

                    startActivity(i);
                }
            }
        });

        Large.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(rentLocker.this);
                    builder.setMessage("Please, login first to continue the order process");
                    builder.setNeutralButton("No", null);
                    builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(rentLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    FirebaseUser user = auth.getCurrentUser();
                    iduser = user.getUid();
                    Intent i = new Intent(rentLocker.this, noLocker.class);

                    //TODO HINT: Perlu dingat data ini akan dikirim kan ke noLockerActivity
                    i.putExtra("locker", "large");


                    startActivity(i);
                }
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(rentLocker.this, LamanUtama.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(rentLocker.this, LamanUtama.class));
    }
}

