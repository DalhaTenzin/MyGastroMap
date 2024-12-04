
package ca.gbc.mygastromap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {

    private Button addRestaurantButton, viewListButton, searchButton, profileButton, aboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        addRestaurantButton = findViewById(R.id.addRestaurantButton);
        viewListButton = findViewById(R.id.viewListButton);
        searchButton = findViewById(R.id.searchButton);
        profileButton = findViewById(R.id.profileButton);
        aboutButton = findViewById(R.id.aboutButton);

        addRestaurantButton.setOnClickListener(view ->
            startActivity(new Intent(MainMenuActivity.this, AddRestaurantActivity.class)));

        viewListButton.setOnClickListener(view ->
            startActivity(new Intent(MainMenuActivity.this, RestaurantListActivity.class)));

        searchButton.setOnClickListener(view ->
            startActivity(new Intent(MainMenuActivity.this, SearchActivity.class)));

        profileButton.setOnClickListener(view ->
            startActivity(new Intent(MainMenuActivity.this, ProfileActivity.class)));

        aboutButton.setOnClickListener(view ->
            startActivity(new Intent(MainMenuActivity.this, AboutActivity.class)));
    }
}
