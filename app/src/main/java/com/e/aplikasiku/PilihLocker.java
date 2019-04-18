package com.e.aplikasiku;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PilihLocker extends AppCompatActivity {

    private Button Small, Medium, Large, smallsetjam, smallsejam, smalldua, smalltiga, smallempat, smallima, smallenam, smalltujuh, smallapan
            , smallsem, mediumsejam, mediumdua, mediumtiga, mediumempat, mediumlima, mediumenam, mediumtujuh, mediumlapan
            , mediumsem, largesem, largesejam, largedua, largetiga, largeempat, largelima, largeenam, largetujuh, largelapan;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase userDatabase;
    private FirebaseUser user;
    private CardView smallcv, mediumcv, largecv;
    String iduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        userDatabase = FirebaseDatabase.getInstance();
        databaseReference = userDatabase.getReference();
        setContentView(R.layout.pilihlocker);
        smallcv = (CardView)findViewById(R.id.cardviewsmall);
        smallsejam = (Button)findViewById(R.id.sejamsmall);
        smalldua = (Button)findViewById(R.id.duajamsmall);
        smalltiga = (Button)findViewById(R.id.tigajamsmall);
        smallempat = (Button)findViewById(R.id.empatjamsmall);
        smallima = (Button)findViewById(R.id.limajamsmall);
        smallenam = (Button)findViewById(R.id.enamjamsmall);
        smalltujuh = (Button)findViewById(R.id.tujuhjamsmall);
        smallapan = (Button)findViewById(R.id.lapanjamsmall);
        smallsem = (Button) findViewById(R.id.semjamsmall);

        mediumcv = (CardView)findViewById(R.id.cardviewmedium);
        mediumsejam = (Button)findViewById(R.id.sejammedium);
        mediumdua = (Button)findViewById(R.id.duajammedium);
        mediumtiga = (Button)findViewById(R.id.tigajammedium);
        mediumempat = (Button)findViewById(R.id.empatjammedium);
        mediumlima = (Button)findViewById(R.id.limajammedium);
        mediumenam = (Button)findViewById(R.id.enamjammedium);
        mediumtujuh = (Button)findViewById(R.id.tujuhjammedium);
        mediumlapan = (Button)findViewById(R.id.lapanjammedium);
        mediumsem = (Button) findViewById(R.id.semjammedium);


        largecv = (CardView)findViewById(R.id.cardviewlarge);
        largesejam = (Button)findViewById(R.id.sejamlarge);
        largedua = (Button)findViewById(R.id.duajamlarge);
        largetiga = (Button)findViewById(R.id.tigajamlarge);
        largeempat = (Button)findViewById(R.id.empatjamlarge);
        largelima = (Button)findViewById(R.id.limajamlarge);
        largeenam = (Button)findViewById(R.id.enamjamlarge);
        largetujuh = (Button)findViewById(R.id.tujuhjamlarge);
        largelapan = (Button)findViewById(R.id.lapanjamlarge);
        largesem = (Button) findViewById(R.id.semjamlarge);

        Small = (Button) findViewById(R.id.btnSmall);
        Large = (Button) findViewById(R.id.btnLarge);
        Medium = (Button) findViewById(R.id.btnMedium);
        smallcv.setVisibility(View.GONE);

        smallsejam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    FirebaseUser user = auth.getCurrentUser();
                    iduser = user.getUid();
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Small");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("1 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(8000);
                    databaseReference.child(iduser).child("chart").child("SisaWaktu").setValue("01:00:00");
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });
        smalldua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    FirebaseUser user = auth.getCurrentUser();
                    iduser = user.getUid();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
                    String currentDateandTime = sdf.format(new Date());

                    Date date = null;
                    try {
                        date = sdf.parse(currentDateandTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.HOUR, 2);

                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Small");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("2 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(15000);
                    databaseReference.child(iduser).child("chart").child("SisaWaktu").setValue(calendar.getTime().toString());
                    Log.d("DEBUG", "Lempar");
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });
        smalltiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    FirebaseUser user = auth.getCurrentUser();
                    iduser = user.getUid();
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Small");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("3 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(22000);
                    databaseReference.child(iduser).child("chart").child("SisaWaktu").setValue("03:00:00");
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });

        smallempat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    FirebaseUser user = auth.getCurrentUser();
                    iduser = user.getUid();
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Small");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("4 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(29000);
                    databaseReference.child(iduser).child("chart").child("SisaWaktu").setValue("04:00:00");
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });
        smallima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    FirebaseUser user = auth.getCurrentUser();
                    iduser = user.getUid();
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("kecil");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("5 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(36000);
                    databaseReference.child(iduser).child("chart").child("SisaWaktu").setValue("05:00:00");
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });
        smallenam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    FirebaseUser user = auth.getCurrentUser();
                    iduser = user.getUid();
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Small");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("6 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(43000);
                    databaseReference.child(iduser).child("chart").child("SisaWaktu").setValue("06:00:00");
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });
        smalltujuh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Small");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("7 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(50000);
                    databaseReference.child(iduser).child("chart").child("SisaWaktu").setValue("07:00:00");
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });

        smallapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Small");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("8 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(57000);
                    databaseReference.child(iduser).child("chart").child("SisaWaktu").setValue("08:00:00");
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });

        smallsem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Small");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("9 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(64000);
                    databaseReference.child(iduser).child("chart").child("SisaWaktu").setValue("09:00:00");
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });

        Small.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View v){
                smallcv.setVisibility(View.VISIBLE);
                mediumcv.setVisibility(View.GONE);
                largecv.setVisibility(View.GONE);
                Toast.makeText(PilihLocker.this, "Pilih durasi penyewaan", Toast.LENGTH_SHORT).show();

            }
        });

        mediumcv.setVisibility(View.GONE);

        mediumsejam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    FirebaseUser user = auth.getCurrentUser();
                    iduser = user.getUid();
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Medium");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("1 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(10000);
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });
        mediumdua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    FirebaseUser user = auth.getCurrentUser();
                    iduser = user.getUid();
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Medium");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("2 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(17000);
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });
        mediumtiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    FirebaseUser user = auth.getCurrentUser();
                    iduser = user.getUid();
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Medium");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("3 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(24000);
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });
        mediumempat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    FirebaseUser user = auth.getCurrentUser();
                    iduser = user.getUid();
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Medium");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("4 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(31000);
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });
        mediumlima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    FirebaseUser user = auth.getCurrentUser();
                    iduser = user.getUid();
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Medium");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("5 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(38000);
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });
        mediumenam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    FirebaseUser user = auth.getCurrentUser();
                    iduser = user.getUid();
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Medium");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("6 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(45000);
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });
        mediumtujuh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    FirebaseUser user = auth.getCurrentUser();
                    iduser = user.getUid();
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Medium");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("7 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(52000);
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });

        mediumlapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Medium");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("8 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(58000);
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });

        mediumsem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Medium");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("9 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(65000);
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });


        Medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediumcv.setVisibility(View.VISIBLE);
                smallcv.setVisibility(View.GONE);
                largecv.setVisibility(View.GONE);
                Toast.makeText(PilihLocker.this, "Pilih durasi penyewaan", Toast.LENGTH_SHORT).show();

            }
        });

        largecv.setVisibility(View.GONE);

        largesejam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    FirebaseUser user = auth.getCurrentUser();
                    iduser = user.getUid();
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Large");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("1 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(12000);
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });
        largedua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    FirebaseUser user = auth.getCurrentUser();
                    iduser = user.getUid();
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Large");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("2 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(20000);
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });
        largetiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    FirebaseUser user = auth.getCurrentUser();
                    iduser = user.getUid();
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Large");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("3 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(28000);
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });
        largeempat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    FirebaseUser user = auth.getCurrentUser();
                    iduser = user.getUid();
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Large");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("4 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(36000);
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });
        largelima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    FirebaseUser user = auth.getCurrentUser();
                    iduser = user.getUid();
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Large");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("5 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(43000);
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });
        largeenam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    FirebaseUser user = auth.getCurrentUser();
                    iduser = user.getUid();
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Large");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("6 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(51000);
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });
        largetujuh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    FirebaseUser user = auth.getCurrentUser();
                    iduser = user.getUid();
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Large");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("7 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(59000);
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });

        largelapan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Large");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("8 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(67000);
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });

        largesem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auth.getCurrentUser() == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PilihLocker.this);
                    builder.setMessage("Silahkan Login terlebih dahulu!");
                    builder.setNeutralButton("tidak", null);
                    builder.setPositiveButton("iya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(PilihLocker.this, Login.class));
                        }
                    });
                    builder.create().show();
                }
                if (auth.getCurrentUser() != null) {
                    databaseReference.child(iduser).child("chart").child("Ukuran").setValue("Large");
                    databaseReference.child(iduser).child("chart").child("Durasi").setValue("9 Jam");
                    databaseReference.child(iduser).child("chart").child("Harga").setValue(75000);
                    startActivity(new Intent(PilihLocker.this, KonfirmasiPesanan.class));
                }
            }
        });

        Large.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                largecv.setVisibility(View.VISIBLE);
                smallcv.setVisibility(View.GONE);
                mediumcv.setVisibility(View.GONE);
                Toast.makeText(PilihLocker.this, "Pilih durasi penyewaan", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
