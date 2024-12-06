package ca.gbc.mygastromap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {
    private TextView restaurantNameTextView, restaurantAddressTextView, restaurantPhoneTextView, restaurantDescriptionTextView;
    private Button viewOnMapButton, shareButton, submitRatingButton;
    private RatingBar ratingBar;
    private String name, address, phone, description;
    private int restaurantId;
    private RestaurantDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Initialize database helper
        dbHelper = new RestaurantDatabaseHelper(this);

        // Connect UI elements
        restaurantNameTextView = findViewById(R.id.textViewRestaurantName);
        restaurantAddressTextView = findViewById(R.id.textViewRestaurantAddress);
        restaurantPhoneTextView = findViewById(R.id.textViewRestaurantPhone);
        restaurantDescriptionTextView = findViewById(R.id.textViewRestaurantDescription);
        viewOnMapButton = findViewById(R.id.buttonViewOnMap);
        shareButton = findViewById(R.id.buttonShare);
        ratingBar = findViewById(R.id.ratingBarRestaurant);
        submitRatingButton = findViewById(R.id.submitRatingButton);

        // Get data passed from previous activity
        restaurantId = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        address = getIntent().getStringExtra("address");
        phone = getIntent().getStringExtra("phone");
        description = getIntent().getStringExtra("description");

        // Handle missing data
        if (restaurantId == -1 || name == null || address == null || phone == null || description == null) {
            Toast.makeText(this, "Error loading restaurant details!", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if data is invalid
            return;
        }

        // Set data to UI elements
        restaurantNameTextView.setText(name);
        restaurantAddressTextView.setText(address);
        restaurantPhoneTextView.setText(phone);
        restaurantDescriptionTextView.setText(description);

        // Retrieve and display the current rating from the database
        float currentRating = dbHelper.getRestaurantRating(restaurantId);
        ratingBar.setRating(currentRating);

        // Set button actions
        viewOnMapButton.setOnClickListener(view -> {
            Intent mapIntent = new Intent(DetailsActivity.this, MapViewActivity.class);
            mapIntent.putExtra("address", address);
            startActivity(mapIntent);
        });

        shareButton.setOnClickListener(view -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this restaurant: " + name + ", " + address);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });

        submitRatingButton.setOnClickListener(view -> {
            float selectedRating = ratingBar.getRating(); // Get selected rating
            if (selectedRating > 0) {
                dbHelper.updateRestaurantRating(restaurantId, selectedRating); // Save rating to database
                Toast.makeText(this, "Rating submitted successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please select a valid rating before submitting.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
