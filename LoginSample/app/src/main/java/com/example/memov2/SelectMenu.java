package com.example.memov2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SelectMenu extends AppCompatActivity implements View.OnClickListener {

    private Button memo_app;
    private Button calcu_app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_page);

        memo_app = findViewById(R.id.memo_app);
        memo_app.setOnClickListener(this);
        calcu_app = findViewById(R.id.calcu_app);
        calcu_app.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.calcu_app) {
            Intent intent = new Intent(getApplication(), Calculator.class);
            startActivity(intent);
        }

    }
}