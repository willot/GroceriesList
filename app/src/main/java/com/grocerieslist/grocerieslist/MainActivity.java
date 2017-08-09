package com.grocerieslist.grocerieslist;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mGroceriesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGroceriesButton = (Button) findViewById(R.id.groceries);

    }

    public void onClickGroceriesButton(View view){
//        mGroceriesButton.setBackgroundColor(ContextCompat.getColor(this,R.color.colorFuchsia));
        Intent intent = new Intent(this, GroceryActivity.class);

        mGroceriesButton.setText("You clicked it");
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
