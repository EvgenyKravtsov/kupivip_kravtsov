package com.kupivipkravtsov.ui.translation;

import com.kupivipkravtsov.domain.entity.FavoriteTranslation;
import com.kupivipkravtsov.domain.entity.Translation;
import com.kupivipkravtsov.domain.interactor.AddTranslationToFavoritesInteractor;
import com.kupivipkravtsov.domain.interactor.GetSupportedLanguagesInteractor;
import com.kupivipkravtsov.domain.interactor.TranslateInteractor;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.BehaviorSubject;

public final class TranslationViewModel {

    private static final String TAG = TranslationViewModel.class.getSimpleName();

    private final BehaviorSubject<String> textToTranslate = BehaviorSubject.create();
    private final BehaviorSubject<String> textTranslated = BehaviorSubject.create();
    private final BehaviorSubject<List<String>> supportedLanguages = BehaviorSubject.create();

    private final TranslateInteractor translateInteractor;
    private final AddTranslationToFavoritesInteractor addTranslationToFavoritesInteractor;

    private String languageCode = "en";

    ////

    public TranslationViewModel(TranslateInteractor translateInteractor,
                                AddTranslationToFavoritesInteractor addTranslationToFavoritesInteractor,
                                GetSupportedLanguagesInteractor getSupportedLanguagesInteractor) {
        this.translateInteractor = translateInteractor;
        this.addTranslationToFavoritesInteractor = addTranslationToFavoritesInteractor;
        getSupportedLanguagesInteractor.execute().observeOn(AndroidSchedulers.mainThread()).subscribe(supportedLanguages::onNext);
    }

    ////

    public BehaviorSubject<String> getTextToTranslate() {
        return textToTranslate;
    }

    BehaviorSubject<String> getTextTranslated() {
        return textTranslated;
    }

    BehaviorSubject<List<String>> getSupportedLanguages() {
        return supportedLanguages;
    }

    void onLanguageSelected(String code) {
        languageCode = code;
        if (textToTranslate.getValue() != null) translate(textToTranslate.getValue());
    }

    ////

    void onTextToTranslateChanged(String textToTranslate) {
        translate(textToTranslate);
    }

    void onAddTranslationToFavorites() {
        String textToTranslate = this.textToTranslate.getValue();
        String textTranslated = this.textTranslated.getValue();
        if (textToTranslate == null || textTranslated == null) return;
        if (textToTranslate.equals("") || textTranslated.equals("")) return;
        addTranslationToFavoritesInteractor.execute(new FavoriteTranslation(languageCode, textToTranslate, textTranslated));
    }

    //// PRIVATE

    private void translate(String textToTranslate) {
        if (textToTranslate.equals("")) return;
        this.textToTranslate.onNext(textToTranslate);
        this.translateInteractor.execute(new Translation(languageCode, textToTranslate))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(textTranslated::onNext);
    }
}
