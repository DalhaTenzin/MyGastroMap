package ca.gbc.mygastromap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RestaurantDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyGastroMap.db";
    private static final int DATABASE_VERSION = 2;


    public RestaurantDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE restaurants (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "address TEXT, " +
                "phone TEXT, " +
                "description TEXT, " +
                "rating REAL DEFAULT 0.0, " +
                "latitude REAL, " +
                "longitude REAL)"); // Add latitude and longitude columns
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("ALTER TABLE restaurants ADD COLUMN rating REAL DEFAULT 0.0");
        }
    }


    public float getRestaurantRating(int restaurantId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "restaurants",
                new String[]{"rating"},
                "id=?",
                new String[]{String.valueOf(restaurantId)},
                null,
                null,
                null
        );

        float rating = 0;
        if (cursor.moveToFirst()) {
            rating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"));
        }
        cursor.close();
        return rating;
    }


    public void updateRestaurantRating(int restaurantId, float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("rating", rating);
        db.update("restaurants", values, "id = ?", new String[]{String.valueOf(restaurantId)});
    }

}



