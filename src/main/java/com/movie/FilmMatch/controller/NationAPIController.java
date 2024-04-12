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

import com.movie.FilmMatch.vo.NationVo;



@Controller
@RequestMapping("/main/")
public class NationAPIController {
	
    @RequestMapping("/nationapi.do")
	public static List<NationVo> search_nation() throws Exception {
    List<NationVo> nation_list = new ArrayList<NationVo>();
    
    String poster_path ="";
    String clientId = "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkNzE5ZDc5YjU5NDNkYTgxNWEyYWY5NDRhNjNlZjI4MSIsInN1YiI6IjY1ZjAxMWE0NjZhN2MzMDBjYWRkYmJhZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.xFFFvCXvNIZSbwFYVN591kIVeKIQnkMs7nAQ6CAxLDA";
    String clientSecret = "d719d79b5943da815a2af944a63ef281";
    String accept = "accept: application/json";

   
    for (int page = 1; page <= 10; page++) {
        String urlStr = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=ko&sort_by=popularity.desc&page=" + page;
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
            JSONArray nationArray = json.getJSONArray("results");

            for (int i = 0; i < nationArray.length(); i++) {
                JSONObject nation = nationArray.getJSONObject(i);

                String id = nation.getDouble("id") + "";
				String original_language = nation.getString("original_language");
                String overview = nation.getString("overview");
				String title = nation.getString("title");
               
                if (!nation.isNull("poster_path")) { // profile_path가 null이 아닌 경우에만 가져옴
                    poster_path = nation.getString("poster_path");
                } else {
                    poster_path = ""; // null이면 빈 문자열로 설정
                }
               
                NationVo vo = new NationVo(id, original_language, overview, title, poster_path);

                nation_list.add(vo);
            }
        }
    }

    return nation_list;
}

}


