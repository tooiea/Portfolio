package com.example.memov2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.memov2.databinding.ActivityMainBinding;

import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private Button newer;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.top_page);  //content_mainのリストを画面に表示
        newer = findViewById(R.id.newer);   //ボタンとidの紐づけ
        newer.setOnClickListener(this);
        login = findViewById(R.id.loginBtn);   //ボタンとidの紐づけ
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {    //ボタンが押されたとき
        Intent intent;
        if (view.getId() == R.id.newer) {
            intent = new Intent(getApplication(), Add_newUser.class);
            startActivity(intent);
        } else
            intent = new Intent(getApplication(), UserLogin.class);
            startActivity(intent);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

}