package com.application.chatbot.viewHolders;

import android.view.View;

import com.application.chatbot.models.AttachmentTypes;

public class MessageTypingViewHolder extends BaseMessageViewHolder {

    public MessageTypingViewHolder(View itemView) {
        super(itemView, AttachmentTypes.NONE_TYPING);
    }

}
