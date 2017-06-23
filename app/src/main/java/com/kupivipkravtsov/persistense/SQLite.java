package com.kupivipkravtsov.persistense;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kupivipkravtsov.data.FavoriteTranslationsStorage;
import com.kupivipkravtsov.domain.entity.FavoriteTranslation;
import com.kupivipkravtsov.domain.entity.Translation;

import io.reactivex.Observable;

public class SQLite extends SQLiteOpenHelper implements FavoriteTranslationsStorage {

    private static final String DATABASE_NAME = "translation_database";
    private static final int DATABASE_VERSION = 1;

    private static final String FAVORITE_TRANSLATIONS_TABLE_NAME = "favorite_translations";
    private static final String ID_COLUMN = "id";
    private static final String LANGUAGE_CODE_COLUMN = "language_code";
    private static final String TEXT_TO_TRANSLATE_COLUMN = "text_to_translate";
    private static final String TEXT_TRANSLATED_COLUMN = "text_translated";
    private String[] allColumns = { ID_COLUMN, LANGUAGE_CODE_COLUMN, TEXT_TO_TRANSLATE_COLUMN, TEXT_TRANSLATED_COLUMN };

    private static SQLite instance;

    private SQLiteDatabase database;

    ////

    private SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    public static synchronized SQLite getInstance(Context context) {
        if (instance == null) instance = new SQLite(context);
        return instance;
    }

    //// SQLITE

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL );",
                FAVORITE_TRANSLATIONS_TABLE_NAME, ID_COLUMN, LANGUAGE_CODE_COLUMN, TEXT_TO_TRANSLATE_COLUMN, TEXT_TRANSLATED_COLUMN));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(String.format("DROP TABLE ID EXISTS %s", FAVORITE_TRANSLATIONS_TABLE_NAME));
        onCreate(sqLiteDatabase);
    }

    //// FAVORITE TRANSLATIONS STORAGE

    @Override
    public void add(FavoriteTranslation favoriteTranslation) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LANGUAGE_CODE_COLUMN, favoriteTranslation.getLanguageCode());
        contentValues.put(TEXT_TO_TRANSLATE_COLUMN, favoriteTranslation.getTextToTranslate());
        contentValues.put(TEXT_TRANSLATED_COLUMN, favoriteTranslation.getTextTranslated());
        database.insert(FAVORITE_TRANSLATIONS_TABLE_NAME, null, contentValues);
    }

    @Override
    public Observable<String> get(Translation translation) {
        return Observable.create(emitter -> {
            Cursor cursor = database.query(
                    FAVORITE_TRANSLATIONS_TABLE_NAME,
                    allColumns,
                    LANGUAGE_CODE_COLUMN + " = " + translation.getLanguageCode() + " AND " +
                            TEXT_TO_TRANSLATE_COLUMN + " = " + translation.getTextToTranslate(),
                    null, null, null, null);

            cursor.moveToFirst();
            emitter.onNext(cursor.getString(2));
            cursor.close();
        });
    }

    @Override
    public Observable<FavoriteTranslation> getAll() {
        return Observable.create(emitter -> {
            Cursor cursor = database.query(
                    FAVORITE_TRANSLATIONS_TABLE_NAME,
                    allColumns,
                    null, null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                emitter.onNext(new FavoriteTranslation(cursor.getString(1), cursor.getString(2), cursor.getString(3)));
                cursor.moveToNext();
            }

            cursor.close();
            emitter.onComplete();
        });
    }

    @Override
    public void remove(FavoriteTranslation favoriteTranslation) {
        database.delete(FAVORITE_TRANSLATIONS_TABLE_NAME,
                LANGUAGE_CODE_COLUMN + " = '" + favoriteTranslation.getLanguageCode() + "' AND " +
                TEXT_TO_TRANSLATE_COLUMN + " = '" + favoriteTranslation.getTextToTranslate() + "'", null);
    }
}
