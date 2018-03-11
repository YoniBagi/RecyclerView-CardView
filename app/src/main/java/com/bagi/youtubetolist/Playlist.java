package com.bagi.youtubetolist;

import java.util.ArrayList;

/**
 * Created by asusX541u on 30/01/2018.
 */

public class Playlist  {
    private String namePlayList;
    private ArrayList<Video> videos;

    @Override
    public String toString() {
        return namePlayList;
    }

    public String getNamePlayList() {
        return namePlayList;
    }

    public void setNamePlayList(String namePlayList) {
        this.namePlayList = namePlayList;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }

    public Playlist(String namePlayList, ArrayList<Video> videos) {

        this.namePlayList = namePlayList;
        this.videos = videos;
    }
}
