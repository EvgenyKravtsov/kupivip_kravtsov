package com.kupivipkravtsov.ui.favorites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kupivipkravtsov.R;
import com.kupivipkravtsov.domain.entity.FavoriteTranslation;
import com.kupivipkravtsov.ui.ViewModelFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public final class FavoritesFragment extends Fragment {

    @BindView(R.id.favorite_translations_recycler_view)
    RecyclerView favoritesTranslationRecyclerView;

    ////

    private FavoritesViewModel viewModel;
    private CompositeDisposable viewModelSubscription;
    private FavoriteTranslationsAdapter adapter;

    ////

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelFactory.provideFavoritesViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, view);
        setupFavoriteTranslationsRecyclerView();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) viewModel.onViewVisibleToUser();
    }

    @Override
    public void onStart() {
        super.onStart();
        bindViewModel();
    }

    @Override
    public void onStop() {
        super.onStop();
        unbindViewModel();
    }

    //// VIEW MODEL

    private void bindViewModel() {
        viewModelSubscription = new CompositeDisposable();
        viewModelSubscription.add(viewModel.getFavoriteTranslations().subscribe(this::displayFavoriteTranslations));
    }

    private void unbindViewModel() {
        viewModelSubscription.dispose();
    }

    //// SETUP

    private void setupFavoriteTranslationsRecyclerView() {
        adapter = new FavoriteTranslationsAdapter();
        favoritesTranslationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        favoritesTranslationRecyclerView.setHasFixedSize(true);
        favoritesTranslationRecyclerView.setAdapter(adapter);
    }

    //// DISPLAY

    private void displayFavoriteTranslations(List<FavoriteTranslation> favoriteTranslations) {
        adapter.updateFavoriteTranslations(favoriteTranslations);
    }
}
















































