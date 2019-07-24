package com.e.aplikasiku;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class Uploadbukti extends AppCompatActivity {

    private ImageView Image;
    private Button Upload, Back, Choose;
    private TextView Text;
    private Uri filePath;

//    private static final int CAMERA_REQUEST_CODE = 1;
//    private static final int ImageBack= 1;

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
        Text = (TextView) findViewById(R.id.textchoose);
//        Choose = (Button) findViewById(R.id.choose);
        Upload = (Button) findViewById(R.id.upload);
        Choose = (Button) findViewById(R.id.choose);
        Back = (Button) findViewById(R.id.btnBack);
        dialog = new ProgressDialog(this);


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChooseImage();
            }
        });

        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImage();
            }
        });

    }

    public void ChooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent, "ImageBack"), 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Text.setVisibility(View.GONE);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void uploadImage() {

        if (filePath != null) {
            dialog.setTitle("Uploading...");
            dialog.show();
            final StorageReference reference = storageRef.child(UUID.randomUUID().toString());

            reference.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            dialog.dismiss();
                            Toast.makeText(Uploadbukti.this, "Images Uploaded", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            dialog.setMessage("Uploaded..." + (int) progress + "%");



                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

//                                    String image = uri.toString();
//                                    reference.child("users").child(idusernya).child("proof").child(image.cl)

                                    DatabaseReference imageStore = firebaseDatabase.getInstance().getReference().child("users").child(idusernya);


                                    HashMap<String,Object> hashMap = new HashMap<>();
                                    hashMap.put("proofTopup", String.valueOf(uri));

                                    imageStore.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(Uploadbukti.this, "Uploading Finished...", Toast.LENGTH_LONG);
//                                            startActivity(new Intent(Uploadbukti.this, finishTopup.class));
                                        }
                                    });
                                }
                            });
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    startActivity(new Intent(Uploadbukti.this, finishTopup.class));
                    Toast.makeText(Uploadbukti.this, "Uploading Finished" +
                            "After payment is confirmed, your balance will enter automatically", Toast.LENGTH_LONG);
                    finish();
                }
            });

        }
    }


}