package com.example.learnmaori;

public class Number {
    private int mId;
    private String mIcon;
    private String mMaoriTranslation;
    private String mAudio;

    public Number(int Id, String Icon, String MaoriTranslation, String Audio) {
        this.mId = Id;
        this.mIcon = Icon;
        this.mMaoriTranslation = MaoriTranslation;
        this.mAudio = Audio;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public String getMaoriTranslation() {
        return mMaoriTranslation;
    }

    public void setMaoriTranslation(String mMaoriTranslation) {
        this.mMaoriTranslation = mMaoriTranslation;
    }

    public String getAudio() {
        return mAudio;
    }

    public void setAudio(String mAudio) {
        this.mAudio = mAudio;
    }
}
