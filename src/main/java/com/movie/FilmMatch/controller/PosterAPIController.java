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

import com.movie.FilmMatch.vo.PosterVo;
import com.movie.FilmMatch.vo.VedioVo;


@Controller
@RequestMapping("/main/")
public class PosterAPIController {
	
	
	private static final List<VedioVo> list = null;

	@RequestMapping("/posterapi.do")
	public static List<PosterVo> search_poster() throws Exception{


        List<PosterVo> list = new ArrayList<PosterVo>();
		
		String clientId = "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkNzE5ZDc5YjU5NDNkYTgxNWEyYWY5NDRhNjNlZjI4MSIsInN1YiI6IjY1ZjAxMWE0NjZhN2MzMDBjYWRkYmJhZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.xFFFvCXvNIZSbwFYVN591kIVeKIQnkMs7nAQ6CAxLDA";
		String clientSecret = "d719d79b5943da815a2af944a63ef281";
		String accept = "accept: application/json";
		String urlStr = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=ko&page=1&sort_by=popularity.desc";
		
		URL url = new URL(urlStr);
		
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

		urlConn.setRequestProperty("key", clientSecret);

		urlConn.setRequestProperty("Authorization", clientId); 
		
		urlConn.setRequestProperty("accept", accept);
		
		urlConn.connect();
		
		InputStream is = urlConn.getInputStream();
		InputStreamReader isr = new InputStreamReader(is,"utf-8");
		BufferedReader  br = new BufferedReader(isr);
		//장점 빠른 성능 속도 성능향상  / 라인단위로 읽기가 가능
		
		StringBuffer sb = new StringBuffer();
		while(true) {
			
			String data = br.readLine(); //라인단위로 읽기
			if(data == null) break; 
			
			sb.append(data); // 라인단위 읽은 데이터 스트링버퍼에 넣는다		
				
		}
	
		JSONObject json = new JSONObject(sb.toString());
		JSONArray posterArray = json.getJSONArray("results");
		
		
		for(int i=0; i<posterArray.length(); i++) {
			
			JSONObject ps = posterArray.getJSONObject(i);
			
			String id = ps.getDouble("id")+"";
			String title = ps.getString("title");
			

			String overview = "";
                        if (!ps.isNull("overview")) { // profile_path가 null이 아닌 경우에만 가져옴
                            overview = ps.getString("overview");
                        } else {
							overview = "";
                        }
			String popularity = ps.getDouble("popularity")+"";
			String poster_path = ps.getString("poster_path");
			String release_date = ps.getString("release_date");
			String vote_average = ps.getDouble("vote_average")+"";
			
			PosterVo vo = new PosterVo(id,title, overview, popularity,poster_path,release_date,vote_average);
			
			list.add(vo);

			if(i>=4)break;
		}
		
	return list;
	}

	@RequestMapping("/voteapi.do")
	public static List<PosterVo> search_vote() throws Exception{


        List<PosterVo> list = new ArrayList<PosterVo>();
		
		String clientId = "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkNzE5ZDc5YjU5NDNkYTgxNWEyYWY5NDRhNjNlZjI4MSIsInN1YiI6IjY1ZjAxMWE0NjZhN2MzMDBjYWRkYmJhZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.xFFFvCXvNIZSbwFYVN591kIVeKIQnkMs7nAQ6CAxLDA";
		String clientSecret = "d719d79b5943da815a2af944a63ef281";
		String accept = "accept: application/json";
		String urlStr = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=ko&page=1&sort_by=vote_count.desc";
		
		URL url = new URL(urlStr);
		
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

		urlConn.setRequestProperty("key", clientSecret);

		urlConn.setRequestProperty("Authorization", clientId);
		
		urlConn.setRequestProperty("accept", accept);
		
		urlConn.connect();
		
		InputStream is = urlConn.getInputStream();
		InputStreamReader isr = new InputStreamReader(is,"utf-8");
		BufferedReader  br = new BufferedReader(isr);
		//장점 빠른 성능 속도 성능향상  / 라인단위로 읽기가 가능
		
		StringBuffer sb = new StringBuffer();
		while(true) {
			
			String data = br.readLine(); //라인단위로 읽기
			if(data == null) break; 
			
			sb.append(data); // 라인단위 읽은 데이터 스트링버퍼에 넣는다		
				
		}
	
		JSONObject json = new JSONObject(sb.toString());
		JSONArray posterArray = json.getJSONArray("results");
		
		
		for(int i=0; i<posterArray.length(); i++) {
			String poster_path; 
			JSONObject ps = posterArray.getJSONObject(i);
			
			String id = ps.getDouble("id")+"";
			String title = ps.getString("title");
			String overview = ps.getString("overview");
			String popularity = ps.getDouble("popularity")+"";
			try {
				poster_path = ps.getString("poster_path");
			} catch (Exception e) {
				poster_path = "/1ZNOOMmILNUzVYbzG1j7GYb5bEV.jpg";
			}
			String release_date = ps.getString("release_date");
			String vote_average = ps.getDouble("vote_average")+"";
			
			PosterVo vo = new PosterVo(id,title, overview, popularity,poster_path,release_date,vote_average);
			
			list.add(vo);

			if(i>=4)break;
		}
		
	return list;
	}

	
	
}

