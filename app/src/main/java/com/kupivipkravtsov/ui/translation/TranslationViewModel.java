package com.kupivipkravtsov.ui.translation;

import com.kupivipkravtsov.domain.entity.FavoriteTranslation;
import com.kupivipkravtsov.domain.interactor.AddTranslationToFavoritesInteractor;
import com.kupivipkravtsov.domain.interactor.TranslateInteractor;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.BehaviorSubject;

public final class TranslationViewModel {

    private static final String TAG = TranslationViewModel.class.getSimpleName();

    private final BehaviorSubject<String> textToTranslate = BehaviorSubject.create();
    private final BehaviorSubject<String> textTranslated = BehaviorSubject.create();

    private final TranslateInteractor translateInteractor;
    private final AddTranslationToFavoritesInteractor addTranslationToFavoritesInteractor;

    ////

    public TranslationViewModel(TranslateInteractor translateInteractor,
                                AddTranslationToFavoritesInteractor addTranslationToFavoritesInteractor) {
        this.translateInteractor = translateInteractor;
        this.addTranslationToFavoritesInteractor = addTranslationToFavoritesInteractor;
    }

    ////

    public BehaviorSubject<String> getTextToTranslate() {
        return textToTranslate;
    }

    BehaviorSubject<String> getTextTranslated() {
        return textTranslated;
    }

    ////

    void onTextToTranslateChanged(String textToTranslate) {
        if (textToTranslate.equals("")) return;
        this.textToTranslate.onNext(textToTranslate);
        this.translateInteractor.execute(textToTranslate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(textTranslated::onNext);
    }

    void onAddTranslationToFavorites() {
        String textToTranslate = this.textToTranslate.getValue();
        String textTranslated = this.textTranslated.getValue();
        if (textToTranslate == null || textTranslated == null) return;
        if (textToTranslate.equals("") || textTranslated.equals("")) return;
        addTranslationToFavoritesInteractor.execute(new FavoriteTranslation(textToTranslate, textTranslated));
    }
}
