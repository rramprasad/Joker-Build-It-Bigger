package com.exinnos.joker;

import android.os.AsyncTask;

import com.exinnos.jokebackend.jokerApi.JokerApi;
import com.exinnos.jokebackend.jokerApi.model.JokeBean;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

/**
 * Created by RAMPRASAD on 12/3/2016.
 */

public class FetchJokeAsyncTask extends AsyncTask<Void, Void, String> {

    private FetchJokeAsyncTaskListener fetchJokeAsyncTaskListener;
    private JokerApi jokerApi;

    public FetchJokeAsyncTask(FetchJokeAsyncTaskListener fetchJokeAsyncTaskListener) {
        this.fetchJokeAsyncTaskListener = fetchJokeAsyncTaskListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        fetchJokeAsyncTaskListener.onFetchJokeAsyncTaskOnPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {

        if (jokerApi == null) {
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
        fetchJokeAsyncTaskListener.onFetchJokeAsyncTaskOnPostExecute(jokeString);
    }

    public interface FetchJokeAsyncTaskListener {
        void onFetchJokeAsyncTaskOnPreExecute();

        void onFetchJokeAsyncTaskOnPostExecute(String jokeString);
    }
}
