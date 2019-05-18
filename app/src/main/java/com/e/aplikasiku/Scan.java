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
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class Scan extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String idusernya;

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
        showResult(scanResult);
    }

    private void showResult (final String id) {

        final String idlocker = getIntent().getExtras().getString("id");
        final String size = getIntent().getExtras().getString("locker");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                if (id != null && id.equals("small-1") || !idlocker.equals("S-1")
                        || dataSnapshot.child("lockers").child("small-1").child("occupiedBy").getValue(String.class)
                        == dataSnapshot.child("users").child(idusernya).child("email").getValue(String.class)){
                    databaseReference.child("lockers").child("small-1").child("occupiedBy").setValue(String.class);




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        if (!idlocker.equals("S-1")) {
            databaseReference.child("lockers").child("small-1").child("isOpen").setValue(0);
            AlertDialog.Builder builder = new AlertDialog.Builder(Scan.this);
            builder.setMessage("Silahkan scan nomor locker yang anda pilih");
            builder.setPositiveButton("Scan lagi", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mScannerView.resumeCameraPreview(Scan.this);
                }
            });

        }

        if (id.equals("small-2")){
            databaseReference.child("lockers").child("small-2").child("isOpen").setValue(1);
            startActivity(new Intent(Scan.this, Pesanan.class));

        }

        if (!idlocker.equals("S-2")) {
            databaseReference.child("lockers").child("small-2").child("isOpen").setValue(1);
            AlertDialog.Builder builder = new AlertDialog.Builder(Scan.this);
            builder.setMessage("Silahkan scan nomor locker yang anda pilih");
            builder.setPositiveButton("Scan lagi", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mScannerView.resumeCameraPreview(Scan.this);
                }
            });

        }
        if (!id.equals("small-3") || !idlocker.equals("S-3") ){
            databaseReference.child("lockers").child("small-3").setValue(0);
            AlertDialog.Builder builder = new AlertDialog.Builder(Scan.this);
            builder.setMessage("Silahkan scan nomor locker yang anda pilih");
            builder.setPositiveButton("Scan lagi", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mScannerView.resumeCameraPreview(Scan.this);
                }
            });
        }




//        if (id.equals("small-01")){
//            databaseReference.child("lockers").child("small-1").child("isOpen").setValue(1);
//            startActivity(new Intent(Scan.this, Pesanan.class));
//            finish();
        }
        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                mScannerView.resumeCameraPreview(Scan.this);

            }
        });
        builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(id));
                startActivity(intent);

            }
        });
        builder.setMessage(id);
        AlertDialog alert = builder.create();
        alert.show();*/

    }

