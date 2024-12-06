package ca.gbc.mygastromap;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private EditText searchInput;
    private Button searchButton;
    private ListView searchResultsListView;
    private RestaurantDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchInput = findViewById(R.id.searchInput);
        searchButton = findViewById(R.id.searchButton);
        searchResultsListView = findViewById(R.id.searchResultsListView);
        dbHelper = new RestaurantDatabaseHelper(this);

        searchButton.setOnClickListener(view -> performSearch());
    }

    private void performSearch() {
        String query = searchInput.getText().toString().trim();
        if (query.isEmpty()) {
            searchInput.setError("Please enter a search term");
            return;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {"name", "address"};
        String selection = "name LIKE ? OR address LIKE ?";
        String[] selectionArgs = {"%" + query + "%", "%" + query + "%"};

        Cursor cursor = db.query("restaurants", projection, selection, selectionArgs, null, null, null);
        ArrayList<String> results = new ArrayList<>();

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            results.add(name + " - " + address);
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, results);
        searchResultsListView.setAdapter(adapter);
    }
}
