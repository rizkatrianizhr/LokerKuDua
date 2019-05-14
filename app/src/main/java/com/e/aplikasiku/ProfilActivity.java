package com.e.aplikasiku;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfilActivity extends AppCompatActivity {
    private TextView getName, getNoHp, getEmail, getSaldo;
    private Button ubahPassword, Remove, Add;
    private ProgressBar loading;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String idusernya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        idusernya = user.getUid();
        Add = (Button) findViewById(R.id.add);
        getSaldo = (TextView) findViewById(R.id.saldo);
        getName = (TextView) findViewById(R.id.nama);
        getEmail = (TextView) findViewById(R.id.email);
        getNoHp = (TextView) findViewById(R.id.nohp);
        ubahPassword = (Button) findViewById(R.id.ubahpassword);
        Remove = (Button) findViewById(R.id.hapusakun);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ubahPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilActivity.this, GantiPassword.class));
            }
        });

        Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ProfilActivity.this);
                builder.setMessage("Apakah anda yakin ingin menghapus akun ini?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (user != null) {
                            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ProfilActivity.this, "Akun berhasil terhapus", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ProfilActivity.this, Login.class));
                                        finish();
                                    } else {
                                        Toast.makeText(ProfilActivity.this, "Gagal mengapus akun!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }).setNeutralButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilActivity.this, TopUp.class));
            }
        });



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer saldo = dataSnapshot.child(idusernya).child("balance").getValue(Integer.class);
                //String nama = dataSnapshot.child(idusernya).child("nama").getValue(String.class);
                String email = dataSnapshot.child(idusernya).child("email").getValue(String.class);
                //String nohp = dataSnapshot.child(idusernya).child("telepon").getValue(String.class);
                //getName.setText(nama);

                //getEmail.setText(email);

                //getNoHp.setText(nohp);

                //getSaldo.setText(Integer.toString(saldo));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ProfilActivity.this, MainActivity.class));
    }


}