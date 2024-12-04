
package ca.gbc.mygastromap;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class RestaurantListActivity extends AppCompatActivity {

    private ListView restaurantListView;
    private RestaurantDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        restaurantListView = findViewById(R.id.restaurantListView);
        dbHelper = new RestaurantDatabaseHelper(this);

        loadRestaurantData();
    }

    private void loadRestaurantData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {"name", "address"};
        Cursor cursor = db.query("restaurants", projection, null, null, null, null, null);

        ArrayList<String> restaurantList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            restaurantList.add(name + " - " + address);
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, restaurantList);
        restaurantListView.setAdapter(adapter);
    }
}
