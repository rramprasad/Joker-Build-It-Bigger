package com.exinnos.jokebackend;

/**
 * The object model for the data we are sending through endpoints
 */
public class JokeBean {

    private String jokeData;

    public String getJokeData() {
        return jokeData;
    }

    public void setJokeData(String jokeData) {
        this.jokeData = jokeData;
    }
}