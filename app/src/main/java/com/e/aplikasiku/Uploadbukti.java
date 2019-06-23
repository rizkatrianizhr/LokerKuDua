package com.e.aplikasiku;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.flags.impl.DataUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class Uploadbukti extends AppCompatActivity {

    private ImageView Image;
    private Button Upload, Back;

    private static final int CAMERA_REQUEST_CODE = 1;
    private Uri uri;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String idusernya;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef;


    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadbukti);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        idusernya = user.getUid();
        storageRef = storage.getReferenceFromUrl("gs://locker-96b1e.appspot.com");

        Image = (ImageView) findViewById(R.id.image);
//        Choose = (Button) findViewById(R.id.choose);
        Upload = (Button) findViewById(R.id.upload);
        Back = (Button) findViewById(R.id.btnBack);
        dialog = new ProgressDialog(this);

//        Image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, CAMERA_REQUEST_CODE);
//
//            }
//        });

    }
    //
    public void handleChooseImage(View view) {
        Intent pickerPhotoIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickerPhotoIntent, 1);
    }


    public void handleInsertData(View view) {
//        dialog.setMessage("Uploading Image....");
//        dialog.show();
        if (this.Image.getDrawable() == null) {
            //Image is blank
            Toast.makeText(this, "You must select image !", Toast.LENGTH_SHORT);
            return;
        }
        //Insert data to Firebase Storage


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {


            case 0:
                if (requestCode == RESULT_OK) {
                    Log.i("Uploadbukti", "case 0");
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    Log.i("Uploadbukti", "selected Image = " + selectedImage);
                    this.Image.setImageURI(selectedImage);
                    dialog.setMessage("Uploading Image....");
                    dialog.show();
                    this.uploadImageToFirebase();
//                    dialog.dismiss();
                }
                break;
        }
    }

    private void uploadImageToFirebase() {
        // Get the data from an ImageView as bytes
        this.Image.setDrawingCacheEnabled(true);
        this.Image.buildDrawingCache();
        Bitmap bitmap = this.Image.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        StorageReference mountainsRef = storageRef.child("Payment");
        UploadTask uploadTask = mountainsRef.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.i("Uploadbukti", "Upload failed");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                dialog.dismiss();
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();
                Toast.makeText(Uploadbukti.this, "Uploading Finished....", Toast.LENGTH_LONG);
                Log.i("Uploadbukti", "Upload successful, downloadUrl = " + downloadUrl);
            }
        });
    }
}


//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK ) {
//
//            dialog.setMessage("Uploading Image....");
//            dialog.show();
//
//            uri = data.getData();
//
//            StorageReference filepath = storageRef.child("photos").child(uri.getLastPathSegment());
//
//            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                    dialog.dismiss();
//
////                    Uri downloadUri = taskSnapshot.getDownloadUrl();
//                    Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
////                    Uri downloadUri = taskSnapshot.getUploadSessionUri();
//                    Picasso.with(Uploadbukti.this).load(String.valueOf(uri)).fit().centerCrop().into(Image);
//
//                    Toast.makeText(Uploadbukti.this, "Uploading Finished....", Toast.LENGTH_LONG);
//                }
//            });
//        }
//    }
//}