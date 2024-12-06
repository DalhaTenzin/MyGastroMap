package ca.gbc.mygastromap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RestaurantListActivity extends AppCompatActivity {

    private LinearLayout restaurantListContainer;
    private RestaurantDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        restaurantListContainer = findViewById(R.id.restaurantListContainer);
        dbHelper = new RestaurantDatabaseHelper(this);

        loadRestaurantData();
    }

    @SuppressLint("SetTextI18n")
    private void loadRestaurantData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                "restaurants",
                new String[]{"id", "name", "address", "phone", "description"},
                null,
                null,
                null,
                null,
                null
        );

        restaurantListContainer.removeAllViews(); // Clear previous views

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));

            // Inflate the custom layout
            View itemView = LayoutInflater.from(this).inflate(R.layout.restaurant_list_item, restaurantListContainer, false);

            // Find and bind views
            TextView restaurantDetails = itemView.findViewById(R.id.textViewRestaurantDetails);
            Button detailsButton = itemView.findViewById(R.id.detailsButton);
            Button deleteButton = itemView.findViewById(R.id.deleteButton);

            restaurantDetails.setText(name + " - " + address);

            // Set button actions
            detailsButton.setOnClickListener(v -> {
                Intent intent = new Intent(RestaurantListActivity.this, DetailsActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("address", address);
                intent.putExtra("phone", phone);
                intent.putExtra("description", description);
                android.util.Log.d("RestaurantListActivity", "Passing Data: " +
                        "ID=" + id + ", Name=" + name + ", Address=" + address +
                        ", Phone=" + phone + ", Description=" + description);
                startActivity(intent);
            });

            deleteButton.setOnClickListener(v -> {
                new android.app.AlertDialog.Builder(this)
                        .setTitle("Delete Restaurant")
                        .setMessage("Are you sure you want to delete this restaurant?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            SQLiteDatabase writableDb = dbHelper.getWritableDatabase();
                            writableDb.delete("restaurants", "id = ?", new String[]{String.valueOf(id)});
                            Toast.makeText(this, "Restaurant Deleted", Toast.LENGTH_SHORT).show();
                            loadRestaurantData(); // Refresh the list
                        })
                        .setNegativeButton("No", null)
                        .show();
            });

            restaurantListContainer.addView(itemView);
        }

        cursor.close();
    }
}
