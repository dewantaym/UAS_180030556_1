package com.bh183.dewanta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Adapter extends RecyclerView.Adapter<Adapter.FilmViewHolder> {

    private Context context;
    private ArrayList<Film> dataFilm;


    public Adapter(Context context, ArrayList<Film> dataFilm) {
        this.context = context;
        this.dataFilm = dataFilm;
    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_film, parent, false);
        return new FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
        Film tempFilm = dataFilm.get(position);
        holder.idFilm = tempFilm.getIdFilm();
        holder.judul.setText(tempFilm.getJudul());
        holder.synopsis.setText(tempFilm.getSynopsis());
        holder.rilis.setText(tempFilm.getRilis());
        holder.cover = tempFilm.getCover();
        holder.genre.setText(tempFilm.getGenre());
        holder.durasi.setText(tempFilm.getDurasi());
        holder.rating.setText(tempFilm.getRating());

        try {
            File file = new File(holder.cover);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            holder.imgCover.setImageBitmap(bitmap);
            holder.imgCover.setContentDescription(holder.cover);
        } catch (FileNotFoundException er){
            er.printStackTrace();
            Toast.makeText(context, "Gagal mengambil gambar dalam media penyimpanan", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {

        return dataFilm.size();
    }

    public class FilmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public int idFilm;
        private ImageView imgCover;
        private TextView judul;
        private TextView synopsis;
        private TextView genre;
        private TextView durasi;
        private TextView rating;
        private TextView rilis;
        private String cover;

        public FilmViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCover = itemView.findViewById(R.id.iv_film);
            judul = itemView.findViewById(R.id.tv_judul_film);
            rilis = itemView.findViewById(R.id.tv_tgl_rilis);
            genre = itemView.findViewById(R.id.tv_genre);
            durasi = itemView.findViewById(R.id.tv_durasi);
            rating = itemView.findViewById(R.id.tv_rating);
            synopsis = itemView.findViewById(R.id.tv_synopsis);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent bukaFilm = new Intent(context, TampilActivity.class);
            bukaFilm.putExtra("ID", idFilm);
            bukaFilm.putExtra("JUDUL", judul.getText().toString());
            bukaFilm.putExtra("RILIS", rilis.getText().toString());;
            bukaFilm.putExtra("COVER", cover);
            bukaFilm.putExtra("GENRE", genre.getText().toString());
            bukaFilm.putExtra("DURASI", durasi.getText().toString());
            bukaFilm.putExtra("RATING", rating.getText().toString());
            bukaFilm.putExtra("SYNOPSIS", synopsis.getText().toString());
            context.startActivity(bukaFilm);
        }

        @Override
        public boolean onLongClick(View v) {

            Intent bukaInput = new Intent(context, InputActivity.class);
            bukaInput.putExtra("OPERASI", "update");
            bukaInput.putExtra("ID", idFilm);
            bukaInput.putExtra("JUDUL", judul.getText().toString());
            bukaInput.putExtra("RILIS", rilis.getText().toString());;
            bukaInput.putExtra("COVER", cover);
            bukaInput.putExtra("GENRE", genre.getText().toString());
            bukaInput.putExtra("DURASI", durasi.getText().toString());
            bukaInput.putExtra("RATING", rating.getText().toString());
            bukaInput.putExtra("SYNOPSIS", synopsis.getText().toString());
            context.startActivity(bukaInput);
            return true;
        }
    }
}
