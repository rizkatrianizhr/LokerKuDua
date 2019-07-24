package com.e.aplikasiku;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Settings extends AppCompatActivity {

    private Button Logout, Myprof, Pw, Help, Delete, Back;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String idusernya;
    private ProgressDialog Dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        idusernya = user.getUid();

        Dialog = new ProgressDialog(this);

        Logout = (Button) findViewById(R.id.logout);
        Myprof = (Button) findViewById(R.id.myprofile);
        Pw = (Button) findViewById(R.id.pw);
        Delete = (Button) findViewById(R.id.delete);
        Help = (Button) findViewById(R.id.help);
        Back = (Button) findViewById(R.id.btnBack);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, LamanUtama.class));
            }
        });


        Myprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, ProfilActivity.class));
            }
        });

        Pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, GantiPassword.class));
            }
        });

        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, helpCenter.class));
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(Settings.this, LamanUtama.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
//                dialog.setMessage("Signing Out...");
//                dialog.show();
//
//                firebaseAuth.signOut();
//                finish();
//                dialog.dismiss();
//                startActivity(new Intent(Settings.this, LamanUtama.class));
//                Toast.makeText(Settings.this, "Succesfully Logout...", Toast.LENGTH_SHORT).show();
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Settings.this);
                dialog.setTitle("Are you sure?");
                dialog.setMessage("Deleting is account will result in completely removing your"
                        + "account for the system and you won't be able to access the app.");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog.setMessage("Deacived Account...");
                        Dialog.show();
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Dialog.dismiss();
                                    Toast.makeText(Settings.this, "Account Deleted", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Settings.this, LamanUtama.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else {
                                    Dialog.dismiss();
                                    Toast.makeText(Settings.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }); dialog.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });



    }

//    public void DeleteAccount (View view) {
//
//        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
//        if (user != null ) {
//            dialog.setMessage("Delete Account...");
//            dialog.show();
//            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if (task.isSuccessful()) {
//                        dialog.dismiss();
//                        startActivity(new Intent(Settings.this, LamanUtama.class));
//                        Toast.makeText(getApplicationContext(), "Account deactivated", Toast.LENGTH_LONG).show();
//                    } else {
//                        dialog.dismiss();
//                        Toast.makeText(getApplicationContext(), "Account couldn't be deactivated", Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
//        }
    }

