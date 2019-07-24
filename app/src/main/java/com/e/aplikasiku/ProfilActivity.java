package com.e.aplikasiku;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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


public class ProfilActivity extends AppCompatActivity {
    private TextView getName, getNoHp, getEmail;
    private Button ubahPassword, Back, Setting;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String idusernya;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        idusernya = user.getUid();
        getName = (TextView) findViewById(R.id.nama);
        getEmail = (TextView) findViewById(R.id.email);
        getNoHp = (TextView) findViewById(R.id.nohp);
        ubahPassword = (Button) findViewById(R.id.ubahpassword);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Back = (Button) findViewById(R.id.btnBack);
        Setting = (Button) findViewById(R.id.settings);

        setSupportActionBar(toolbar);

        ubahPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilActivity.this, GantiPassword.class));
            }
        });

        Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilActivity.this, Settings.class));
            }
        });

//        Remove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final AlertDialog.Builder builder = new AlertDialog.Builder(ProfilActivity.this);
//                builder.setMessage("Apakah anda yakin ingin menghapus akun ini?");
//                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (user != null) {
//                            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(ProfilActivity.this, "Akun berhasil terhapus", Toast.LENGTH_SHORT).show();
//                                        startActivity(new Intent(ProfilActivity.this, Login.class));
//                                        finish();
//                                    } else {
//                                        Toast.makeText(ProfilActivity.this, "Gagal mengapus akun!", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                        }
//                    }
//                }).setNeutralButton("Tidak", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener()  {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String nama = dataSnapshot.child("users").child(idusernya).child("name").getValue(String.class);
               String email = dataSnapshot.child("users").child(idusernya).child("email").getValue(String.class);
               String nomorhp = dataSnapshot.child("users").child(idusernya).child("telepon").getValue(String.class);
               getName.setText(nama);
               getEmail.setText(email);
               getNoHp.setText(nomorhp);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, Login.class));
                break;
        }
        return true;


    }
}