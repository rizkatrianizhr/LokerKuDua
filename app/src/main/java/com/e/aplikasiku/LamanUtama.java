package com.e.aplikasiku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LamanUtama extends AppCompatActivity {

    private Button topUp, Pesan, cek, profil, bantuan;
    private TextView nama, uang;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laman_utama);

        profil = (Button)findViewById(R.id.profile);
        Pesan = (Button) findViewById(R.id.order);
        topUp = (Button) findViewById(R.id.topup);
        bantuan = (Button) findViewById(R.id.help);
        nama = (TextView) findViewById(R.id.names);
        uang = (TextView) findViewById(R.id.balance);
    }
}
