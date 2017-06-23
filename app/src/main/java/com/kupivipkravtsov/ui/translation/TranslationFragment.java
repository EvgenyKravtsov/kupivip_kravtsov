package com.kupivipkravtsov.ui.translation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.kupivipkravtsov.R;
import com.kupivipkravtsov.domain.entity.Language;
import com.kupivipkravtsov.ui.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public final class TranslationFragment extends Fragment {

    @BindView(R.id.text_to_translate_edit_text)
    EditText textToTranslateEditText;
    @BindView(R.id.supported_languages_spinner)
    Spinner supportedLanguagesSpinner;
    @BindView(R.id.text_translated_text_view)
    TextView textTranslatedTextView;

    ////

    @OnClick(R.id.add_to_favorites_button)
    public void onClickAddToFavoritesButton() {
        viewModel.onAddTranslationToFavorites();
    }

    ////

    private TranslationViewModel viewModel;
    private CompositeDisposable viewModelSubscription;

    ////

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelFactory.provideTranslationViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translation, container, false);
        ButterKnife.bind(this, view);
        setupTextToTranslateEditText();
        return view;
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
        viewModelSubscription.add(viewModel.getTextTranslated().subscribe(this::displayTextTranslated));
        viewModelSubscription.add(viewModel.getSupportedLanguages().subscribe(this::displaySupportedLanguages));
    }

    private void unbindViewModel() {
        viewModelSubscription.dispose();
    }

    //// SETUP

    private void setupTextToTranslateEditText() {
        textToTranslateEditText.setText(viewModel.getTextToTranslate().getValue());
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            textToTranslateEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    emitter.onNext(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });}).debounce(400, java.util.concurrent.TimeUnit.MILLISECONDS)
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(textToTranslate -> viewModel.onTextToTranslateChanged(textToTranslate));
    }

    //// DISPLAY

    private void displayTextTranslated(String textTranslated) {
        textTranslatedTextView.setText(textTranslated);
    }

    private void displaySupportedLanguages(List<Language> supportedLanguages) {
        List<String> languageDescriptions = new ArrayList<>();
        for (Language language : supportedLanguages) languageDescriptions.add(language.getDescription());

        int selectedLanguageIndex = 0;
        for (int i = 0; i < supportedLanguages.size(); i++) {
            if (supportedLanguages.get(i).getCode().equals(viewModel.getLanguageCode())) {
                selectedLanguageIndex = i;
                break;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_supported_language, languageDescriptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        supportedLanguagesSpinner.setAdapter(adapter);
        supportedLanguagesSpinner.setSelection(selectedLanguageIndex);

        supportedLanguagesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.onLanguageSelected(supportedLanguages.get(i).getCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
