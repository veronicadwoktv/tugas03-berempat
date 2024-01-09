package com.ayyalls.firebaseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ayyalls.firebaseapp.adapter.AdapterBarangRecyclerView;
import com.ayyalls.firebaseapp.model.Barang;
import java.util.ArrayList;

public class ReadActivity extends AppCompatActivity implements
        AdapterBarangRecyclerView.FirebaseDataListener {
    /**
     * Mendefinisikan variable yang akan dipakai
     */
    private DatabaseReference database;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Barang> daftarBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
/**
 * Inisialisasi RecyclerView & komponennya
 */
        rvView = (RecyclerView) findViewById(R.id.rv_main);
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);
/**
 * Inisialisasi dan mengambil Firebase Database Reference
 */
        database = FirebaseDatabase.getInstance().getReference();
/**
 * Mengambil data barang dari Firebase Realtime DB
 */
        database.child("barang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
/**
 * Saat ada data baru, masukkan datanya ke ArrayList
 */
                daftarBarang = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
/**
 * Mapping data pada DataSnapshot ke dalam object Barang
 * Dan juga menyimpan primary key pada object Barang
 * untuk keperluan Edit dan Delete data

 */
                    Barang barang = noteDataSnapshot.getValue(Barang.class);
                    barang.setKey(noteDataSnapshot.getKey());
/**
 * Menambahkan object Barang yang sudah dimapping
 * ke dalam ArrayList
 */
                    daftarBarang.add(barang);
                }
/**
 * Inisialisasi adapter dan data barang dalam bentuk ArrayList
 * dan mengeset Adapter ke dalam RecyclerView
 */
                adapter = new AdapterBarangRecyclerView(daftarBarang,
                        ReadActivity.this);
                rvView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
/**
 * Kode ini akan dipanggil ketika ada error dan
 * pengambilan data gagal dan memprint error nya
 * ke LogCat
 */
                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());
            }
        });
    }

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, ReadActivity.class);
    }
// bewok
    @Override
    public void onDeleteData(Barang barang, final int position) {

/**
 * Kode ini akan dipanggil ketika method onDeleteData
 * dipanggil dari adapter lewat interface.
 * Yang kemudian akan mendelete data di Firebase Realtime DB
 * berdasarkan key barang.
 * Jika sukses akan memunculkan Toast
 */
        if (database != null) {
            database.child("barang").child(barang.getKey()).removeValue().addOnSuccessListener
                    (new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ReadActivity.this, "success delete",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
}