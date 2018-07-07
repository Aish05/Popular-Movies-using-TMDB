package moviesapp.com.myapplication.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import moviesapp.com.myapplication.R;
import moviesapp.com.myapplication.beans.PopularResult;
import moviesapp.com.myapplication.data.MoviesDbHelper;

public class MovieDetailsFragment extends Fragment {

    private PopularResult movieObj;
    private ImageView imgPoster;
    private TextView tvOverview, tvReleaseDate, tvLanguage, tvTitle,tvTitleValue;
    private RatingBar movieRating;
    private FloatingActionButton fabFavorite;
    private Context mContext;
    private MoviesDbHelper moviesDbHelper;
    private boolean isFav = false;



    public static MovieDetailsFragment newInstance(PopularResult popularResult) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("MovieObj", popularResult);
        fragment.setArguments(args);
        return fragment;
    }

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            movieObj = getArguments().getParcelable(getResources().getString(R.string.movie_obj));
        }
        mContext = getActivity();
        moviesDbHelper = new MoviesDbHelper(mContext);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        setupViews(view);

        if (movieObj != null) {
            fillData();
        }

        return view;
    }

    private void fillData() {
        float rating = (float) ((5 * movieObj.getVoteAverage()) / 10);
        movieRating.setRating(rating);
        tvReleaseDate.setText(movieObj.getReleaseDate());
        Locale current = new Locale(movieObj.getOriginalLanguage());
        tvLanguage.setText(current.getDisplayLanguage());
        tvOverview.setText(movieObj.getOverview());
    }

    private void setupViews(View view) {
        tvTitle = view.findViewById(R.id.tvTitle);
        tvTitleValue = view.findViewById(R.id.tvTitleValue);
        tvTitleValue.setText(movieObj.getTitle());

        imgPoster = view.findViewById(R.id.img_movie_poster);
        Picasso.with(getActivity())
                .load("http://image.tmdb.org/t/p/w780/" + movieObj.getPosterPath())
                .placeholder(getResources().getDrawable(R.drawable.movie_default))
                .error(getResources().getDrawable(R.drawable.movie_default))
                .into(imgPoster);

        tvOverview = view.findViewById(R.id.txtOverview);

        movieRating = view.findViewById(R.id.movieRating);
        movieRating.setIsIndicator(true);

        tvReleaseDate = view.findViewById(R.id.txtReleaseDateValue);
        tvLanguage = view.findViewById(R.id.txtLanguageValue);

        fabFavorite = (FloatingActionButton) view.findViewById(R.id.fabFavorite);

        isFav = moviesDbHelper.CheckIsDataAlreadyInDBorNot(movieObj);
        if (isFav) {
            fabFavorite.setImageResource(R.drawable.ic_favorite_red);
        } else {
            fabFavorite.setImageResource(R.drawable.ic_favorite_white);
        }

        fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!isFav && movieObj!= null){
                    isFav = true;
                    fabFavorite.setImageResource(R.drawable.ic_favorite_red);

                    moviesDbHelper.insertMovie(movieObj,mContext);
                    Toast.makeText(mContext,"Movie Added to Favourites",Toast.LENGTH_SHORT).show();
                    Log.d("TotAL NO OF ROWS", "after insert: "+moviesDbHelper.numberOfRows());
                } else {
                    isFav = false;
                    fabFavorite.setImageResource(R.drawable.ic_favorite_white);
                    moviesDbHelper.deleteMovie(movieObj,mContext);
                    Toast.makeText(mContext,"Movie Deleted from Favourites",Toast.LENGTH_SHORT).show();
                    Log.d("TotAL NO OF ROWS", "after delete: "+moviesDbHelper.numberOfRows());
                }

            }
        });

    }
}
