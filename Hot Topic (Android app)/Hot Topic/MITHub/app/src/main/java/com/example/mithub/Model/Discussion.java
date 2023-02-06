package com.example.mithub.Model;

public class Discussion {
    //have to add attributes for image and categories
    public String poster_id;
    public String poster_full_name;
    public String poster_profile_image_url;
    public String upload_date;
    public String upload_time;
    public String discussion_message;
    public String discussion_image_url;

    public Discussion()
    {

    }

    public Discussion(String poster_id, String poster_full_name, String poster_profile_image_url, String upload_date, String upload_time, String discussion_message) {
        this.poster_id = poster_id;
        this.poster_full_name = poster_full_name;
        this.poster_profile_image_url = poster_profile_image_url;
        this.upload_date = upload_date;
        this.upload_time = upload_time;
        this.discussion_message = discussion_message;
    }

    public Discussion(String poster_id, String poster_full_name, String poster_profile_image_url, String upload_date, String upload_time, String discussion_message, String discussion_image_url) {
        this.poster_id = poster_id;
        this.poster_full_name = poster_full_name;
        this.poster_profile_image_url = poster_profile_image_url;
        this.upload_date = upload_date;
        this.upload_time = upload_time;
        this.discussion_message = discussion_message;
        this.discussion_image_url = discussion_image_url;
    }

    public String getPoster_id() {
        return poster_id;
    }

    public void setPoster_id(String poster_id) {
        this.poster_id = poster_id;
    }

    public String getPoster_full_name() {
        return poster_full_name;
    }

    public void setPoster_full_name(String poster_full_name) {
        this.poster_full_name = poster_full_name;
    }

    public String getPoster_profile_image_url() {
        return poster_profile_image_url;
    }

    public void setPoster_profile_image_url(String poster_profile_image_url) {
        this.poster_profile_image_url = poster_profile_image_url;
    }

    public String getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(String upload_date) {
        this.upload_date = upload_date;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public String getDiscussion_message() {
        return discussion_message;
    }

    public void setDiscussion_message(String discussion_message) {
        this.discussion_message = discussion_message;
    }

    public String getDiscussion_image_url() {
        return discussion_image_url;
    }

    public void setDiscussion_image_url(String discussion_image_url) {
        this.discussion_image_url = discussion_image_url;
    }


}
