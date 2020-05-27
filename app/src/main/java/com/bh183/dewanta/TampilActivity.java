package com.bh183.dewanta;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TampilActivity extends AppCompatActivity {

    private ImageView imgCover;
    private TextView tvJudul, tvRilis, tvGenre, tvDurasi, tvRating, tvSynopsis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil);

        imgCover = findViewById(R.id.iv_film);
        tvJudul = findViewById(R.id.tv_judul_film);
        tvRilis = findViewById(R.id.tv_tgl_rilis);
        tvGenre = findViewById(R.id.tv_genre);
        tvDurasi = findViewById(R.id.tv_durasi);
        tvRating = findViewById(R.id.tv_rating);
        tvSynopsis = findViewById(R.id.tv_synopsis);

        Intent terimaData = getIntent();
        tvJudul.setText(terimaData.getStringExtra("JUDUL"));
        tvRilis.setText(terimaData.getStringExtra("RILIS"));
        tvGenre.setText(terimaData.getStringExtra("GENRE"));
        tvDurasi.setText(terimaData.getStringExtra("DURASI"));
        tvRating.setText(terimaData.getStringExtra("RATING"));
        tvSynopsis.setText(terimaData.getStringExtra("SYNOPSIS"));
        String imgLocation = terimaData.getStringExtra("COVER");

        try {
            File file = new File(imgLocation);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            imgCover.setImageBitmap(bitmap);
            imgCover.setContentDescription(imgLocation);
        } catch (FileNotFoundException er) {
            er.printStackTrace();
            Toast.makeText(this, "Gagal dalam mengambil gambar", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tampil_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}

