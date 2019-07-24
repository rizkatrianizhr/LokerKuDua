package com.e.aplikasiku;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.e.aplikasiku.models.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;

import java.util.Calendar;

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

    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener) {
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
        Toast.makeText(this, "Result: " + scanResult, Toast.LENGTH_SHORT).show();
        showResult(scanResult);
    }

    private void showResult(final String id) {

        Toast.makeText(this, "Locker: " + id, Toast.LENGTH_SHORT).show();
        final String idlocker = getIntent().getExtras().getString("id");
        final String size = getIntent().getExtras().getString("locker");

        if (id.equals(idlocker)) {
            Log.d("Scan>>:", idlocker);
            AlertDialog.Builder builder = new AlertDialog.Builder(Scan.this);
            builder.setMessage("Are you sure you want to continue the order?");
            builder.setNeutralButton("No", null);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(Scan.this)
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setSmallIcon(R.drawable.ic_account)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_waktu))
                            .setContentTitle("Successful Order")
                            .setContentText("The locker that you ordered successfully opens");

                    Intent resultIntent = new Intent(Scan.this, Pesanan.class);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(Scan.this);
                    stackBuilder.addParentStack(Pesanan.class);

// Adds the Intent that starts the Activity to the top of the stack
                    stackBuilder.addNextIntent(resultIntent);
                    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
                    builder.setContentIntent(resultPendingIntent);

                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(1, builder.build());


                    databaseReference.child("lockers").child(idlocker).child("occupiedBy").setValue(firebaseAuth.getCurrentUser().getEmail());
                    databaseReference.child("lockers").child(idlocker).child("isOpen").setValue(1);
                    databaseReference.child("lockers").child(idlocker).child("isOccupied").setValue(1);


                    Order order = new Order(idlocker, firebaseAuth.getCurrentUser().getEmail(), (Calendar.getInstance().getTime()).toString(), 0);
                    databaseReference.child("users")
                            .child(firebaseAuth.getCurrentUser().getUid())
                            .child("order").setValue(order);


                    startActivity(new Intent(Scan.this, Pesanan.class));
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(Scan.this);
            builder.setMessage("Please, scan the number locker you selected!");
            builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mScannerView.resumeCameraPreview(Scan.this);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

//    public void Notification(View view) {
////        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
////                .setDefaults(NotificationCompat.DEFAULT_ALL)
////                .setSmallIcon(R.drawable.ic_account)
////                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_waktu))
////                .setContentTitle("Notification")
////                .setContentText("blabjbjasbjajsabj");
////
////        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
////        notificationManager.notify(1, builder.build());
//    }

}
