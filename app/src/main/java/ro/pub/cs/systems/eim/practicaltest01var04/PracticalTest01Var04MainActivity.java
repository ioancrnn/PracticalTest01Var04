package ro.pub.cs.systems.eim.practicaltest01var04;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var04MainActivity extends AppCompatActivity {

    EditText editName;
    EditText editGroup;
    Button navigateToSecondaryActivityButton;
    Button display_information_button;

    TextView displayText;

    CheckBox checkBoxName;
    CheckBox checkBoxGroup;

    boolean checkBoxNameState;
    boolean checkBoxGroupState;
    String name;
    String group;

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_EXTRA, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();

    private void startPracticalTestService() {
        if (editName != null && editGroup != null) {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Var04Service.class);
            intent.putExtra("editName", editName.getText().toString());
            intent.putExtra("editGroup", editGroup.getText().toString());
            getApplicationContext().startService(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_main);

        editName = findViewById(R.id.edit_name);
        editGroup = findViewById(R.id.edit_group);
        navigateToSecondaryActivityButton = findViewById(R.id.navigate_to_secondary_activity_button);

        display_information_button = findViewById(R.id.display_information_button);
        checkBoxName = findViewById(R.id.checkbox_name);
        checkBoxGroup = findViewById(R.id.checkbox_group);

        checkBoxName.setOnClickListener(it -> {
            checkBoxNameState = checkBoxName.isChecked();
        });

        checkBoxGroup.setOnClickListener(it -> {
            checkBoxGroupState = checkBoxGroup.isChecked();
        });




        displayText = findViewById(R.id.displayed_information);
        display_information_button.setOnClickListener(it -> {
            displayText.setText(String.valueOf(name + " " + group));
            if (checkBoxNameState) {
                name = editName.getText().toString();
            }
            else {
                Toast.makeText(this, "set name value", Toast.LENGTH_LONG).show();
            }
            if (checkBoxGroupState) {
                group = editGroup.getText().toString();

            }
            else {
                Toast.makeText(this, "set group value", Toast.LENGTH_LONG).show();
            }
            displayText = findViewById(R.id.displayed_information);
            startPracticalTestService();
        });


        navigateToSecondaryActivityButton.setOnClickListener(it -> {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Var04SecondaryActivity.class);
            intent.putExtra("editName", editName.getText().toString());
            intent.putExtra("editGroup", editGroup.getText().toString());
            startActivityForResult(intent, 1);
        });

        for (String action : Constants.actionTypes) {
            intentFilter.addAction(action);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageBroadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var04Service.class);
        getApplicationContext().stopService(intent);
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("editName", editName.getText().toString());
        savedInstanceState.putString("editGroup", editGroup.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey("editName")) {
            editName.setText(savedInstanceState.getString("editName"));
        } else {
            editName.setText("");
        }
        if (savedInstanceState.containsKey("editGroup")) {
            editGroup.setText(savedInstanceState.getString("editGroup"));
        } else {
            editGroup.setText("");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Result ok!", Toast.LENGTH_LONG).show();
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Result not ok!", Toast.LENGTH_LONG).show();
            }
        }
    }

}