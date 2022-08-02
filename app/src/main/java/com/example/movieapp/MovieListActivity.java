package com.example.movieapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.widget.Toast;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.example.movieapp.adapters.MovieRecyclerView;
import com.example.movieapp.adapters.OnMovieListener;
import com.example.movieapp.models.MovieModel;
import com.example.movieapp.network.MovieApi;
import com.example.movieapp.network.Service;
import com.example.movieapp.response.MovieSearchResponse;
import com.example.movieapp.utils.Credentials;
import com.example.movieapp.utils.StatusCode;
import com.example.movieapp.viewmodels.MovieListViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {
    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerAdapter;


    private MovieListViewModel movieListViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // SearchView
        setupSearchView();

        recyclerView = findViewById(R.id.recyclerView);
        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        ConfigureRecyclerView();
        ObserveAnyChange();



    }

    private void ObserveAnyChange(){
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                // Observing this any data changing

                if(movieModels != null){
                    for(MovieModel movieModel:movieModels){
                        Log.v("Tag","onChanged : "+movieModel.getTitle());

                        movieRecyclerAdapter.setmMovies(movieModels);
                    }
                }
            }
        });
    }

    private void ConfigureRecyclerView(){
        movieRecyclerAdapter = new MovieRecyclerView(this);
        recyclerView.setAdapter(movieRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1)){
                    movieListViewModel.searchNextPage();
                }
            }
        });
    }

    @Override
    public void onMovieClick(int position) {
        Intent intent = new Intent(this,MovieDetails.class);
        intent.putExtra("movie",movieRecyclerAdapter.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }

    private void setupSearchView(){
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMovieApi(
                        query,
                        1,
                        "tr"
                );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

//    private void GetRetrofitResponse() {
//        MovieApi movieApi = Service.getMovieApi();
//
//        Call<MovieSearchResponse> responseCall =  movieApi.searchMovie(
//                Credentials.API_KEY,
//                "Batman",
//                1,
//                "tr"
//        );
//
//        responseCall.enqueue(new Callback<MovieSearchResponse>() {
//            @Override
//            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
//                try{
//                if(response.code() == StatusCode.OK){
//                    Log.v("Tag","the response"+response.body().toString());
//                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
//                    for(MovieModel movie: movies){
//                        Log.v("Tag","Liste"+movie.getTitle());
//                    }
//                }
//                else if(response.code() == StatusCode.NOT_FOUND){
//
//                }
//                else if(response.code() == StatusCode.INTERNAL_SERVER_ERROR){
//
//                }
//                else{
//                    try{
//                        Log.v("Body Error","Error"+response.errorBody().toString());
//                    }
//                    catch (Exception ecx){
//                        Log.v("Body Error","Error"+response.errorBody().toString());
//                    }
//                }
//                }
//                   catch (Exception ex){
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
//
//            }
//        });
//    }
//
//    private void GetRetrofitResponseAccordingToId(){
//        MovieApi movieApi = Service.getMovieApi();
//        Call<MovieModel> responseCall =
//                movieApi.getMovie(
//                        550,
//                        Credentials.API_KEY);
//
//        responseCall.enqueue(new Callback<MovieModel>() {
//            @Override
//            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
//                try{
//                    if(response.code() == StatusCode.OK){
//                      MovieModel movie = response.body();
//                      Log.v("Tag","GetRetrofitResponseAccordingToId"+movie.getTitle());
//                    }
//                    else if(response.code() == StatusCode.NOT_FOUND){
//
//                    }
//                    else if(response.code() == StatusCode.INTERNAL_SERVER_ERROR){
//
//                    }
//                    else{
//                        try{
//                            Log.v("Body Error","Error"+response.errorBody().toString());
//                        }
//                        catch (Exception ecx){
//                            Log.v("Body Error","Error"+response.errorBody().toString());
//                        }
//                    }
//                }
//                catch (Exception ex){
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieModel> call, Throwable t) {
//
//            }
//        });
//
//    }
}