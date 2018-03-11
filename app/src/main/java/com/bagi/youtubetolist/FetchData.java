package com.bagi.youtubetolist;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by asusX541u on 29/01/2018.
 */

public class FetchData extends AsyncTask<Void, Void, Void> {
    private String data ="";
    private ArrayList<Video> videos;
    private ArrayList<Playlist> playlists;
    private Context context;
    private WeakReference<MainActivity> mainActivityWeakReference;

    public FetchData(Context context, MainActivity activity) {
        this.context = context;
        this.mainActivityWeakReference = new WeakReference<MainActivity>(activity);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            playlists = new ArrayList<>();
            URL url = new URL("https://api.myjson.com/bins/genpx");
            InputStream inputStream = url.openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while (line != null){
                line = bufferedReader.readLine();
                data += line;
            }


            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArrayPlaylist = jsonObject.getJSONArray("Playlists");
            for (int i=0; i<jsonArrayPlaylist.length(); i++){
                videos = new ArrayList<>();
                JSONArray jsonArrayItem = jsonArrayPlaylist.getJSONObject(i).getJSONArray("ListItems");
                for (int j=0; j<jsonArrayItem.length(); j++){
                    JSONObject jsonObjectItem = jsonArrayItem.getJSONObject(j);
                    String title =jsonObjectItem.getString("Title");
                    String link = jsonObjectItem.getString("link");
                    String thumb = jsonObjectItem.getString("thumb");
                    Video video = new Video(title, link, thumb);
                    videos.add(video);
                }
                Playlist playlist = new Playlist(jsonArrayPlaylist.getJSONObject(i).getString("ListTitle"),videos);
                playlists.add(playlist);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        CustomAdapter customAdapter = new CustomAdapter(playlists, context);
        MainActivity mainActivity = mainActivityWeakReference.get();
        //MainActivity mainActivity = new MainActivity();
        if(mainActivity != null)
            mainActivity.getRecyclerView().setAdapter(customAdapter);

    }
}
