package com.example.mithub;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewHolders {

    public static class DiscussionViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView, uploadDateTextView, uploadTimeTextView, discussionMessageTextView;
        CircleImageView posterProfileImage;
        ImageView discussionImageView;

        public DiscussionViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.discussion_poster_full_name);
            uploadDateTextView = itemView.findViewById(R.id.discussion_upload_date);
            uploadTimeTextView = itemView.findViewById(R.id.discussion_upload_time);
            discussionMessageTextView = itemView.findViewById(R.id.discussion_message);
            posterProfileImage = itemView.findViewById(R.id.dicsussion_poster_profile_image);
            discussionImageView = itemView.findViewById(R.id.discussion_image);
        }
    }

    public static class CommentsViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView, dateTextView, timeTextView, commentTextView;
        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.commenter_name_text_view);
            dateTextView = itemView.findViewById(R.id.comment_date_text_view);
            timeTextView = itemView.findViewById(R.id.comment_time_text_view);
            commentTextView = itemView.findViewById(R.id.comment_message_text_view);
        }
    }
}
