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

import com.movie.FilmMatch.vo.ActorVo;
import com.movie.FilmMatch.vo.ActorprofVo;
import com.movie.FilmMatch.vo.KnownforVo;

@Controller
@RequestMapping("/main/")
public class Actor2APIController {

    @RequestMapping("/actorjapan.do")
    public static List<ActorVo> search_actorjapan() throws Exception {

        List<ActorVo> actor_list = new ArrayList<>();
        List<ActorprofVo> actorprof_list = new ArrayList<>();
        String homepage = "";
        String birthday = "";
        String clientId = "d719d79b5943da815a2af944a63ef281";
        String urlStr = "https://api.themoviedb.org/3/search/person?query=Aya%20Asahina&include_adult=false&language=ko&page=1&api_key=" + clientId;

        URL url = new URL(urlStr);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

        try (InputStream is = urlConn.getInputStream();
             InputStreamReader isr = new InputStreamReader(is, "utf-8");
             BufferedReader br = new BufferedReader(isr)) {

            StringBuffer sb = new StringBuffer();
            String data;
            while ((data = br.readLine()) != null) {
                sb.append(data);
            }

            JSONObject json = new JSONObject(sb.toString());
            JSONArray actorArray = json.getJSONArray("results");

            for (int i = 0; i < actorArray.length(); i++) {
                JSONObject actor = actorArray.getJSONObject(i);

                String id = actor.getDouble("id") + "";

                String clientId1 = "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkNzE5ZDc5YjU5NDNkYTgxNWEyYWY5NDRhNjNlZjI4MSIsInN1YiI6IjY1ZjAxMWE0NjZhN2MzMDBjYWRkYmJhZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.xFFFvCXvNIZSbwFYVN591kIVeKIQnkMs7nAQ6CAxLDA";
                String clientSecret1 = "d719d79b5943da815a2af944a63ef281";
                String accept1 = "accept: application/json";
                String urlStr1 = "https://api.themoviedb.org/3/person/" + id + "?language=ko";
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
                   

                    for (int m = 0; m < json1.length(); m++) {
                        JSONObject ps1 = json1;

                        String biography = ps1.getString("biography");
                        
                        
                        if (!ps1.isNull("birthday")) { // profile_path가 null이 아닌 경우에만 가져옴
                            birthday = ps1.getString("birthday");
                        } else {
                            birthday = ""; // null이면 빈 문자열로 설정
                        }


                        if (!ps1.isNull("homepage")) { // profile_path가 null이 아닌 경우에만 가져옴
                            homepage = ps1.getString("homepage");
                        } else {
                            homepage = ""; // null이면 빈 문자열로 설정
                        }
                    
                        ActorprofVo vo1 = new ActorprofVo(biography, birthday, homepage);
                        actorprof_list.add(vo1);
                        if(m>=0)break;
                    }
                    
                }


                String gender = actor.getInt("gender") + "";
                String name = actor.getString("name");
                 String profile_path = "";
                if (!actor.isNull("profile_path")) { // profile_path가 null이 아닌 경우에만 가져옴
                    profile_path = actor.getString("profile_path");
                }
                
                if (i == 5) {continue;}

                JSONArray movieinfoArray = actor.getJSONArray("known_for");
                List<KnownforVo> known_list = new ArrayList<>(); // Initialize known_list here

                for (int j = 0; j < movieinfoArray.length(); j++) {
                    JSONObject movieinfo = movieinfoArray.getJSONObject(j);

                    String id1 = movieinfo.getDouble("id") + "";
                    String title = "";
                    try {
                        title = movieinfo.getString("name");    
                    } catch (Exception e) {

                    }

                    try {
                        title = movieinfo.getString("title");    
                    } catch (Exception e) {
                        
                    }

                    String overview = movieinfo.getString("overview");
                    String poster_path = movieinfo.getString("poster_path");
                    String media_type = movieinfo.getString("media_type");
                    String vote_average = movieinfo.getDouble("vote_average") + "";
                    String vote_count = movieinfo.getDouble("vote_count") + "";


                    KnownforVo vo = new KnownforVo(id1, title, overview, poster_path, media_type, vote_average, vote_count, vote_count);
                    known_list.add(vo);
                }

               
                ActorVo vo = new ActorVo(id, gender, name, profile_path, known_list, actorprof_list);
                actor_list.add(vo);
                if (actor_list.size() >= 1) break;
            }
        }

        return actor_list;
    }



}
