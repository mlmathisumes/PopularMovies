package com.example.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.popularmovies.R;
import com.example.popularmovies.model.Trailer;

import java.util.ArrayList;

public class TrailerRecyclerViewAdpater extends RecyclerView.Adapter<TrailerRecyclerViewAdpater.TrailerRecyclerViewHolder> {

    private ArrayList<Trailer> trailerArrayList;
    private LayoutInflater inflater;
    private Context context;

    public TrailerRecyclerViewAdpater(Context context, ArrayList<Trailer> trailerArrayList){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.trailerArrayList = trailerArrayList;
    }

    @NonNull
    @Override
    public TrailerRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View rootView = inflater.inflate(R.layout.trailer_list_item, parent, false);
        return new TrailerRecyclerViewHolder(rootView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerRecyclerViewHolder holder, final int position) {
        final Trailer trailer = trailerArrayList.get(position);
        if(trailer.getKey() != null){
            String url = "https://img.youtube.com/vi/" + trailer.getKey() + "/hqdefault.jpg";
            Glide.with(context).load(url).centerCrop().into(holder.iv_YouTubeImage);

            String title = trailer.getName();
            holder.tv_YouTubeTitle.setText(title);
        }else{
            Log.d("Adapter", "No trailers");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position != RecyclerView.NO_POSITION) {
                    Trailer trailer = trailerArrayList.get(position);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getKey()));
                    context.startActivity(intent);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return trailerArrayList.size();
    }

    static class TrailerRecyclerViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_YouTubeImage;
        TextView tv_YouTubeTitle;

        TrailerRecyclerViewHolder(@NonNull View itemView, TrailerRecyclerViewAdpater adpater) {
            super(itemView);
            iv_YouTubeImage = itemView.findViewById(R.id.iv_movie_backdrop);
            tv_YouTubeTitle = itemView.findViewById(R.id.tv_movie_title);
        }

    }


}
