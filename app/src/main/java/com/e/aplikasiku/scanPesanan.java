package com.e.aplikasiku;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.service.autofill.SaveCallback;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.e.aplikasiku.models.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.util.Calendar;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class scanPesanan extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String idusernya;
    String balance;
    Integer bill;

    String idlocker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        idusernya = user.getUid();

        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);

        idlocker = getIntent().getExtras().getString("id");
        Toast.makeText(this, "Locker yg dipinjam: " + idlocker, Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(scanPesanan.this, "Permission is granted", Toast.LENGTH_SHORT).show();
            } else {
                requestPermissions();
            }
        }
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(scanPesanan.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode, String permission[], int grantResults[]) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        Toast.makeText(scanPesanan.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(scanPesanan.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                displayAlertMessage("You need to allow access for both permissions", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                        }
                                    }
                                });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermission()) {
            if (mScannerView == null) {
                mScannerView = new ZXingScannerView(this);
                setContentView(mScannerView);
            }
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();
        } else {
            requestPermissions();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mScannerView.stopCamera();
    }

    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(scanPesanan.this)
                .setMessage(message)
                .setPositiveButton("Oke", listener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void handleResult(Result result) {
        final String scanResult = result.getText();
        showResult(scanResult);
    }

    private void showResult(final String id) {

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                balance = dataSnapshot.child("users")
                        .child(firebaseAuth.getCurrentUser().getUid()).child("balance").getValue(String.class);
                bill = dataSnapshot.child("users").child(firebaseAuth.getCurrentUser().getUid()).child("order").child("bill").getValue(Integer.class);

                Toast.makeText(scanPesanan.this, "balance: "+balance, Toast.LENGTH_SHORT).show();
                Toast.makeText(scanPesanan.this, "bill: "+bill, Toast.LENGTH_SHORT).show();

                int saldo = Integer.parseInt(balance);

                if (id.equals(idlocker) && saldo >= bill) {
                    databaseReference.child("lockers").child(idlocker).child("isOpen").setValue(1);
                    startActivity(new Intent(getApplicationContext(), Pesanan.class));

                } else if (saldo <= bill) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(scanPesanan.this);
                    builder.setMessage("Sorry, Your balance is insufficient! Please, do top up to open your locker");
                    builder.setPositiveButton("Top Up", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getApplicationContext(), Ecash.class));
                        }
                    });
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(scanPesanan.this);
                    builder.setMessage("Please, scan the number locker you selected!");
                    builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mScannerView.resumeCameraPreview(scanPesanan.this);
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        }

//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                balance = dataSnapshot.child("users")
//                        .child(firebaseAuth.getCurrentUser().getUid()).child("balance").getValue(String.class);
//                bill = dataSnapshot.child("users").child(firebaseAuth.getCurrentUser().getUid()).child("order").child("bill").getValue(Integer.class);
//
//                Toast.makeText(scanPesanan.this, "balance: "+balance, Toast.LENGTH_SHORT).show();
//                Toast.makeText(scanPesanan.this, "bill: "+bill, Toast.LENGTH_SHORT).show();
//
//                int saldo = Integer.parseInt(balance);
//
//                if (id.equals(idlocker) && saldo >= bill) {
//                    databaseReference.child("lockers").child(idlocker).child("isOpen").setValue(1);
//                    startActivity(new Intent(getApplicationContext(), Pesanan.class));
//
//                } else if (saldo <= bill) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(scanPesanan.this);
//                    builder.setMessage("Sorry, Your balance is insufficient! Please, do top up to open your locker");
//                    builder.setPositiveButton("Top Up", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            startActivity(new Intent(getApplicationContext(), Ecash.class));
//                        }
//                    });
//                } else {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(scanPesanan.this);
//                    builder.setMessage("Please, scan the number locker you selected!");
//                    builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            mScannerView.resumeCameraPreview(scanPesanan.this);
//                        }
//                    });
//                    AlertDialog alert = builder.create();
//                    alert.show();
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//    }
}
