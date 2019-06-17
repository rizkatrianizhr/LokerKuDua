package com.e.aplikasiku;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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

import java.util.Calendar;

public class confirmOrder extends AppCompatActivity {
    private TextView name, Size, Cost, emaill, Id, Number;
    private Button Scan, Back;
    private ProgressBar Probar;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String idusernya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
//        Probar = (ProgressBar) findViewById(R.id.probar);
//        Probar.setVisibility(View.GONE);
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

        databaseReference.addValueEventListener(new ValueEventListener() {
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
//                Toast.makeText(confirmOrder.this, "Locker: " + id, Toast.LENGTH_SHORT).show();
//                final String idlocker = getIntent().getExtras().getString("id");
//                final String size = getIntent().getExtras().getString("locker");
//                databaseReference.child("lockers").child(idlocker).child("occupiedBy").setValue(firebaseAuth.getCurrentUser().getEmail());
//                databaseReference.child("lockers").child(idlocker).child("isOpen").setValue(1);
//                databaseReference.child("lockers").child(idlocker).child("isOccupied").setValue(1);
//
//                Order order = new Order(idlocker, firebaseAuth.getCurrentUser().getEmail(), (Calendar.getInstance().getTime()).toString(), 0);
//                databaseReference.child("users")
//                        .child(firebaseAuth.getCurrentUser().getUid())
//                        .child("order").setValue(order);
//
//                databaseReference.child("orders").push().setValue(order);
//
//                startActivity(new Intent(confirmOrder.this, Pesanan.class));

                Intent intent = new Intent(confirmOrder.this, Scan.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(confirmOrder.this, noLocker.class));
            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(confirmOrder.this, noLocker.class));
    }
}
