package com.ayyalls.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button btCreateDB;
    private Button btViewDB;

    private Button btLogout;
    private TextView tvUser;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener fStateListener;
    private static final String TAG = LoginActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btCreateDB = (Button) findViewById(R.id.bt_createdata);
        btViewDB = (Button) findViewById(R.id.bt_viewdata);
        btLogout = (Button)findViewById(R.id.bt_logout);
        tvUser = (TextView) findViewById(R.id.tv_user);
        fAuth = FirebaseAuth.getInstance();
/**
 * Cek apakah ada user yang sudah login
 */
        fStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
// User sedang login

                    Log.v(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
/**
 * Method ini akan dipanggil apabila user berhasil logout

 */

                    Toast.makeText(MainActivity.this, "User Logout\n",
                            Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "onAuthStateChanged:signed_out");
                    startActivity(new Intent(MainActivity.this,
                            LoginActivity.class));
                    finish();
                }
            }
        };
        checkLogin();
    }
    public void tambah(View v) {
// kelas yang akan dijalankan ketika tombol Create/Insert Data diklik
        startActivity(CreateActivity.getActIntent(MainActivity.this));
    }
    public void lihat(View v) {
        startActivity(ReadActivity.getActIntent(MainActivity.this));
    }
    public void logout(View v) {
        fAuth.signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
    private void checkLogin(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            tvUser.setText("Welcome, " + email);
        }
    }
}