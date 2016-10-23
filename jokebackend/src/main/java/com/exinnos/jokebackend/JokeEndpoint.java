/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.exinnos.jokebackend;

import com.exinnos.jokelibrary.JokesProvider;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "jokerApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "jokebackend.exinnos.com",
                ownerName = "jokebackend.exinnos.com",
                packagePath = ""
        )
)
public class JokeEndpoint {

    /**
     * A simple endpoint method that takes a random joke from java library
     */
    @ApiMethod(name = "fetchAJoke")
    public JokeBean fetchAJoke() {
        JokesProvider jokesProvider = new JokesProvider();
        String jokeString = jokesProvider.getAJoke();

        JokeBean response = new JokeBean();
        response.setJokeData(jokeString);

        return response;
    }
}
