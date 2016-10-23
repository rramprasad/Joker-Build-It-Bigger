package com.exinnos.joker.paid;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.exinnos.jokebackend.jokerApi.JokerApi;
import com.exinnos.jokebackend.jokerApi.model.JokeBean;
import com.exinnos.jokedisplaylibrary.JokeDisplayActivity;
import com.exinnos.joker.AppConstants;
import com.exinnos.joker.MainActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.SignInButton;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.R;

import java.io.IOException;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ProgressBar jokeProgressBar;
    private OnMainActivityFragmentListener mListener;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Button tellJokeButton = (Button)rootView.findViewById(R.id.tell_joke_button);
        tellJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FetchJokeAsyncTask().execute();
            }
        });

        jokeProgressBar = (ProgressBar)rootView.findViewById(R.id.joke_progressbar);

        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        if(context instanceof MainActivity){
            mListener = (OnMainActivityFragmentListener)context;
        }
        super.onAttach(context);
    }

    private class FetchJokeAsyncTask extends AsyncTask<Void,Void,String> {

        private JokerApi jokerApi;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(jokeProgressBar != null){
                jokeProgressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected String doInBackground(Void... voids) {

            if(jokerApi == null) {
                JokerApi.Builder jokerApiBuilder = new JokerApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
                jokerApiBuilder.setRootUrl(AppConstants.BASE_URL);
                        // Used for local development purpose
                        /*.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                                request.setDisableGZipContent(true);
                            }
                        });*/
                jokerApi = jokerApiBuilder.build();
            }

            try {
                JokeBean jokeBean = jokerApi.fetchAJoke().execute();
                return jokeBean.getJokeData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String jokeString) {
            super.onPostExecute(jokeString);

            if(jokeProgressBar != null){
                jokeProgressBar.setVisibility(View.GONE);
            }

            mListener.onJokeReceived(jokeString);
        }
    }

    public interface OnMainActivityFragmentListener{
        void onJokeReceived(String jokeString);
    }
}
