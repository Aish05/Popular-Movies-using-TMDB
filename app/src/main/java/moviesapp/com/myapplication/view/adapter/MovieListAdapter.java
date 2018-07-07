package moviesapp.com.myapplication.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import moviesapp.com.myapplication.MovieItemClick;
import moviesapp.com.myapplication.R;
import moviesapp.com.myapplication.beans.PopularMain;
import moviesapp.com.myapplication.beans.PopularResult;
import moviesapp.com.myapplication.view.MainActivity;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {

    Context context;
    MovieListViewHolder movieListViewHolder;
    MainActivity mainActivity;
    List<PopularResult> movieDataModel;
    private MovieItemClick mListener;

    public MovieListAdapter(Context context, List<PopularResult> movieDataModels) {
        this.context = context;
        this.movieDataModel = movieDataModels;
        mListener = (MovieItemClick) context;

    }

    @NonNull
    @Override
    public MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);
        movieListViewHolder = new MovieListViewHolder(view);
        return movieListViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MovieListViewHolder holder, final int position) {
        Picasso.with(context)
                .load("http://image.tmdb.org/t/p/w500/" + movieDataModel.get(position).getPosterPath())
                .error(R.drawable.default_movie)
                .placeholder(R.drawable.default_movie)
                .into(holder.tvItemImage);

        holder.tvItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "Position" + position, Toast.LENGTH_LONG).show();
                mListener.onMoveItemClicked(position, movieDataModel.get(position));

            }
        });
    }


    @Override
    public int getItemCount() {
        if (movieDataModel != null && movieDataModel.size() > 0) {
            return movieDataModel.size();
        }
        return 0;
    }

    public void setMovieResults(List<PopularResult> movieResults) {
        this.movieDataModel = movieResults;
    }


    public class MovieListViewHolder extends RecyclerView.ViewHolder {
        public ImageView tvItemImage;

        public MovieListViewHolder(View itemView) {
            super(itemView);
            tvItemImage = itemView.findViewById(R.id.tvItemImage);
        }
    }
}
