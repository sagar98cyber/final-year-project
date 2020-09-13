package com.application.chatbot.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.chatbot.R;
import com.application.chatbot.models.AttachmentTypes;
import com.application.chatbot.models.Message;
import com.application.chatbot.viewHolders.BaseMessageViewHolder;
import com.application.chatbot.viewHolders.MessageSepratorViewHolder;
import com.application.chatbot.viewHolders.MessageTextViewHolder;
import com.application.chatbot.viewHolders.MessageTypingViewHolder;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<BaseMessageViewHolder> {

    private Context context;
    private ArrayList<Message> messages;
    private String myId;
    private View newMessage;

    public static final int MY = 0x00000000;
    public static final int OTHER = 0x0000100;

    public MessageAdapter(Context context, ArrayList<Message> messages, String myId, View newMessage) {
        this.context = context;
        this.messages = messages;
        this.myId = myId;
        this.newMessage = newMessage;
    }

    @NonNull
    @Override
    public BaseMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewType &= 0x00000FF;
        switch (viewType) {
            /*case AttachmentTypes.RECORDING:
                return new MessageAttachmentRecordingViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_attachment_recording, parent, false));
            case AttachmentTypes.AUDIO:
                return new MessageAttachmentAudioViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_attachment_audio, parent, false));
            case AttachmentTypes.CONTACT:
                return new MessageAttachmentContactViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_attachment_contact, parent, false));
            case AttachmentTypes.DOCUMENT:
                return new MessageAttachmentDocumentViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_attachment_document, parent, false));
            case AttachmentTypes.IMAGE:
                return new MessageAttachmentImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_attachment_image, parent, false));
            case AttachmentTypes.LOCATION:
                return new MessageAttachmentLocationViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_attachment_location, parent, false));
            case AttachmentTypes.VIDEO:
                return new MessageAttachmentVideoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_attachment_video, parent, false));*/
            case AttachmentTypes.NONE_TYPING:
                return new MessageTypingViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_typing, parent, false));
            case AttachmentTypes.SPERATOR:
                return new MessageSepratorViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_seperator, parent, false));
            case AttachmentTypes.NONE_TEXT:
            default:
                return new MessageTextViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_text, parent, false), newMessage);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseMessageViewHolder holder, int position) {
        holder.setData(messages.get(position), position);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() == 0) {
            return super.getItemViewType(position);
        } else {
            Message message = messages.get(position);
            int userType;
            if (message.getSenderId().equals(myId))
                userType = MY;
            else
                userType = OTHER;
            return message.getAttachmentType() | userType;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setData(ArrayList<Message> messages) {
        this.messages = messages;
    }

}
