package com.movie.FilmMatch.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.movie.FilmMatch.vo.CompanyVo;
import com.movie.FilmMatch.vo.DetailMovieTVVo;


public class IdAPIUtils {
	
	public static List<DetailMovieTVVo> search_id(String id,String media_type) throws Exception {

            List<DetailMovieTVVo> list = new ArrayList<DetailMovieTVVo>();


            String urlStr = "";
            String clientId = "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkNzE5ZDc5YjU5NDNkYTgxNWEyYWY5NDRhNjNlZjI4MSIsInN1YiI6IjY1ZjAxMWE0NjZhN2MzMDBjYWRkYmJhZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.xFFFvCXvNIZSbwFYVN591kIVeKIQnkMs7nAQ6CAxLDA";
            String clientSecret = "d719d79b5943da815a2af944a63ef281";
            String accept = "accept: application/json";
            URL url = null;
            HttpURLConnection urlConn=null;
            BufferedReader br = null;

            if(media_type.equals("movie") || media_type.equals("undefined") ){
               
                urlStr = "https://api.themoviedb.org/3/movie/"+ id +"?language=ko";

                try{
                

                    url = new URL(urlStr);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setRequestProperty("key", clientSecret);
                    urlConn.setRequestProperty("Authorization", clientId);
                    urlConn.setRequestProperty("accept", accept);
                    urlConn.connect();
                    InputStream is = urlConn.getInputStream();
                     InputStreamReader isr = new InputStreamReader(is, "utf-8");
                      br = new BufferedReader(isr);
    
                    StringBuffer sb = new StringBuffer();
                    String data;
                    while ((data = br.readLine()) != null) {
                        sb.append(data);
                    }
    
                    JSONObject json = new JSONObject(sb.toString());
    
                    for (int s = 0; s < json.length(); s++) {
                        JSONObject info= new JSONObject(sb.toString());
    
                  
                        String budget = "";
                        try {
                            budget = info.getDouble("budget")+"";
                        } catch (Exception e) {
                            budget = info.getString("first_air_date");
                        }
    
                        JSONArray infoArray = info.getJSONArray("genres");
                        List<String> genres_list = new ArrayList<>();
                        for (int j = 0; j < infoArray.length(); j++) {
                            String name = infoArray.getJSONObject(j).getString("name");
    
                            genres_list.add(name);
                        }
    
                        String original_language = info.getString("original_language");
                        String original_title = "";
                        try {
                            original_title = info.getString("original_title");
                        } catch (Exception e) {
                            original_title = info.getString("original_name");
                        }
                        String overview = info.getString("overview");
                        String popularity = info.getDouble("popularity")+"";
                        String poster_path = info.getString("poster_path");
    
                        JSONArray compArray = info.getJSONArray("production_companies");
                        List<CompanyVo> company_list = new ArrayList<CompanyVo>();
                        for (int i = 0; i < compArray.length(); i++) {
    
                           
                            String logo_path = compArray.getJSONObject(i).getString("name");
                            String name = compArray.getJSONObject(i).getString("name");
                            String origin_country = compArray.getJSONObject(i).getString("name");
    
                            CompanyVo vo = new CompanyVo(logo_path, name, origin_country);
    
                            company_list.add(vo);
                        }
    
                        JSONArray counArray = info.getJSONArray("production_countries");
                        List<String> countries_list = new ArrayList<>();
                        for (int m = 0; m < counArray.length(); m++) {
    
                            String english_name = "";
                            if (!counArray.getJSONObject(m).isNull("english_name")) { // profile_path가 null이 아닌 경우에만 가져옴
                                english_name = counArray.getJSONObject(m).getString("english_name");
                            } else {
                                english_name = counArray.getJSONObject(m).getString("name");
                            }
                            countries_list.add(english_name);
                        }
    
                        
                        String release_date = "";
                        try {
                            release_date = info.getString("release_date");
                        } catch (Exception e) {
                            release_date = info.getString("first_air_date");
                        }
                        String revenue = "";
                        try {
                            revenue = info.getDouble("revenue")+"";
                        } catch (Exception e) {
                            revenue = info.getString("last_air_date")+"";
                        }
                        String runtime = "";
                        try {
                            runtime = info.getDouble("runtime")+"";
                        } catch (Exception e) {
                            runtime = info.getString("title")+"";
                        }
                        DetailMovieTVVo vo = new DetailMovieTVVo(budget, original_language, original_title, overview, popularity, poster_path, release_date, revenue, runtime, genres_list, company_list, countries_list);
                        list.add(vo);
                        
                    }
    
                }catch(Exception e){
    
                }
    
    
                return list;

            }else{ 

                urlStr = "https://api.themoviedb.org/3/tv/"+ id + "?language=ko";

                try{
                

                    url = new URL(urlStr);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setRequestProperty("key", clientSecret);
                    urlConn.setRequestProperty("Authorization", clientId);
                    urlConn.setRequestProperty("accept", accept);
                    urlConn.connect();
                    InputStream is = urlConn.getInputStream();
                     InputStreamReader isr = new InputStreamReader(is, "utf-8");
                      br = new BufferedReader(isr);
    
                    StringBuffer sb = new StringBuffer();
                    String data;
                    while ((data = br.readLine()) != null) {
                        sb.append(data);
                    }
    
                    JSONObject json = new JSONObject(sb.toString());
    
                    for (int s = 0; s < json.length(); s++) {
                        JSONObject info= json;
    
                  
                        String budget = info.getString("first_air_date");
                        
                        JSONArray infoArray = info.getJSONArray("genres");
                        List<String> genres_list = new ArrayList<>();
                        for (int j = 0; j < infoArray.length(); j++) {
                            String name = infoArray.getJSONObject(j).getString("name");
    
                            genres_list.add(name);
                        }
    
                        String original_language = info.getString("original_language");
                        String original_title = info.getString("original_name");
                        String overview = info.getString("overview");
                        String popularity = info.getDouble("popularity")+"";
                        String poster_path = info.getString("poster_path");
    
                        JSONArray compArray = info.getJSONArray("production_companies");
                        List<CompanyVo> company_list = new ArrayList<CompanyVo>();
                        for (int i = 0; i < compArray.length(); i++) {
    
                           
                        String logo_path = compArray.getJSONObject(i).getString("name");
                        String name = compArray.getJSONObject(i).getString("name");
                        String origin_country = compArray.getJSONObject(i).getString("name");
    
                        CompanyVo vo = new CompanyVo(logo_path, name, origin_country);
    
                        company_list.add(vo);
                        }
    
                       
    
                        JSONArray counArray = info.getJSONArray("production_countries");
                        List<String> countries_list = new ArrayList<>();
                        for (int m = 0; m < counArray.length(); m++) {
    
                            String english_name = "";
                            if (!counArray.getJSONObject(m).isNull("english_name")) { // profile_path가 null이 아닌 경우에만 가져옴
                                english_name = counArray.getJSONObject(m).getString("name");
                            } else {
                                english_name = "";
                            }
                            countries_list.add(english_name);
                        }
    
                        
                        String release_date = "";
                        String revenue = "";
                        String runtime = "";
                       
                        DetailMovieTVVo vo = new DetailMovieTVVo(budget, original_language, original_title, overview, popularity, poster_path, release_date, revenue, runtime, genres_list, company_list, countries_list);
                        list.add(vo);
                        
                    }
    
                }catch(Exception e){
    
                }
    
    
                return list;
 
            }
            
            
        }
    }

   

	





