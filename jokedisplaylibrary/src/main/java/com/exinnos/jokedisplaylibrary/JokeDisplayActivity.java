package com.exinnos.jokedisplaylibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {

    public static final String INTENT_EXTRA_KEY_JOKE = "intent_extra_key_joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        String jokeString = getIntent().getStringExtra(INTENT_EXTRA_KEY_JOKE);

        TextView jokeTextView = (TextView) findViewById(R.id.joke_textview);
        jokeTextView.setText(jokeString);
    }
}
