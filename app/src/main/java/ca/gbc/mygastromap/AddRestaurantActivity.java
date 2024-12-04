
package ca.gbc.mygastromap;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddRestaurantActivity extends AppCompatActivity {

    private EditText restaurantName, restaurantAddress, restaurantPhone, restaurantDescription;
    private Button saveButton;
    private RestaurantDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        restaurantName = findViewById(R.id.restaurantName);
        restaurantAddress = findViewById(R.id.restaurantAddress);
        restaurantPhone = findViewById(R.id.restaurantPhone);
        restaurantDescription = findViewById(R.id.restaurantDescription);
        saveButton = findViewById(R.id.saveButton);

        dbHelper = new RestaurantDatabaseHelper(this);

        saveButton.setOnClickListener(view -> {
            String name = restaurantName.getText().toString();
            String address = restaurantAddress.getText().toString();
            String phone = restaurantPhone.getText().toString();
            String description = restaurantDescription.getText().toString();

            if (!name.isEmpty() && !address.isEmpty()) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("address", address);
                values.put("phone", phone);
                values.put("description", description);

                long newRowId = db.insert("restaurants", null, values);
                if (newRowId != -1) {
                    Toast.makeText(this, "Restaurant Saved", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Error Saving Restaurant", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
