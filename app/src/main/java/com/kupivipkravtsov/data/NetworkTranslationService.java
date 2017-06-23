package com.kupivipkravtsov.data;

import com.kupivipkravtsov.domain.entity.Language;
import com.kupivipkravtsov.domain.entity.Translation;

import java.util.List;

import io.reactivex.Observable;

public interface NetworkTranslationService {

    Observable<String> translate(Translation translation);

    Observable<List<Language>> requestSupportedLanguages();
}
