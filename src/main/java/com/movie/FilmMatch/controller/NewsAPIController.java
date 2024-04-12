package com.movie.FilmMatch.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.movie.FilmMatch.vo.NewsVo;




@Controller
@RequestMapping("/main/")
public class NewsAPIController {
	
	
	@RequestMapping("/newsapi.do")
	public static List<NewsVo> search_news() throws Exception{


        List<NewsVo> list = new ArrayList<NewsVo>();
		
		String clientId = "B_tQ0p48ck9dp9iFadBP";
		String clientSecret = "fGyNPcA96T";

		String qu = URLEncoder.encode("극장영화","utf-8");
		//String qu = URLEncoder.encode("movie");
		//String qu = "극장영화";

		
		String urlStr = "https://openapi.naver.com/v1/search/news.json?query=" + qu + "&start=1&display=3&sorting=pubDate";
		
		URL url = new URL(urlStr);
		
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		
		urlConn.setRequestProperty("X-Naver-Client-Id", clientId); 
		
		urlConn.setRequestProperty("X-Naver-Client-Secret", clientSecret);
		
		urlConn.setRequestProperty("Content-Type", "application/json");
		
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
		JSONArray newsArray = json.getJSONArray("items");
		
		
		for(int i=0; i<newsArray.length();i++) {
			
			JSONObject nw = newsArray.getJSONObject(i);
			
			String title = nw.getString("title");
			String originallink = nw.getString("originallink");
			String link = nw.getString("link");
			String description = nw.getString("description");
			String pubDate = nw.getString("pubDate");
			
			NewsVo vo = new NewsVo(title, originallink, link,description,pubDate);
		
			list.add(vo);
		}
		
	return list;
	}


	
}
