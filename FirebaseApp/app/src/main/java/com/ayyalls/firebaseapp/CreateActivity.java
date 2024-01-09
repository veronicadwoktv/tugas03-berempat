package com.ayyalls.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ayyalls.firebaseapp.model.Barang;

public class CreateActivity extends AppCompatActivity {
    // variable yang merefers ke Firebase Realtime Database
    private DatabaseReference database;
    // variable fields EditText dan Button
    private Button btSubmit;
    private EditText etNama;
    private EditText etMerk;
    private EditText etHarga;
    private Barang barang = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
// inisialisasi fields EditText dan Button
        etNama = (EditText) findViewById(R.id.et_namabarang);
        etMerk = (EditText) findViewById(R.id.et_merkbarang);
        etHarga = (EditText) findViewById(R.id.et_hargabarang);
        btSubmit = (Button) findViewById(R.id.bt_submit);
// mengambil referensi ke Firebase Database
        database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-app-a52a8-default-rtdb.firebaseio.com/");
                barang = (Barang) getIntent().getSerializableExtra("data");
        if (barang != null) {
            etNama.setText(barang.getNama());
            etMerk.setText(barang.getMerk());
            etHarga.setText(barang.getHarga());
        }
    }
    public void save(View v) {
        if (barang != null) {
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    barang.setNama(etNama.getText().toString());
                    barang.setMerk(etMerk.getText().toString());
                    barang.setHarga(etHarga.getText().toString());
                    updateBarang(barang);
                }
            });
        } else {
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!isEmpty(etNama.getText().toString()) &&
                            !isEmpty(etMerk.getText().toString()) && !isEmpty(etHarga.getText().toString()))
                        submitBarang(new Barang(etNama.getText().toString(),
                                etMerk.getText().toString(), etHarga.getText().toString()));
                    else
                        Snackbar.make(findViewById(R.id.bt_submit), "Data barang tidak boleh kosong", Snackbar.LENGTH_LONG).show();
                                InputMethodManager imm = (InputMethodManager)
                                        getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(
                            etNama.getWindowToken(), 0);
                }
            });
        }
    }
    private boolean isEmpty(String s) {
// Cek apakah ada fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }
    private void updateBarang(Barang barang) {
/**
 * Baris kode yang digunakan untuk mengupdate data barang
 * yang sudah dimasukkan di Firebase Realtime Database
 */
        database.child("barang") //akses parent index, ibaratnya seperti nama tabel
                .child(barang.getKey()) //select barang berdasarkan key
                .setValue(barang) //set value barang yang baru
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override

                    public void onSuccess(Void aVoid) {

/**
 * Baris kode yang akan dipanggil apabila proses update
 barang sukses
 */
                        Snackbar.make(findViewById(R.id.bt_submit), "Data berhasil diupdatekan", Snackbar.LENGTH_LONG).setAction("Oke", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                        }).show();
                    }
                });
    }
    private void submitBarang(Barang barang) {
/**
 * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase
 Realtime Database
 * dan juga kita set onSuccessListener yang berisi kode yang akan
 dijalankan
 * ketika data berhasil ditambahkan
 */
        database.child("barang").push().setValue(barang).addOnSuccessListener(this, new
                OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        etNama.setText("");
                        etMerk.setText("");
                        etHarga.setText("");
                        Snackbar.make(findViewById(R.id.bt_submit), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
                    }
                });
    }
    public static Intent getActIntent(Activity activity) {
// kode untuk pengambilan Intent
        return new Intent(activity, CreateActivity.class);
    }
}