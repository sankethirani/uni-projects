package com.example.mithub.Model;

public class Comment {
    String commenter_user_id;
    String commenter_name;
    String comment_message;
    String comment_date;
    String comment_time;


    public Comment() {
    }

    public Comment(String commenter_user_id, String commenter_name, String comment_message, String comment_date, String comment_time) {
        this.commenter_user_id = commenter_user_id;
        this.commenter_name = commenter_name;
        this.comment_message = comment_message;
        this.comment_date = comment_date;
        this.comment_time = comment_time;
    }

    public String getCommenter_name() {
        return commenter_name;
    }

    public void setCommenter_name(String commenter_name) {
        this.commenter_name = commenter_name;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public String getCommenter_user_id() {
        return commenter_user_id;
    }

    public void setCommenter_user_id(String commenter_user_id) {
        this.commenter_user_id = commenter_user_id;
    }

    public String getComment_message() {
        return comment_message;
    }

    public void setComment_message(String comment_message) {
        this.comment_message = comment_message;
    }
}
