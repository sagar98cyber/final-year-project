package com.application.chatbot.viewHolders;

import android.view.View;
import android.widget.TextView;

import com.application.chatbot.R;
import com.application.chatbot.Utils.Helper;
import com.application.chatbot.models.AttachmentTypes;
import com.application.chatbot.models.Message;

public class MessageSepratorViewHolder extends BaseMessageViewHolder {

    TextView tvDate;

    public MessageSepratorViewHolder(View itemView) {
        super(itemView, AttachmentTypes.SPERATOR);
        tvDate = itemView.findViewById(R.id.tvDate);
    }

    @Override
    public void setData(Message message, int position) {
        tvDate.setText(Helper.TimestampToDate(message.getDate()));
    }

}
