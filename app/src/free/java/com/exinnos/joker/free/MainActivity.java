package com.exinnos.joker.free;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.exinnos.jokedisplaylibrary.JokeDisplayActivity;
import com.udacity.gradle.builditbigger.R;


public class MainActivity extends AppCompatActivity implements MainActivityFragment.OnMainActivityFragmentListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onJokeReceived(String jokeString) {
        launchJokeDisplayActivity(jokeString);
    }

    private void launchJokeDisplayActivity(String jokeString) {
        // Launch joke using Android library - jokedisplaylibrary
        Intent intent = new Intent(MainActivity.this, JokeDisplayActivity.class);
        intent.putExtra(JokeDisplayActivity.INTENT_EXTRA_KEY_JOKE, jokeString);
        startActivity(intent);
    }
}
