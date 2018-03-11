package com.bagi.youtubetolist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by asusX541u on 30/01/2018.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private ArrayList<Playlist> playlists;
    private Context context;
    private ArrayList<Video> videos;

    public CustomAdapter(ArrayList<Playlist> playlists, Context context) {
        this.playlists = playlists;
        this.context = context;
        this.videos = new ArrayList<>();
        for (int i=0; i<(playlists.size());i++){
            for (int j=0; j<(playlists.get(i).getVideos().size()); j++)
                this.videos.add(playlists.get(i).getVideos().get(j));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_video, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Video video = videos.get(position);
        holder.nameVideoTV.setText(video.getTitle());
        Picasso.with(context).load(video.getThumb().replaceAll(" ", "")).resize(450,450).into(holder.thumbIV);
        setAnimation(holder.cardLL, position);
        holder.cardLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = new MainActivity();
                mainActivity.getPlayer().loadVideo(getIdVideo(video.getLink()));

            }
        });

    }

    private void setAnimation(View itemView, int position) {
        int lastPosition = -1;
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {

        return videos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nameVideoTV;
        ImageView thumbIV;
        LinearLayout cardLL;

        public ViewHolder(View itemView) {
            super(itemView);
            nameVideoTV = itemView.findViewById(R.id.nameVideoTV);
            thumbIV = itemView.findViewById(R.id.thumbIV);
            cardLL = itemView.findViewById(R.id.cardLL);
        }
    }
    public String getIdVideo (String link){return link.substring(link.lastIndexOf("=")+1);}
}
