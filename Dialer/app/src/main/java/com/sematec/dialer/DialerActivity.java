package com.sematec.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DialerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialer);
    }

    public void onDialButtonClicked(View view){
        EditText dialPad = findViewById(R.id.dial_pad);

        String phoneNumber = "tel:" + dialPad.getText().toString();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber));
        startActivity(intent);
    }
}
