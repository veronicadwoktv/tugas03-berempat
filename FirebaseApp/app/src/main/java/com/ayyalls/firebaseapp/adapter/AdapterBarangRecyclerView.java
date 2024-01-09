package com.ayyalls.firebaseapp.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.ayyalls.firebaseapp.CreateActivity;
import com.ayyalls.firebaseapp.R;
import com.ayyalls.firebaseapp.ReadActivity;
import com.ayyalls.firebaseapp.model.Barang;
import java.util.ArrayList;
public class AdapterBarangRecyclerView extends
        RecyclerView.Adapter<AdapterBarangRecyclerView.ViewHolder> {
    private ArrayList<Barang> daftarBarang;
    private Context context;
    FirebaseDataListener listener;
    public AdapterBarangRecyclerView(ArrayList<Barang> barangs, Context ctx){
        /**
         * Inisiasi data dan variabel yang akan digunakan
         */
        daftarBarang = barangs;
        context = ctx;
        listener = (ReadActivity)ctx;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * Inisiasi View
         * Di tutorial ini kita hanya menggunakan data String untuk tiap item
         * dan juga view nya hanyalah satu TextView
         */
        TextView tvTitle;
        ViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tv_namabarang);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
/**
 * Inisiasi ViewHolder
 */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent,
                        false);
// mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
/**
 * Menampilkan data pada view
 */
        final String name = daftarBarang.get(position).getNama();
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/**
 * Kodingan detail data
 */
            }
        });
        holder.tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
/**
 * Kodingan Delete dan update data
 */
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_view);
                dialog.show();
                Button editButton = (Button)
                        dialog.findViewById(R.id.bt_edit_data);
                Button delButton = (Button)
                        dialog.findViewById(R.id.bt_delete_data);
//apabila tombol edit diklik
                editButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override

                            public void onClick(View view) {

                                dialog.dismiss();
                                context.startActivity(CreateActivity.getActIntent((Activity)
                                        context).putExtra("data", daftarBarang.get(position)));
                            }
                        }
                );
//apabila tombol delete diklik
                delButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override

                            public void onClick(View view) {

/**
 * Kodingan Delete data

 */
                                dialog.dismiss();

                                listener.onDeleteData(daftarBarang.get(position),

                                        position);
                            }
                        }
                );
                return true;
            }
        });
        holder.tvTitle.setText(name);
    }
    @Override
    public int getItemCount() {
/**
 * Mengembalikan jumlah item pada barang
 */
        return daftarBarang.size();
    }
    public interface FirebaseDataListener{
        void onDeleteData(Barang barang, int position);
    }

}