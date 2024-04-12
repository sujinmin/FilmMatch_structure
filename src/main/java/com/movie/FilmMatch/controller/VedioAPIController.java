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

import com.movie.FilmMatch.vo.VedioVo;


@Controller
@RequestMapping("/main/")
public class VedioAPIController {
	

	@RequestMapping("/vedioapi.do")
	public static List<VedioVo> search_vedio() throws Exception {
    List<VedioVo> list = new ArrayList<>(); // 리스트는 반복문 외부에서 초기화

    String clientId = "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkNzE5ZDc5YjU5NDNkYTgxNWEyYWY5NDRhNjNlZjI4MSIsInN1YiI6IjY1ZjAxMWE0NjZhN2MzMDBjYWRkYmJhZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.xFFFvCXvNIZSbwFYVN591kIVeKIQnkMs7nAQ6CAxLDA";
    String clientSecret = "d719d79b5943da815a2af944a63ef281";
    String accept = "accept: application/json";
    String urlStr = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en&page=1&sort_by=popularity.desc";
    
    URL url = new URL(urlStr);
    HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

    urlConn.setRequestProperty("key", clientSecret);
    urlConn.setRequestProperty("Authorization", clientId);
    urlConn.setRequestProperty("accept", accept);
    
    urlConn.connect();
    
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
            JSONObject ps = posterArray.getJSONObject(i);
            int  id = ps.getInt("id");
	
            String clientId1 = "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkNzE5ZDc5YjU5NDNkYTgxNWEyYWY5NDRhNjNlZjI4MSIsInN1YiI6IjY1ZjAxMWE0NjZhN2MzMDBjYWRkYmJhZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.xFFFvCXvNIZSbwFYVN591kIVeKIQnkMs7nAQ6CAxLDA";
            String clientSecret1 = "d719d79b5943da815a2af944a63ef281";
            String accept1 = "accept: application/json";
            String urlStr1 = "https://api.themoviedb.org/3/movie/"+ id +"/videos?language=ko";
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
                JSONArray vedioArray = json1.getJSONArray("results");
                

                for (int s = 0; s < vedioArray.length(); s++) {
                    JSONObject ps1 = vedioArray.getJSONObject(s);
                    String name = ps1.getString("name").replaceAll("'", " ");
                    String key = ps1.getString("key");
			
                    VedioVo vo = new VedioVo(name, key);
                    list.add(vo);
					break;
                }

            }
            // 가져온 비디오 정보 처리 후에 리스트의 크기가 6보다 크거나 같으면 반복문 종료
           // if (list.size() >= 20) break;
        }
    }

    return list;
}
	



}

