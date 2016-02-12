package com.example.aparna.newsimagesearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by aparna on 2/9/16.
 */
public class Article implements Serializable{
    String webUrl;
    String headline;
    String thumbnail;
    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getThumbnail() {
        return thumbnail;
    }



    public Article(JSONObject jsonObject) {
        try {
            this.webUrl = jsonObject.getString("web_url");
            this.headline = jsonObject.getJSONObject("headline").getString("main");

            JSONArray multimedia = jsonObject.getJSONArray("multimedia");
            if(multimedia.length() > 0) {
                JSONObject multimediaJson = multimedia.getJSONObject(0);
                this.thumbnail = "http://www.nytimes.com/" + multimediaJson.getString("url");
            }else {
                this.thumbnail = "";
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Article> fromJSONArray(JSONArray array) {
       ArrayList<Article> results = new ArrayList<>();

        for (int x = 0; x < array.length(); x++) {
            try {
                results.add(new Article(array.getJSONObject(x)));
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  results;
    }

//    public static ArrayList<Article> createArticlesList(String query, int i, int page) {
//
//        AsyncHttpClient client = new AsyncHttpClient();
//        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
//
//        RequestParams params = new RequestParams();
//        params.put("api-key", "f306189c1f5f78fe86775fd533f26965:13:74339278");
//        params.put("page", page);
//        params.put("q", query);
//
//         ArrayList<Article> moreArticles = new ArrayList<>();
//        client.get(url, params, new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                Log.d("DEBUG", response.toString());
//                JSONArray articleJsonResults = null;
//
//                try {
//                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
//                    moreArticles = fromJSONArray(articleJsonResults);
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        return null;
//    }
}
