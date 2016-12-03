package com.exinnos.joker.paid;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.exinnos.joker.FetchJokeAsyncTask;
import com.udacity.gradle.builditbigger.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements FetchJokeAsyncTask.FetchJokeAsyncTaskListener {

    private ProgressBar jokeProgressBar;
    private OnMainActivityFragmentListener mListener;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Button tellJokeButton = (Button) rootView.findViewById(R.id.tell_joke_button);
        tellJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FetchJokeAsyncTask(MainActivityFragment.this).execute();
            }
        });

        jokeProgressBar = (ProgressBar) rootView.findViewById(R.id.joke_progressbar);

        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        if (context instanceof MainActivity) {
            mListener = (OnMainActivityFragmentListener) context;
        }
        super.onAttach(context);
    }

    @Override
    public void onFetchJokeAsyncTaskOnPreExecute() {
        if (jokeProgressBar != null) {
            jokeProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFetchJokeAsyncTaskOnPostExecute(String jokeString) {
        if (jokeProgressBar != null) {
            jokeProgressBar.setVisibility(View.GONE);
        }

        mListener.onJokeReceived(jokeString);
    }


    public interface OnMainActivityFragmentListener {
        void onJokeReceived(String jokeString);
    }
}
