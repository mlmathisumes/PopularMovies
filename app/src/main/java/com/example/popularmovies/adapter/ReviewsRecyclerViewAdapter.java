package com.example.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.model.Review;

import java.util.ArrayList;

public class ReviewsRecyclerViewAdapter extends RecyclerView.Adapter<ReviewsRecyclerViewAdapter.ReviewsRecyclerViewHolder>{
    private ArrayList<Review> reviewArrayList;
    private LayoutInflater inflater;
    private Context context;

    public ReviewsRecyclerViewAdapter(Context context, ArrayList<Review> reviewArrayList) {
        this.reviewArrayList = reviewArrayList;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.comment_list_item, parent, false);
        return new ReviewsRecyclerViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsRecyclerViewHolder holder, final int position) {
        if(reviewArrayList.size() > 0){
            Review review = reviewArrayList.get(position);
            holder.author.setText(review.getAuthor());
            holder.content.setText(review.getContent());

            holder.link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(reviewArrayList.get(position).getUrl().toString()));
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return reviewArrayList.size();
    }

    public static class ReviewsRecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView author;
        TextView content;
        TextView link;

        ReviewsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            content = itemView.findViewById(R.id.comment);
            link = itemView.findViewById(R.id.review_link);
        }
    }
}
