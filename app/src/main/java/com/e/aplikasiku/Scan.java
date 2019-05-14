package com.e.aplikasiku;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.e.aplikasiku.model.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class Scan extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
    private DatabaseReference mDatabase;
    private FirebaseAuth authentication;
//    private FirebaseAuth auth;
//    private DatabaseReference databaseReference;
//    private FirebaseDatabase userDatabase;
//    private FirebaseUser user;
//    String iduser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        auth = FirebaseAuth.getInstance();
//        userDatabase = FirebaseDatabase.getInstance();
//        databaseReference = userDatabase.getReference();
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(Scan.this, "Permission is granted", Toast.LENGTH_SHORT).show();
            } else {
                requestPermissions();
            }
        }
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(Scan.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
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
                        Toast.makeText(Scan.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Scan.this, "Permission Denied", Toast.LENGTH_LONG).show();
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

    public  void displayAlertMessage (String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(Scan.this)
                .setMessage(message)
                .setPositiveButton("Oke", listener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }

    @Override
    public void handleResult(Result result) {
        final String scanResult = result.getText();
//        Toast.makeText(this, "Result", Toast.LENGTH_SHORT).show();
        showResult(scanResult);
    }

    private void showResult (final String id) {


//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Scan Result");
//        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int i) {
//                mScannerView.resumeCameraPreview(Scan.this);
//
//            }
//        });
//        builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int i) {
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(id));
//                startActivity(intent);
//
//            }
//        });
//        builder.setMessage(id);
//        AlertDialog alert = builder.create();
//        alert.show();


        mDatabase = FirebaseDatabase.getInstance().getReference();

        final boolean sameUser = true;

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int isOccupied = dataSnapshot.child("lockers").child(id).child("isOpen").getValue(Integer.class);
                FirebaseUser currentUser = authentication.getCurrentUser();

                if(isOccupied == 0|| (isOccupied == 1  && sameUser)){
                    if(isOccupied == 0){
                        Order newOrder = new Order(0,currentUser.getEmail(),id, new Date());
                        mDatabase.child("orders").child("asb36fbsdf").setValue(newOrder);

                        mDatabase.child("lockers").child(id).child("isOccupied").setValue(1);
                        mDatabase.child("lockers").child(id).child("occupiedBy").setValue(currentUser.getEmail());

                        mDatabase.child("lockers").child(id).child("isOpen").setValue(1);
                    }else{
                        String email = dataSnapshot.child("lockers").child(id).child("occupiedBy").getValue(String.class);
                        if(email == currentUser.getEmail()){
                            mDatabase.child("lockers").child(id).child("isOpen").setValue(1);
                        }else{
                            //TODO: Show dialog that restrict user to open locker
                            Toast.makeText(Scan.this, "Locker is Occupied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

