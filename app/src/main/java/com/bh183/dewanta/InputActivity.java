package com.bh183.dewanta;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class InputActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editJudul, editRilis, editGenre, editDurasi, editRating, editSynopsis;
    private ImageView ivFilm;
    private DatabaseHandler dbHandler;

    private boolean updateData = false;
    private int idFilm = 0;
    private Button btnSimpan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        editJudul = findViewById(R.id.edit_judul_film);
        editRilis = findViewById(R.id.edit_rilis_date);
        editGenre = findViewById(R.id.edit_genre);
        editDurasi = findViewById(R.id.edit_durasi);
        editSynopsis = findViewById(R.id.edit_synopsis);
        editRating = findViewById(R.id.edit_rating);
        ivFilm = findViewById(R.id.iv_film);
        btnSimpan = findViewById(R.id.btn_simpan);

        dbHandler = new DatabaseHandler(this);

        Intent terimaIntent = getIntent();
        Bundle data = terimaIntent.getExtras();
        if(data.getString("OPERASI").equals("insert")){
            updateData = false;
        } else {
            updateData = true;
            idFilm = data.getInt("ID");
            editJudul.setText(data.getString("JUDUL"));
            editRilis.setText(data.getString("RILIS"));
            editGenre.setText(data.getString("GENRE"));
            editDurasi.setText(data.getString("DURASI"));
            editRating.setText(data.getString("RATING"));
            editSynopsis.setText(data.getString("SYNOPSIS"));
            loadImageFromInternalStorage(data.getString("COVER"));
        }

        ivFilm.setOnClickListener(this);
        btnSimpan.setOnClickListener(this);

    }

    private void pickImage() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(4,6)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                try{
                    Uri imageUri = result.getUri();
                    InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    String location = saveImageToInternalStorage(selectedImage, getApplicationContext());
                    loadImageFromInternalStorage(location);
                } catch (FileNotFoundException er){
                    er.printStackTrace();
                    Toast.makeText(this, "Ada kegagalan dalam mengambil file gambar", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "Anda belum memilih gambar", Toast.LENGTH_SHORT).show();
        }
    }

    public static String saveImageToInternalStorage(Bitmap bitmap, Context ctx){
        ContextWrapper ctxWrapper = new ContextWrapper(ctx);
        File file = ctxWrapper.getDir("images", MODE_PRIVATE);
        String uniqueID = UUID.randomUUID().toString();
        file = new File(file, "film."+ uniqueID + ".jpg");
        try {
            OutputStream stream = null;
            stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
        } catch (IOException er){
            er.printStackTrace();
        }

        Uri savedImage = Uri.parse(file.getAbsolutePath());
        return savedImage.toString();
    }

    private void loadImageFromInternalStorage(String imageLocation){
        try {
            File file = new File(imageLocation);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            ivFilm.setImageBitmap(bitmap);
            ivFilm.setContentDescription(imageLocation);
        } catch (FileNotFoundException er){
            er.printStackTrace();
            Toast.makeText(this, "Gagal dalam mengambil gambar", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem item = menu.findItem(R.id.item_menu_hapus);

        if(updateData==true){
            item.setEnabled(true);
            item.getIcon().setAlpha(255);
        } else {
            item.setEnabled(false);
            item.getIcon().setAlpha(130);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.input_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.item_menu_hapus){
            hapusData();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void simpanData(){
        String judul, cover, rilis, genre, durasi, rating, synopsis;

        judul = editJudul.getText().toString();
        cover = ivFilm.getContentDescription().toString();
        rilis = editRilis.getText().toString();
        genre = editGenre.getText().toString();
        durasi = editDurasi.getText().toString();
        rating = editRating.getText().toString();
        synopsis = editSynopsis.getText().toString();



        Film tempFilm = new Film(
                idFilm, judul, rilis, cover, genre, durasi, rating, synopsis
        );

        if (updateData == true){
            dbHandler.editFilm(tempFilm);
            Toast.makeText(this, "Data film telah diperbaharui", Toast.LENGTH_SHORT).show();
        } else {
            dbHandler.tambahFilm(tempFilm);
            Toast.makeText(this, "Data film telah ditambahkan", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void hapusData(){
        dbHandler.hapusFilm(idFilm);
        Toast.makeText(this, "Data film telah dihapus", Toast.LENGTH_SHORT).show();
    }





    @Override
    public void onClick(View v) {
        int idView = v.getId();

        if (idView == R.id.btn_simpan){
            simpanData();
        } else if (idView == R.id.iv_film) {
            pickImage();
        }
    }
}

