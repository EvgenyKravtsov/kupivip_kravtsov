package com.kupivipkravtsov.ui.translation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.kupivipkravtsov.R;
import com.kupivipkravtsov.ui.ViewModelFactory;

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
    @BindView(R.id.text_translated_text_view)
    TextView textTranslatedTextView;

    ////

    @OnClick(R.id.add_to_favorites_button)
    public void onClickAddToFavoritesButton() {
        viewModel.onAddTranslationToFavorites();
    }

    ////

    private static final String TAG = TranslationFragment.class.getSimpleName();

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
}