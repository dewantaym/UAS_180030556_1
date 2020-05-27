package com.bh183.dewanta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_film";
    private final static String TABLE_FILM ="t_film";
    private final static String KEY_ID_FILM = "ID_Film";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_TGL = "Release_Date";
    private final static String KEY_COVER = "Cover";
    private final static String KEY_GENRE = "Genre";
    private final static String KEY_DURATION = "Duration";
    private final static String KEY_RATING = "Rating";
    private final static String KEY_SYNOPSIS = "Synopsis";
    private Context context;


    public DatabaseHandler(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FILM = "CREATE TABLE " + TABLE_FILM
                + "(" + KEY_ID_FILM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_TGL + " DATE, "
                + KEY_COVER + " TEXT, " + KEY_GENRE + " TEXT, "
                + KEY_DURATION + " TEXT, " + KEY_RATING + " TEXT, " + KEY_SYNOPSIS + " TEXT);";

        db.execSQL(CREATE_TABLE_FILM);
        inisialisasiFilmAwal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_FILM;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahFilm(Film dataFilm){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, dataFilm.getRilis());
        cv.put(KEY_COVER, dataFilm.getCover());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_DURATION, dataFilm.getDurasi());
        cv.put(KEY_DURATION, dataFilm.getDurasi());
        cv.put(KEY_RATING, dataFilm.getRating());
        cv.put(KEY_SYNOPSIS, dataFilm.getSynopsis());


        db.insert(TABLE_FILM, null, cv);
        db.close();
    }

    public void tambahFilm(Film dataFilm, SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, dataFilm.getRilis());
        cv.put(KEY_COVER, dataFilm.getCover());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_DURATION, dataFilm.getDurasi());
        cv.put(KEY_RATING, dataFilm.getRating());
        cv.put(KEY_SYNOPSIS, dataFilm.getSynopsis());
        db.insert(TABLE_FILM, null, cv);
    }

    public void editFilm(Film dataFilm){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, dataFilm.getRilis());
        cv.put(KEY_COVER, dataFilm.getCover());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_DURATION, dataFilm.getDurasi());
        cv.put(KEY_RATING, dataFilm.getRating());
        cv.put(KEY_SYNOPSIS, dataFilm.getSynopsis());

        db.update(TABLE_FILM, cv, KEY_ID_FILM + "=?", new String[]{String.valueOf(dataFilm.getIdFilm())});
        db.close();
    }

    public void hapusFilm (int idFilm){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FILM, KEY_ID_FILM + "=?", new String[]{String.valueOf(idFilm)});
        db.close();
    }

    public ArrayList<Film> getAllFilm(){
        ArrayList<Film> dataFilm = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FILM;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if(csr.moveToFirst()){
            do {
                Film tempFilm = new Film(
                        csr.getInt(0),
                        csr.getString(1),
                        csr.getString(2),
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6),
                        csr.getString(7)

                );

                dataFilm.add(tempFilm);
            } while (csr.moveToNext());
        }

        return dataFilm;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiFilmAwal(SQLiteDatabase db){
        int idFilm = 0;

        // Menambahkan data film ke 1
        Film film1 = new Film(
                idFilm,
                "Your Name.",
                "Aug 26, 2016",
                storeImageFile(R.drawable.film1),
                "Romance, Drama",
                "1 hr. 46 min.",
                "PG-13 - Teens 13 or older",
                "Mitsuha Miyamizu, a high school girl, yearns to live the life of a boy in the bustling city of Tokyo—a dream that stands in stark contrast to her present life in the countryside. Meanwhile in the city, Taki Tachibana lives a busy life as a high school student while juggling his part-time job and hopes for a future in architecture.\n" +
                        "\n" +
                        "One day, Mitsuha awakens in a room that is not her own and suddenly finds herself living the dream life in Tokyo—but in Taki's body! Elsewhere, Taki finds himself living Mitsuha's life in the humble countryside. In pursuit of an answer to this strange phenomenon, they begin to search for one another.\n" +
                        "\n" +
                        "Kimi no Na wa. revolves around Mitsuha and Taki's actions, which begin to have a dramatic impact on each other's lives, weaving them into a fabric held together by fate and circumstance."
        );

        tambahFilm(film1, db);
        idFilm++;

        // Menambahkan data film ke 2
        Film film2 = new Film(
                idFilm,
                "A Silent Voice",
                "Sep 17, 2016",
                storeImageFile(R.drawable.film2),
                "Drama, School",
                "2 hr. 10 min.",
                "PG-13 - Teens 13 or older",
                "As a wild youth, elementary school student Shouya Ishida sought to beat boredom in the cruelest ways. When the deaf Shouko Nishimiya transfers into his class, Shouya and the rest of his class thoughtlessly bully her for fun. However, when her mother notifies the school, he is singled out and blamed for everything done to her. With Shouko transferring out of the school, Shouya is left at the mercy of his classmates. He is heartlessly ostracized all throughout elementary and middle school, while teachers turn a blind eye.\n" +
                        "\n" +
                        "Now in his third year of high school, Shouya is still plagued by his wrongdoings as a young boy. Sincerely regretting his past actions, he sets out on a journey of redemption: to meet Shouko once more and make amends.\n" +
                        "\n" +
                        "Koe no Katachi tells the heartwarming tale of Shouya's reunion with Shouko and his honest attempts to redeem himself, all while being continually haunted by the shadows of his past."
        );

        tambahFilm(film2, db);
        idFilm++;

        // Menambahkan data film ke 3
        Film film3 = new Film(
                idFilm,
                "Gintama Movie 2",
                "Jul 6, 2013",
                storeImageFile(R.drawable.film3),
                "Action, Sci-Fi, Shounen",
                "1 hr. 50 min.",
                " PG-13 - Teens 13 or older",
                "When Gintoki apprehends a movie pirate at a premiere, he checks the camera's footage and finds himself transported to a bleak, post-apocalyptic version of Edo, where a mysterious epidemic called the \"White Plague\" has ravished the world's population. It turns out that the movie pirate wasn't a pirate after all—it was an android time machine, and Gintoki has been hurtled five years into the future! Shinpachi and Kagura, his Yorozuya cohorts, have had a falling out and are now battle-hardened solo vigilantes and he himself has been missing for years, disappearing without a trace after scribbling a strange message in his journal.\n" +
                        "\n" +
                        "Setting out in the disguise given to him by the android time machine, Gintoki haphazardly reunites the Yorozuya team to investigate the White Plague, and soon discovers that the key to saving the future lies in the darkness of his own past. Determined to confront a powerful foe, he makes an important discovery—with a ragtag band of friends and allies at his side, he doesn't have to fight alone."
        );

        tambahFilm(film3, db);
        idFilm++;

    }
}
