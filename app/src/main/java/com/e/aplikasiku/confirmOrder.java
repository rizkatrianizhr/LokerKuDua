package com.e.aplikasiku;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class confirmOrder extends AppCompatActivity {
    private TextView name, Size, Cost, emaill, Id, Number;
    private Button Scan, Back;
    private ProgressBar Probar;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String idusernya;

    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        idusernya = user.getUid();

        name = (TextView) findViewById(R.id.nameUser);
        Number = (TextView) findViewById(R.id.number);
        emaill = (TextView) findViewById(R.id.emailUser);
        Size = (TextView) findViewById(R.id.size);
        Id = (TextView) findViewById(R.id.idloker);
        Cost = (TextView) findViewById(R.id.cost);
        Scan = (Button) findViewById(R.id.scan);
        Back = (Button) findViewById(R.id.btnBack);

        final String id = getIntent().getExtras().getString("id");
        String size = getIntent().getExtras().getString("sizelocker");
        String cost = getIntent().getExtras().getString("hourlycost");
        String no = getIntent().getExtras().getString("id");
        Id.setText(id);
        Size.setText(size);
        Cost.setText(cost);
        Number.setText(no);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nama = dataSnapshot.child("users").child(idusernya).child("name").getValue(String.class);
                String email = dataSnapshot.child("users").child(idusernya).child("email").getValue(String.class);
                name.setText(nama);
                emaill.setText(email);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        // databaseReference.addValueEventListener(valueEventListener);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nama = dataSnapshot.child("users").child(idusernya).child("name").getValue(String.class);
                String email = dataSnapshot.child("users").child(idusernya).child("email").getValue(String.class);
                name.setText(nama);
                emaill.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d("click>>");
                Intent intent = new Intent(confirmOrder.this, Scan.class);
                intent.putExtra("id",id);
                startActivity(intent);
                finish();
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
    protected void onDestroy() {
        super.onDestroy();
        if (valueEventListener != null)
            databaseReference.removeEventListener(valueEventListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(confirmOrder.this, noLocker.class));
    }
}
