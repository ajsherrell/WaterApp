package com.packtpub.waterapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.packtpub.waterapp.adapters.MainAdapter;
import com.packtpub.waterapp.models.Drink;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private MainAdapter mAdapter;
    private ArrayList<Drink> mDrinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // add recycler view class and attach it to xml view
        RecyclerView recyclerView = (RecyclerView)
                findViewById(R.id.main_recycler_view);

        // use linear layout manager and attach recycler view to it
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // dummy data
        mDrinks = new ArrayList<Drink>();
        Drink firstDrink = new Drink();
        firstDrink.comments = "I like water with bubbles most of the time...";
        firstDrink.dateAndTime = new Date();
        mDrinks.add(firstDrink);
        Drink secondDrink = new Drink();
        secondDrink.comments = "I also like water without bubbles. It depends om my mood I guess /;-)";
        secondDrink.dateAndTime = new Date();
        mDrinks.add(secondDrink);

        // make an instance of the adapter
        mAdapter = new MainAdapter(this, mDrinks);
        recyclerView.setAdapter(mAdapter);

        //set anim button elevation
        findViewById(R.id.main_button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEntry();
            }
        });

    }

    private int REQUEST_NEW_ENTRY = 1;
    private void showEntry() {
        Intent intent = new Intent(this, EntryActivity.class);
        startActivityForResult(intent, REQUEST_NEW_ENTRY);
    }

    //handle result by overriding the onActivityResult method. A new drink will be created
    // and added to list of drinks. Then notify the adapter of the update to be displayed.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_NEW_ENTRY && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            Drink newDrink = new Drink();
            newDrink.comments = bundle.getString("comments");
            newDrink.imageUri = bundle.getString("uri");
            newDrink.dateAndTime = new Date();
            mDrinks.add(newDrink);
            mAdapter.notifyDataSetChanged();
        }
    }

}
