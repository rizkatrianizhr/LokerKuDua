package com.e.aplikasiku;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class resetPass extends AppCompatActivity {

    private EditText Email;
    private ProgressBar Bar;
    private Button btnReset;
    private TextView btnBack;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        Email = (EditText) findViewById(R.id.emailmu);
        btnReset = (Button) findViewById(R.id.reset);
        btnBack = (TextView) findViewById(R.id.btn_back);
        Bar = (ProgressBar) findViewById(R.id.probar);

        Bar.setVisibility(View.GONE);

        auth = FirebaseAuth.getInstance();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = Email.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Masukan Email anda yang sudah terdaftar!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Bar.setVisibility(View.VISIBLE);

                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(resetPass.this, "Cek Email untuk merest password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(resetPass.this, "Gagal mengirim ke email!", Toast.LENGTH_SHORT).show();
                                }

                                Bar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }

}

