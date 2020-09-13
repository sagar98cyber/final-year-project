package com.application.chatbot.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class Message implements Parcelable {

    private String body, senderName, senderId, recipientId, id;
    private long date, timestamp = 0l;
    private boolean delivered = false, sent = false, read = false;
    private
    @AttachmentTypes.AttachmentType
    int attachmentType;
    private Attachment attachment;

    private boolean selected;

    public Message() {
    }

    public Message(int attachmentType) {
        this.attachmentType = attachmentType;
        this.senderId = "";
    }

    protected Message(Parcel in) {
        body = in.readString();
        senderName = in.readString();
        senderId = in.readString();
        recipientId = in.readString();
        id = in.readString();
        date = in.readLong();
        timestamp = in.readLong();
        delivered = in.readByte() != 0;
        sent = in.readByte() != 0;
        attachmentType = in.readInt();
        attachment = in.readParcelable(Attachment.class.getClassLoader());
        selected = in.readByte() != 0;
        read = in.readByte() != 0;
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;

        Message message = (Message) o;

        return getId() != null ? getId().equals(message.getId()) : message.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public int getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(int attachmentType) {
        this.attachmentType = attachmentType;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(body);
        parcel.writeString(senderName);
        parcel.writeString(senderId);
        parcel.writeString(recipientId);
        parcel.writeString(id);
        parcel.writeLong(date);
        parcel.writeLong(timestamp);
        parcel.writeByte((byte) (delivered ? 1 : 0));
        parcel.writeByte((byte) (sent ? 1 : 0));
        parcel.writeInt(attachmentType);
        parcel.writeParcelable(attachment, i);
        parcel.writeByte((byte) (selected ? 1 : 0));
        parcel.writeByte((byte) (read ? 1 : 0));
    }

    public Map<String, Object> getHashMap() {
        Map<String, Object> msg = new HashMap<>();
        msg.put("body", this.body);
        msg.put("senderName", this.senderName);
        msg.put("senderId", this.senderId);
        msg.put("recipientId", this.recipientId);
        msg.put("id", this.id);
        msg.put("date", this.date);
        msg.put("timestamp", this.timestamp);
        msg.put("delivered", this.delivered);
        msg.put("sent", this.sent);
        msg.put("read", this.read);
        msg.put("attachmentType", this.attachmentType);
        if(attachment!=null){
            msg.put("attachment", this.attachment.getHasMap());
        }
        return msg;
    }

}
