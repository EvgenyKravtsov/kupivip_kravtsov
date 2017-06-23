package com.kupivipkravtsov.ui.favorites;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kupivipkravtsov.R;
import com.kupivipkravtsov.domain.entity.FavoriteTranslation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

final class FavoriteTranslationsAdapter extends RecyclerView.Adapter<FavoriteTranslationsAdapter.ViewHolder> {

    interface Listener {

        void onRemoveFavoriteTranslation(FavoriteTranslation translation);
    }

    ////

    private final List<FavoriteTranslation> favoriteTranslations = new ArrayList<>();

    private Listener listener;

    ////

    void setListener(Listener listener) {
        this.listener = listener;
    }

    void removeListener() {
        this.listener = null;
    }

    void updateFavoriteTranslations(List<FavoriteTranslation> favoriteTranslations) {
        this.favoriteTranslations.clear();
        this.favoriteTranslations.addAll(favoriteTranslations);
        notifyDataSetChanged();
    }

    void removeFavoriteTranslation(FavoriteTranslation favoriteTranslation) {
        favoriteTranslations.remove(favoriteTranslation);
        notifyDataSetChanged();
    }

    //// RECYCLER ADAPTER

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_favorite_translation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FavoriteTranslation translation = favoriteTranslations.get(position);
        holder.textToTranslateTextView.setText(translation.getTextToTranslate());
        holder.textTranslatedTextView.setText(translation.getTextTranslated());
        holder.removeTranslationImageButton.setOnClickListener(
                view -> listener.onRemoveFavoriteTranslation(translation));
    }

    @Override
    public int getItemCount() {
        return favoriteTranslations.size();
    }

    ////

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_to_translate_text_view)
        TextView textToTranslateTextView;
        @BindView(R.id.text_translated_text_view)
        TextView textTranslatedTextView;
        @BindView(R.id.remove_translation_image_button)
        ImageButton removeTranslationImageButton;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
