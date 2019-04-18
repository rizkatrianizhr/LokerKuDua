package com.e.aplikasiku;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GantiPassword extends AppCompatActivity {

    private EditText Pass, Repass;
    private Button Ubah;
    private ProgressBar Probar;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase userDatabase;
    private FirebaseUser user;
    String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganti_password);
        Probar = (ProgressBar) findViewById(R.id.probar);
        Probar.setVisibility(View.GONE);
        userDatabase = FirebaseDatabase.getInstance();
        databaseReference = userDatabase.getReference();
        auth = FirebaseAuth.getInstance();

        Pass = (EditText) findViewById(R.id.pass);
        Repass = (EditText) findViewById(R.id.repass);
        Ubah = (Button) findViewById(R.id.ubah);

        Ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null && !Pass.getText().toString().trim().equals("")) {
                    String pass = Pass.getText().toString();
                    String repas = Repass.getText().toString();

                    if (TextUtils.isEmpty(pass)) {
                        Pass.setError("Masukan Password");
                    }
                    if (TextUtils.isEmpty(repas)) {
                        Repass.setError("Konfirmasi Password Baru");
                        if (!pass.equals(repas)) {
                            Pass.setError("Password tidak sama");
                            Repass.setError("Password tidak sama");
                        }
                        if (Pass.getText().toString().trim().length() < 6) {
                            Pass.setError("Password minimum 6 karakter!");
                        } else if
                        (!TextUtils.isEmpty(pass) && !TextUtils.isEmpty(repas) && pass.equals(repas)) {
                            user.updatePassword(Pass.getText().toString().trim())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(GantiPassword.this, "Password berhasil diubah", Toast.LENGTH_SHORT).show();
                                                auth.signOut();
                                                startActivity(new Intent(GantiPassword.this, Login.class));
                                            } else {
                                                Toast.makeText(GantiPassword.this, "Permintaan gagal, coba lagi!", Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                    });

                        }

                    } else if (Pass.getText().toString().trim().equals("")) {
                        Pass.setError("Masukan Password");
                    }
                }

            }
        });
    }
}