package com.e.aplikasiku;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    private Button Login, Back;
    private TextView LupaPass, TextRegis;
    private CheckBox Showpass;
    private ProgressDialog dialog;
    private RelativeLayout Layout;
    private AnimationDrawable animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authentication = FirebaseAuth.getInstance();


        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        Login = (Button) findViewById(R.id.btn_login);
        LupaPass = (TextView) findViewById(R.id.lupaPass);
        TextRegis = (TextView) findViewById(R.id.textRegis);
        Showpass = (CheckBox) findViewById(R.id.show);
        Back = (Button) findViewById(R.id.btnBack);
        dialog = new ProgressDialog(this);

        Layout = (RelativeLayout) findViewById(R.id.layout);
        animation = (AnimationDrawable) Layout.getBackground();


        animation.setEnterFadeDuration(5000);
        animation.setExitFadeDuration(3000);

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

                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Input Your Email and Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Input Your Email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Input Your Password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.setMessage("Signing In...");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                authentication.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();

                        if (task.isSuccessful()) {
                            if (authentication.getCurrentUser().isEmailVerified()) {
                                Toast.makeText(getApplicationContext(), "User found and now signed", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(com.e.aplikasiku.Login.this, LamanUtama.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Please verify your email address", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

        });

    }
    @Override
    protected void onResume() {
        super.onResume();

        if (animation!= null && !animation.isRunning()){
            animation.start();
        }
    }


}
