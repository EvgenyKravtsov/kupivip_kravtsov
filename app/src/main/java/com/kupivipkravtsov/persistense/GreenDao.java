package com.kupivipkravtsov.persistense;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.kupivipkravtsov.data.FavoriteTranslationStorage;
import com.kupivipkravtsov.domain.entity.FavoriteTranslation;

import java.util.List;

import io.reactivex.Observable;

public final class GreenDao implements FavoriteTranslationStorage {

    private final DaoSession daoSession;

    ////

    public GreenDao(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(
                context, "sqlite_database", null);

        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }

    //// FAVORITE TRANSLATION STORAGE

    @Override
    public void add(FavoriteTranslation favoriteTranslation) {
        FavoriteTranslationGreenDaoDao dao = daoSession.getFavoriteTranslationGreenDaoDao();
        List<FavoriteTranslationGreenDao> items = dao.loadAll();

        for (FavoriteTranslationGreenDao item : items) {
            if (item.getTextToTranslate().equals(favoriteTranslation.getTextToTranslate())
                    && item.getTextTranslated().equals(favoriteTranslation.getTextTranslated())) return;
        }

        dao.insert(new FavoriteTranslationGreenDao(favoriteTranslation.getTextToTranslate(),
                                                   favoriteTranslation.getTextTranslated()));
    }

    @Override
    public Observable<String> get(String textToTranslate) {
        FavoriteTranslationGreenDaoDao dao = daoSession.getFavoriteTranslationGreenDaoDao();
        return Observable.fromIterable(dao.loadAll())
                .filter(item -> item.getTextToTranslate().equals(textToTranslate))
                .map(FavoriteTranslationGreenDao::getTextTranslated);
    }

    @Override
    public Observable<FavoriteTranslation> getFavoriteTranslations() {
        FavoriteTranslationGreenDaoDao dao = daoSession.getFavoriteTranslationGreenDaoDao();
        return Observable.fromIterable(dao.loadAll())
                .map(item -> new FavoriteTranslation(item.getTextToTranslate(), item.getTextTranslated()));
    }
}
