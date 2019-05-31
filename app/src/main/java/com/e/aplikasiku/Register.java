package com.e.aplikasiku;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {


    private EditText Emaill, Pass, Names, confPass, Nohp;
    private Button btnregis, Back;
    private TextView TextLogin;
    private CheckBox Unhide;
    private ProgressBar Progressbar;
    private FirebaseAuth auth;

    private DatabaseReference databaseReference;
    private FirebaseDatabase userDatabase;
    private FirebaseUser user;
    String iduser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Progressbar = (ProgressBar) findViewById(R.id.progressbar);
        Progressbar.setVisibility(View.GONE);
        userDatabase = FirebaseDatabase.getInstance();
        databaseReference = userDatabase.getReference();
        auth = FirebaseAuth.getInstance();
        Names= (EditText) findViewById(R.id.name);
        Emaill= (EditText) findViewById(R.id.email);
        Pass = (EditText) findViewById(R.id.pass);
        confPass = (EditText) findViewById(R.id.C_pass);
        btnregis= (Button) findViewById(R.id.btn_regis);
        Back = (Button) findViewById(R.id.btnBack);
        Unhide = (CheckBox) findViewById(R.id.unhide);
        TextLogin = (TextView) findViewById(R.id.textLogin);
        Nohp = (EditText) findViewById(R.id.nohp);


        TextLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });

        Unhide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (b) {
                    Pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    Pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }

        });

        btnregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String namee = Names.getText().toString().trim();
                final String email = Emaill.getText().toString().trim();
                String pass = Pass.getText().toString().trim();
                String C_pass = confPass.getText().toString().trim();
                final String nohp = Nohp.getText().toString().trim();

                if (TextUtils.isEmpty(namee)) {
                    Toast.makeText(getApplicationContext(), "Input Username!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Input Your Email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(nohp)) {
                    Toast.makeText(getApplicationContext(), "Input Your Handphone!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(getApplicationContext(), "Make a Password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Minimum Password of 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(C_pass)) {
                    Toast.makeText(getApplicationContext(), "Confirmation Password", Toast.LENGTH_SHORT).show();

                    if (pass != C_pass) {
                        Toast.makeText(Register.this, "Password Not Match!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
//
                Progressbar.setVisibility(View.VISIBLE);

                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = auth.getCurrentUser();
                                    Intent intent = new Intent(Register.this, Login.class);
                                    databaseReference.child("users").child(auth.getUid()).child("name").setValue(namee);
                                    databaseReference.child("users").child(auth.getUid()).child("telepon").setValue(nohp);
                                    databaseReference.child("users").child(auth.getUid()).child("email").setValue(email);
                                    databaseReference.child("users").child(auth.getUid()).child("balance").setValue("0");
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Register.this, "Account Registration Failed : " +  task.getException(),
                                            Toast.LENGTH_LONG).show();
                                }
                                Progressbar.setVisibility(View.GONE);
                            }
                });
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Register.this, Login.class));
    }
}

//                        Toast.makeText(Register.this, "createUserWithEmail:onComplete;" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
//                        Progressbar.setVisibility(View.GONE);

//                    if (!task.isSuccessful()) {
//                        Toast.makeText(Register.this, "Gagal!," + task.getException(),
//                                Toast.LENGTH_SHORT).show();
//                    } else {
//                        databaseReference.child("users").child(auth.getUid()).child("name").setValue(namee);
//                        databaseReference.child("users").child(auth.getUid()).child("telepon").setValue(nohp);
//                        databaseReference.child("users").child(auth.getUid()).child("email").setValue(email);
//                        databaseReference.child("users").child(auth.getUid()).child("balance").setValue("0");
//                        startActivity(new Intent(Register.this, Login.class));
//                        finish();
//                    }
//
//
//                }
//            });
//        }
//
//    });
//}
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//       Progressbar.setVisibility(View.GONE);
//
//    }
//}
