package moviesapp.com.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import moviesapp.com.myapplication.beans.PopularMain;
import moviesapp.com.myapplication.beans.PopularResult;

public class MoviesDbHelper extends SQLiteOpenHelper {


    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "movies.db";


    //Movie Constants
    public static final String TABLE_NAME = "movies";

    public static final String _ID = "_id";

    public static final String COLUMN_ADULT = "adult";
    public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ORIGINAL_LANGUAGE = "original_language";
    public static final String COLUMN_ORIGINAL_TITLE = "original_title";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_RELEASE_DATE = "release_date";
    public static final String COLUMN_POSTER_PATH = "poster_path";
    public static final String COLUMN_POPULARITY = "popularity";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_VIDEO = "video";
    public static final String COLUMN_VOTE_AVERAGE = "vote_average";
    public static final String COLUMN_VOTE_COUNT = "vote_count";
    public static final String COLUMN_FULL_POSTER_PATH = "full_poster_path";
    public static final String COLUMN_IMAGE = "jpg_small";
    public static final String COLUMN_FULL_IMAGE = "jpg_big";
    //endregion

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +

                // Unique keys will be auto-generated in either case.
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                // the ID of the location entry associated with this
                COLUMN_ID + " INTEGER, " +
                COLUMN_ADULT + " TEXT, " +
                COLUMN_BACKDROP_PATH + " TEXT, " +
                COLUMN_ORIGINAL_LANGUAGE + " TEXT, " +
                COLUMN_ORIGINAL_TITLE + " TEXT," +
                COLUMN_OVERVIEW + " TEXT, " +
                COLUMN_RELEASE_DATE + " TEXT," +
                COLUMN_POSTER_PATH + " TEXT, " +
                COLUMN_FULL_POSTER_PATH + " TEXT," +
                COLUMN_POPULARITY + " TEXT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_VIDEO + " TEXT, " +
                COLUMN_VOTE_AVERAGE + " REAL, " +
                COLUMN_VOTE_COUNT + " INTEGER, " +
                COLUMN_IMAGE + " BLOB, " +
                COLUMN_FULL_IMAGE + " BLOB" +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Drop db if exists on update
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertMovie(PopularResult popularResult, Context mContext) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, popularResult.getId());
        contentValues.put(COLUMN_ADULT, popularResult.getAdult());
        contentValues.put(COLUMN_BACKDROP_PATH, popularResult.getBackdropPath());
        contentValues.put(COLUMN_ORIGINAL_LANGUAGE, popularResult.getOriginalLanguage());
        contentValues.put(COLUMN_ORIGINAL_TITLE, popularResult.getOriginalTitle());
        contentValues.put(COLUMN_OVERVIEW, popularResult.getOverview());
        contentValues.put(COLUMN_RELEASE_DATE, popularResult.getReleaseDate());
        contentValues.put(COLUMN_POSTER_PATH, popularResult.getPosterPath());
        contentValues.put(COLUMN_POPULARITY, popularResult.getPopularity());
        contentValues.put(COLUMN_TITLE, popularResult.getTitle());
        contentValues.put(COLUMN_VIDEO, popularResult.getVideo());
        contentValues.put(COLUMN_VOTE_AVERAGE, popularResult.getVoteAverage());
        contentValues.put(COLUMN_VOTE_COUNT, popularResult.getVoteCount());


        long rowInserted = db.insert(TABLE_NAME, null, contentValues);

        /*if (rowInserted != -1)
            Toast.makeText(mContext, "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(mContext, "Something wrong", Toast.LENGTH_SHORT).show();*/
        return true;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateMovie(PopularResult popularResult) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ADULT, popularResult.getAdult());
        contentValues.put(COLUMN_BACKDROP_PATH, popularResult.getBackdropPath());
        contentValues.put(COLUMN_ORIGINAL_LANGUAGE, popularResult.getOriginalLanguage());
        contentValues.put(COLUMN_ORIGINAL_TITLE, popularResult.getOriginalTitle());
        contentValues.put(COLUMN_OVERVIEW, popularResult.getOverview());
        contentValues.put(COLUMN_RELEASE_DATE, popularResult.getReleaseDate());
        contentValues.put(COLUMN_POSTER_PATH, popularResult.getPosterPath());
        contentValues.put(COLUMN_POPULARITY, popularResult.getPopularity());
        contentValues.put(COLUMN_TITLE, popularResult.getTitle());
        contentValues.put(COLUMN_VIDEO, popularResult.getVideo());
        contentValues.put(COLUMN_VOTE_AVERAGE, popularResult.getVoteAverage());
        contentValues.put(COLUMN_VOTE_COUNT, popularResult.getVoteCount());

        db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ? ", new String[]{Integer.toString(popularResult.getId())});
        return true;
    }

    public Integer deleteMovie(PopularResult popularResult, Context mContext) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + " = ? ",
                new String[]{Integer.toString(popularResult.getId())});
    }

    public Cursor getMovie(PopularResult popularResult) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                COLUMN_ID + "=?", new String[]{Integer.toString(popularResult.getId())});
        return res;
    }

    public Cursor getAllMovies() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }

    public List<PopularResult> getAllMoviesFromDb() {
        List<PopularResult> result = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        int id = c.getColumnIndex(COLUMN_ID);
        int heading = c.getColumnIndex(COLUMN_TITLE);
        int posterImage = c.getColumnIndex(COLUMN_POSTER_PATH);
        int overvw = c.getColumnIndex(COLUMN_OVERVIEW);
        int ratng = c.getColumnIndex(COLUMN_VOTE_AVERAGE);
        int releasedate = c.getColumnIndex(COLUMN_RELEASE_DATE);
        int language = c.getColumnIndex(COLUMN_ORIGINAL_LANGUAGE);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            PopularResult resultsObj = new PopularResult();

            //Add it to the object
            int movieid = c.getInt(id);
            String title = c.getString(heading);
            String posterPath = c.getString(posterImage);
            String overviews = c.getString(overvw);
            Double rating = c.getDouble(ratng);
            String releaseDate = c.getString(releasedate);
            String lang = c.getString(language);

            //add obejct to the list
            resultsObj.setId(movieid);
            resultsObj.setTitle(title);
            resultsObj.setPosterPath(posterPath);
            resultsObj.setOverview(overviews);
            resultsObj.setVoteAverage(rating);
            resultsObj.setReleaseDate(releaseDate);
            resultsObj.setOriginalLanguage(lang);
            result.add(resultsObj);


        }
        return result;
    }


    public boolean CheckIsDataAlreadyInDBorNot(PopularResult result) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "='" + result.getId() + "'", null);
            if (cursor.moveToFirst()) {
                db.close();
                Log.d("Record  Already Exists", "Table is:" + TABLE_NAME + " ColumnName:" + COLUMN_ID);
                return true;//record Exists

            }
            Log.d("New Record  ", "Table is:" + TABLE_NAME + " ColumnName:" + COLUMN_ID + " Column Value:" + COLUMN_ID);
            db.close();
        } catch (Exception errorException) {
            Log.d("Exception occured", "Exception occured " + errorException);
            // db.close();
        }
        return false;

    }


}
