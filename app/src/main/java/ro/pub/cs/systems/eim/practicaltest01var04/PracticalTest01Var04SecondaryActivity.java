package ro.pub.cs.systems.eim.practicaltest01var04;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PracticalTest01Var04SecondaryActivity extends AppCompatActivity {

    TextView textName;

    TextView textGroup;
    Button okButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_secondary);

        textName = findViewById(R.id.text_name);
        textGroup = findViewById(R.id.text_group);
        okButton = findViewById(R.id.ok_button);
        cancelButton = findViewById(R.id.cancel_button);

        okButton.setOnClickListener(it -> {
            setResult(RESULT_OK, null);
            finish();
        });

        cancelButton.setOnClickListener(it -> {
            setResult(RESULT_CANCELED, null);
            finish();
        });

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                textName.setText("");
                textGroup.setText("");
            } else {
                String left = String.valueOf(extras.getString("editName"));
                String right = String.valueOf(extras.getString("editGroup"));
                textName.setText(left);
                textGroup.setText(right);
            }
        } else {
            textName.setText(savedInstanceState.getString("resultText"));
        }
    }
}
