package com.manning.aip.json;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONObject;

import com.manning.aip.Movie;

public class JsonMovieParser {

   public static Movie parseMovie(InputStream json) throws Exception {
      BufferedReader reader = new BufferedReader(new InputStreamReader(json));
      StringBuilder sb = new StringBuilder();

      String line = reader.readLine();
      while (line != null) {
         sb.append(line);
         line = reader.readLine();
      }
      reader.close();

      JSONArray jsonReply = new JSONArray(sb.toString());
      JSONObject jsonMovie = jsonReply.getJSONObject(0);
      return new JsonMovieParser().parse(jsonMovie);
   }

   public Movie parse(JSONObject jsonMovie) throws Exception {
      Movie movie = new Movie();

      movie.setTitle(jsonMovie.getString("name"));
      movie.setRating(jsonMovie.getString("rating"));

      return movie;
   }
}
