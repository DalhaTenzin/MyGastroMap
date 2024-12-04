
package ca.gbc.mygastromap;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private RestaurantDatabaseHelper dbHelper;
    private TextView totalRestaurantsText, highestRatedText, favoriteCuisineText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        totalRestaurantsText = findViewById(R.id.totalRestaurants);
        highestRatedText = findViewById(R.id.highestRated);
        favoriteCuisineText = findViewById(R.id.favoriteCuisine);

        dbHelper = new RestaurantDatabaseHelper(this);
        loadProfileData();
    }

    private void loadProfileData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor totalCursor = db.rawQuery("SELECT COUNT(*) FROM restaurants", null);
        if (totalCursor.moveToFirst()) {
            int total = totalCursor.getInt(0);
            totalRestaurantsText.setText("Total Restaurants Visited: " + total);
        }
        totalCursor.close();

        highestRatedText.setText("Highest Rated Restaurant: Coming Soon");
        favoriteCuisineText.setText("Favorite Cuisine: Coming Soon");
    }
}
