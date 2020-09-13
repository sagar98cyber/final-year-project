package com.application.chatbot.viewHolders;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.application.chatbot.R;
import com.application.chatbot.Utils.GeneralUtils;
import com.application.chatbot.Utils.Helper;
import com.application.chatbot.models.AttachmentTypes;
import com.application.chatbot.models.Message;

import static com.application.chatbot.adapters.MessageAdapter.OTHER;

public class BaseMessageViewHolder extends RecyclerView.ViewHolder {

    protected Context context;

    protected static View newMessageView;
    protected static int lastPosition;

    private Message message;

    TextView time, senderName;
    LinearLayout ll;

    private int attachmentType;

    private static int _48dpInPx = -1;
    public static boolean animate;

    public BaseMessageViewHolder(View itemView, int attachmentType) {
        super(itemView);
        this.attachmentType = attachmentType;
    }

    public BaseMessageViewHolder(View itemView, View newMessage) {
        this(itemView);
        if (newMessageView == null) newMessageView = newMessage;
    }

    public BaseMessageViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        time = itemView.findViewById(R.id.time);
        senderName = itemView.findViewById(R.id.senderName);
        ll = itemView.findViewById(R.id.container);
        if (_48dpInPx == -1) _48dpInPx = GeneralUtils.dpToPx(itemView.getContext(), 48);
    }

    public void setData(Message message, int position) {
        this.message = message;
        if (attachmentType == AttachmentTypes.NONE_TYPING)
            return;
        if (attachmentType == AttachmentTypes.SPERATOR)
            return;
        time.setText(Helper.getTime(message.getDate()));
        if (message.getRecipientId().startsWith(Helper.GROUP_PREFIX)) {
            String nameToDisplay = message.getSenderName();
            /*if (myUsersNameInPhoneMap != null && myUsersNameInPhoneMap.containsKey(message.getSenderId())) {
                nameToDisplay = myUsersNameInPhoneMap.get(message.getSenderId()).getNameToDisplay();
            }*/
            senderName.setText(isMine() ? "You" : nameToDisplay);
            senderName.setTextColor(isMine() ? ContextCompat.getColor(context, R.color.colorPrimaryDark) : ContextCompat.getColor(context, R.color.colorAccent));
            senderName.setVisibility(View.VISIBLE);
        } else {
            senderName.setVisibility(View.GONE);
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) ll.getLayoutParams();
        if (isMine()) {
            layoutParams.gravity = Gravity.RIGHT;
            layoutParams.leftMargin = _48dpInPx;
            ll.setBackground(context.getResources().getDrawable(R.drawable.shape_bg_outgoing_bubble));
            time.setCompoundDrawablesWithIntrinsicBounds(0, 0, message.isSent() ? (message.isDelivered() ? (message.isRead() ? R.drawable.ic_read_all : R.drawable.ic_read) : R.drawable.ic_sent) : R.drawable.ic_waiting, 0);
        } else {
            layoutParams.gravity = Gravity.LEFT;
            layoutParams.rightMargin = _48dpInPx;
            ll.setBackground(context.getResources().getDrawable(R.drawable.shape_bg_incoming_bubble));
            itemView.startAnimation(AnimationUtils.makeInAnimation(itemView.getContext(), true));
        }
        ll.setLayoutParams(layoutParams);
    }

    protected boolean isMine() {
        return (getItemViewType() & OTHER) != OTHER;
    }
}
