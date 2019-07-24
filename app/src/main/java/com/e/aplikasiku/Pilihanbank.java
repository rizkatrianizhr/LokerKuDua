package com.e.aplikasiku;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.Button;

public class Pilihanbank extends AppCompatActivity {

    private Button btnBank, Bca, Bri, Mandiri, Bni, Upload;
    private CardView cvBCA, cvBRI, cvMandiri, cvBNI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihanbank);

        btnBank = (Button) findViewById(R.id.btnbank);
        Bca = (Button) findViewById(R.id.bca);
        Bri = (Button)  findViewById(R.id.bri);
        Upload = (Button) findViewById(R.id.upload);
        Mandiri = (Button) findViewById(R.id.mandiri);
        Bni = ( Button) findViewById(R.id.bni);
        cvBCA = (CardView) findViewById(R.id.cvbca);
        cvBNI = (CardView) findViewById(R.id.cvbni);
        cvMandiri = (CardView) findViewById(R.id.cvmandiri);
        cvBRI = (CardView) findViewById(R.id.cvbri);
//
        Bca.setVisibility(View.GONE);
        Bni.setVisibility(View.GONE);
        Bri.setVisibility(View.GONE);
        Mandiri.setVisibility(View.GONE);
        cvBRI.setVisibility(View.GONE);
        cvMandiri.setVisibility(View.GONE);
        cvBNI.setVisibility(View.GONE);
        cvBCA.setVisibility(View.GONE);

        btnBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bca.setVisibility(View.VISIBLE);
                Bri.setVisibility(View.VISIBLE);
                Mandiri.setVisibility(View.VISIBLE);
                Bni.setVisibility(View.VISIBLE);
            }
        });

        Bca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvBCA.setVisibility(View.VISIBLE);
                cvBRI.setVisibility(View.GONE);
                cvBNI.setVisibility(View.GONE);
                cvMandiri.setVisibility(View.GONE);

            }
        });
        Bri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvBRI.setVisibility(View.VISIBLE);
                cvBCA.setVisibility(View.GONE);
                cvMandiri.setVisibility(View.GONE);
                cvBNI.setVisibility(View.GONE);
            }
        });
        Mandiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvMandiri.setVisibility(View.VISIBLE);
                cvBCA.setVisibility(View.GONE);
                cvBNI.setVisibility(View.GONE);
                cvBRI.setVisibility(View.GONE);

            }
        });
        Bni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvBNI.setVisibility(View.VISIBLE);
                cvBCA.setVisibility(View.GONE);
                cvBRI.setVisibility(View.GONE);
                cvMandiri.setVisibility(View.GONE);
            }
        });
        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Pilihanbank.this, Uploadbukti.class));
            }
        });

    }
}
