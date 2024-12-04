
package ca.gbc.mygastromap;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView aboutText = findViewById(R.id.aboutText);
        aboutText.setText("Welcome to MyGastroMap!\n\nThis app helps food enthusiasts document " +
                "and share their dining experiences. It was developed with passion to provide an intuitive way " +
                "to track restaurants, explore cuisines, and share favorites with the community.");
    }
}
