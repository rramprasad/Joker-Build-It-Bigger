package com.exinnos.jokelibrary;

import java.util.Random;

/**
 * Joke provider
 */
public class JokesProvider {

    String[] jokes = {
            "Joke 1","Joke 2","Joke 3","Joke 4","Joke 5"
    };

    /**
     * Get a joke from collections
     * @return
     */
    public String getAJoke(){
        int index = new Random().nextInt(5);
        return jokes[index];
    }
}

