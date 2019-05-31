package com.e.aplikasiku;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity {

    private FirebaseAuth authentication;
    private EditText Email, Password;
    private Button Login;
    private Button Back;
    private TextView LupaPass, TextRegis;
    private CheckBox Showpass;
    private ProgressBar ProBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ProBar = (ProgressBar) findViewById(R.id.progressBar);
        ProBar.setVisibility(View.GONE);
        authentication = FirebaseAuth.getInstance();
        Email= (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        Login = (Button) findViewById(R.id.btn_login);
        LupaPass = (TextView) findViewById(R.id.lupaPass);
        TextRegis = (TextView) findViewById(R.id.textRegis);
        Back = (Button) findViewById(R.id.btnBack);
        Showpass = (CheckBox) findViewById(R.id.show);
        LupaPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, resetPass.class));
            }
        });

        TextRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
        Showpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (b) {
                    Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }

        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                final String password = Password.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Input Your Email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Input Your Password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ProBar.setVisibility(View.VISIBLE);

                authentication.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                ProBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {

                                    FirebaseUser user = authentication.getCurrentUser();
                                    Intent intent = new Intent(com.e.aplikasiku.Login.this, LamanUtama.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(com.e.aplikasiku.Login.this, "Login Filed! : " + task.getException(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

////                            if (password.length() < 6) {
////                                Password.setError(getString(R.string.minimum_password));
////                            } else {
////                                Toast.makeText(Login.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
////                            }
////                        } else {
//                            Intent intent = new Intent(Login.this, LamanUtama.class);
//                            startActivity(intent);
//                            finish();
////                        }
////                    }
//                });
            }

        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, LamanUtama.class));
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Login.this, LamanUtama.class));
    }

}
