package com.e.aplikasiku;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class addsaldo extends AppCompatActivity {

    private TextView Balance;
    private EditText Topup;
    private Button Bayar, Back;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase userDatabase;
    private FirebaseUser user;
    String iduser;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsaldo);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        iduser = user.getUid();
        userDatabase = FirebaseDatabase.getInstance();
        databaseReference = userDatabase.getReference();

        Balance = (TextView) findViewById(R.id.balance);
        Bayar = (Button) findViewById(R.id.bayar);
        Back = (Button) findViewById(R.id.btnBack);
        Topup = (EditText) findViewById(R.id.topup);
        dialog = new ProgressDialog(this);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String balance = dataSnapshot.child("users").child(iduser).child("balance").getValue(String.class);
                Balance.setText(balance);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setMessage("Loading...");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                final String jumlah = Topup.getText().toString().trim();


                if (TextUtils.isEmpty(jumlah)) {
                    dialog.dismiss();
                    Bayar.setClickable(false);

                    return;
                }

                if (!TextUtils.isEmpty(jumlah)) {
                    dialog.dismiss();
                    databaseReference.child("users")
                            .child(auth.getCurrentUser().getUid())
                            .child("balanceSuspend").setValue(jumlah);
                    startActivity(new Intent(addsaldo.this, Bank.class));
                }
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}
