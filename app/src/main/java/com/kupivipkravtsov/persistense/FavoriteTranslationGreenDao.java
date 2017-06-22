package com.kupivipkravtsov.persistense;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public final class FavoriteTranslationGreenDao {

    @NotNull
    private String textToTranslate;
    @NotNull
    private String textTranslated;
    @Generated(hash = 572454228)
    public FavoriteTranslationGreenDao(@NotNull String textToTranslate,
            @NotNull String textTranslated) {
        this.textToTranslate = textToTranslate;
        this.textTranslated = textTranslated;
    }
    @Generated(hash = 437983763)
    public FavoriteTranslationGreenDao() {
    }
    public String getTextToTranslate() {
        return this.textToTranslate;
    }
    public void setTextToTranslate(String textToTranslate) {
        this.textToTranslate = textToTranslate;
    }
    public String getTextTranslated() {
        return this.textTranslated;
    }
    public void setTextTranslated(String textTranslated) {
        this.textTranslated = textTranslated;
    }
}
