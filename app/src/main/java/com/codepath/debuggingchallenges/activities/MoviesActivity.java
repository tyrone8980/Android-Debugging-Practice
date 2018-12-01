package com.codepath.debuggingchallenges.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.codepath.debuggingchallenges.R;
import com.codepath.debuggingchallenges.adapters.MoviesAdapter;
import com.codepath.debuggingchallenges.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MoviesActivity extends AppCompatActivity {

    private static final String API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed";

    ListView lvMovies;
    MoviesAdapter adapter;
    ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        lvMovies = (ListView) findViewById(R.id.lvMovies);
        fetchMovies();
        // Create the adapter to convert the array to views
        MoviesAdapter adapter = new MoviesAdapter(this, movies);

        // Attach the adapter to a ListView
        lvMovies.setAdapter(adapter);


    }


    private void fetchMovies() {
        String url = " https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray moviesJson = response.getJSONArray("results");
                    if(moviesJson.length() ==0)
                    {
                        Log.d("TESTER", "Nothing received from results");
                    }
                    Log.d("TESTER","Adding to movies ArrayList");
                    movies = (ArrayList<Movie>) Movie.fromJSONArray(moviesJson);
                    Log.d("TESTER", "Something was added to movies");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("TESTER", " Nothing was added to movies");
                }
            }
        });
    }
}
