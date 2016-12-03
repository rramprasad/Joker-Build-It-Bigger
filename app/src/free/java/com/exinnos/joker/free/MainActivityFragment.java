package com.exinnos.joker.free;

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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements FetchJokeAsyncTask.FetchJokeAsyncTaskListener {

    private ProgressBar jokeProgressBar;
    private OnMainActivityFragmentListener mListener;
    private InterstitialAd mInterstialAd;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = (AdView) rootView.findViewById(R.id.adView);

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(getString(R.string.ad_test_device))
                .build();
        mAdView.loadAd(adRequest);

        mInterstialAd = new InterstitialAd(getActivity());
        mInterstialAd.setAdUnitId(getResources().getString(R.string.interstitial_ad_unit_id));

        mInterstialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                loadNewInsterstialAd();
                new FetchJokeAsyncTask(MainActivityFragment.this).execute();
            }
        });

        loadNewInsterstialAd();

        Button tellJokeButton = (Button) rootView.findViewById(R.id.tell_joke_button);
        tellJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstialAd.isLoaded()) {
                    mInterstialAd.show();
                } else {
                    new FetchJokeAsyncTask(MainActivityFragment.this).execute();
                }

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

    private void loadNewInsterstialAd() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(getString(R.string.ad_test_device))
                .build();
        mInterstialAd.loadAd(adRequest);
    }

    public interface OnMainActivityFragmentListener {
        void onJokeReceived(String jokeString);
    }

}
