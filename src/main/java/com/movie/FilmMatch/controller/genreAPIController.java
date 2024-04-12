package com.movie.FilmMatch.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.movie.FilmMatch.vo.CraditeVo;
import com.movie.FilmMatch.vo.PlayingVo;



@Controller
@RequestMapping("/main/")
public class genreAPIController {
	
    @RequestMapping("/genreapi.do")
public static List<PlayingVo> search_playing() throws Exception {
    List<PlayingVo> playing_list = new ArrayList<>();
    List<CraditeVo> cradite_list = new ArrayList<>();

    String clientId = "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkNzE5ZDc5YjU5NDNkYTgxNWEyYWY5NDRhNjNlZjI4MSIsInN1YiI6IjY1ZjAxMWE0NjZhN2MzMDBjYWRkYmJhZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.xFFFvCXvNIZSbwFYVN591kIVeKIQnkMs7nAQ6CAxLDA";
    String clientSecret = "d719d79b5943da815a2af944a63ef281";
    String accept = "accept: application/json";

    String gender = "";
    String name = "";
    String profile_path = "";
    String character = "";
    String known_for_department = "";

    for (int page = 1; page <= 5; page++) {
        String urlStr = "https://api.themoviedb.org/3/movie/now_playing?language=ko&page=" + page;
        URL url = new URL(urlStr);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

        urlConn.setRequestProperty("api_key", clientSecret);
        urlConn.setRequestProperty("Authorization", clientId);
        urlConn.setRequestProperty("accept", accept);

        try (InputStream is = urlConn.getInputStream();
             InputStreamReader isr = new InputStreamReader(is, "utf-8");
             BufferedReader br = new BufferedReader(isr)) {

            StringBuffer sb = new StringBuffer();
            String data;
            while ((data = br.readLine()) != null) {
                sb.append(data);
            }

            JSONObject json = new JSONObject(sb.toString());
            JSONArray posterArray = json.getJSONArray("results");

            for (int i = 0; i < posterArray.length(); i++) {
                JSONObject movie = posterArray.getJSONObject(i);

                String id = movie.getDouble("id") + "";

                String clientId1 = "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkNzE5ZDc5YjU5NDNkYTgxNWEyYWY5NDRhNjNlZjI4MSIsInN1YiI6IjY1ZjAxMWE0NjZhN2MzMDBjYWRkYmJhZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.xFFFvCXvNIZSbwFYVN591kIVeKIQnkMs7nAQ6CAxLDA";
                String clientSecret1 = "d719d79b5943da815a2af944a63ef281";
                String accept1 = "accept: application/json";
                String urlStr1 = "https://api.themoviedb.org/3/movie/" + id + "/credits?language=en-US";
                URL url1 = new URL(urlStr1);
                HttpURLConnection urlConn1 = (HttpURLConnection) url1.openConnection();

                urlConn1.setRequestProperty("key", clientSecret1);
                urlConn1.setRequestProperty("Authorization", clientId1);
                urlConn1.setRequestProperty("accept", accept1);

                urlConn1.connect();

                try (InputStream is1 = urlConn1.getInputStream();
                     InputStreamReader isr1 = new InputStreamReader(is1, "utf-8");
                     BufferedReader br1 = new BufferedReader(isr1)) {

                    StringBuffer sb1 = new StringBuffer();
                    String data1;
                    while ((data1 = br1.readLine()) != null) {
                        sb1.append(data1);
                    }

                    JSONObject json1 = new JSONObject(sb1.toString());
                    JSONArray craditeArray = json1.getJSONArray("cast");

                    for (int s = 0; s < craditeArray.length(); s++) {
                        JSONObject ps1 = craditeArray.getJSONObject(s);

                        gender = ps1.getInt("gender")+"";
                        name = ps1.getString("name");
                        if (!ps1.isNull("profile_path")) { // profile_path가 null이 아닌 경우에만 가져옴
                            profile_path = ps1.getString("profile_path");
                        } else {
                            profile_path = ""; // null이면 빈 문자열로 설정
                        }
                        character = ps1.getString("character");
                        known_for_department = ps1.getString("known_for_department");

                        CraditeVo vo1 = new CraditeVo(gender, name, profile_path, character, known_for_department);
                        cradite_list.add(vo1);
                        if(s>=3)break;
                    }
                    
                }


                String title = movie.getString("title");
                String overview = movie.getString("overview");
                String poster_path = movie.getString("poster_path");
                String release_date = movie.getString("release_date");
                String popularity = movie.getDouble("popularity") + "";
                String vote_count = movie.getDouble("vote_count") + "";

                JSONArray genreIdsArray = movie.getJSONArray("genre_ids");
                List<Integer> genre_list = new ArrayList<>();
                for (int j = 0; j < genreIdsArray.length(); j++) {
                    int genreId = genreIdsArray.getInt(j);
                    genre_list.add(genreId);
                }

                PlayingVo vo = new PlayingVo(id, title, overview, poster_path, release_date, popularity, vote_count, id, genre_list, cradite_list);

                playing_list.add(vo);
            }
        }
    }

    return playing_list;
}

}

