package com.chandan.android.comicsworld.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chandan.android.comicsworld.R;
import com.chandan.android.comicsworld.model.movies.MoviesData;
import com.chandan.android.comicsworld.model.volumes.VolumesData;
import com.chandan.android.comicsworld.utilities.DateUtils;
import com.chandan.android.comicsworld.utilities.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MoviesListHolder> {

    final private MoviesClickListener mOnClickListener;

    private List<MoviesData> moviesDataList = new ArrayList<>();

    public interface MoviesClickListener {
        void onMoviesContentClick(int clickedItemIndex);
    }

    public MoviesListAdapter(MoviesClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public void updateVolumesData(List<MoviesData> moviesData) {
        this.moviesDataList = moviesData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviesListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movies_list_data;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new MoviesListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesListHolder moviesListHolder, int i) {
        Context context = moviesListHolder.moviesImageView.getContext();
        MoviesData moviesData = moviesDataList.get(i);

        moviesListHolder.movieNameTextView.setText(moviesData.getMovieName());

        String releaseDate = "Released On: " + DateUtils.getFormattedDate(moviesData.getMovieReleaseDate(), "MMM dd, yyyy");
        moviesListHolder.releaseDateTextView.setText(releaseDate);

        ImageUtils.displayImageFromUrlWithPlaceHolder(context, moviesData.getMoviesImage(),
                moviesListHolder.moviesImageView, R.drawable.image_placeholder, R.drawable.error_image_loading);
    }

    @Override
    public int getItemCount() {
        return moviesDataList.size();
    }

    class MoviesListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView moviesImageView;
        TextView movieNameTextView;
        TextView releaseDateTextView;

        MoviesListHolder(View itemView) {
            super(itemView);

            moviesImageView = (ImageView) itemView.findViewById(R.id.movies_imageView);
            movieNameTextView = (TextView) itemView.findViewById(R.id.movie_name_textview);
            releaseDateTextView = (TextView) itemView.findViewById(R.id.release_date_textview);

            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onMoviesContentClick(clickedPosition);
        }
    }
}
