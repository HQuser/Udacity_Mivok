package com.example.android.miwok;

/**
 * Created by abdur on 9/14/17.
 */

public class Word {
    private static final int NO_IMAGE_STATE = -1;
    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mImageRecourseId = NO_IMAGE_STATE;

    public Word(String mDefaultTranslation, String mMiwokTranslation, int mImageRecourseId) {
        this.mDefaultTranslation = mDefaultTranslation;
        this.mMiwokTranslation = mMiwokTranslation;
        this.mImageRecourseId = mImageRecourseId;
    }

    public Word(String mDefaultTranslation, String mMiwokTranslation) {

        this.mDefaultTranslation = mDefaultTranslation;
        this.mMiwokTranslation = mMiwokTranslation;
    }

    public int getmmageRecourseId() {
        return mImageRecourseId;
    }

    public String getdefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getmiwokTranslation() {
        return mMiwokTranslation;
    }

    public boolean hasImage() {
        return mImageRecourseId != NO_IMAGE_STATE;
    }
}
