package com.exinnos.joker;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by RAMPRASAD on 12/3/2016.
 */
public class FetchJokeAsyncTaskTest {

    @Test
    public void testFetchJokeAsyncTask() {
        try {
            String jokeString = new FetchJokeAsyncTask(new FetchJokeAsyncTask.FetchJokeAsyncTaskListener() {
                @Override
                public void onFetchJokeAsyncTaskOnPreExecute() {

                }

                @Override
                public void onFetchJokeAsyncTaskOnPostExecute(String jokeString) {

                }
            }).execute().get(60, TimeUnit.SECONDS);

            assertNotNull(jokeString);
            assertTrue("Joke Async task successfully retrieves a non-empty string " + jokeString, jokeString.length() > 0);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}