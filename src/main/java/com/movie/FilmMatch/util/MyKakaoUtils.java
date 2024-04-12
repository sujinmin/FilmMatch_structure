

package com.movie.FilmMatch.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.movie.FilmMatch.vo.TheaterVo;

public class MyKakaoUtils{

    public static  List<TheaterVo> search_map(double latitude, double longitude, String query) throws Exception {


        List<TheaterVo> list = new ArrayList<>(); // 리스트는 반복문 외부에서 초기화
    
        StringBuffer sb = new StringBuffer();
    
        query = URLEncoder.encode(query, "utf-8");
        
        String clientId = "KakaoAK 4aa4d275595dbaeec3592e8541f140c4";
        String urlStr = String.format("https://dapi.kakao.com/v2/local/search/keyword.json?query=%s&y=%.10f&x=%.10f&radius=5000&sort=distance",query ,latitude ,longitude);
        
        URL url = new URL(urlStr);
        
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
    
        urlConn.setRequestProperty("Authorization", clientId); 
        
        urlConn.connect();
            
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(),"utf-8"));
    
        while(true) {
            
            String data = br.readLine();
            if(data==null) break;
            
            sb.append(data);
        }			
        
    
        JSONObject json = new JSONObject(sb.toString());
        JSONArray theaterArray = json.getJSONArray("documents");
        
        for(int i=0; i<theaterArray.length(); i++) {
            JSONObject theater = theaterArray.getJSONObject(i);
            
            String category_group_code = theater.getString("category_group_code");

            if ("CT1".equals(category_group_code)) {
                String address_name = theater.getString("address_name");
                String distance = theater.getString("distance");
                String id = theater.getString("id");
                String phone = theater.getString("phone");
                String place_name = theater.getString("place_name");
                String place_url = theater.getString("place_url");
                String road_address_name = theater.getString("road_address_name");
                double x = theater.getDouble("x");
                double y = theater.getDouble("y");

                TheaterVo vo = new TheaterVo(category_group_code, address_name, distance, id, phone, place_name, place_url, road_address_name, x, y);

                list.add(vo);
            }
        }

        return list;
    }
}