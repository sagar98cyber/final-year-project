package com.application.chatbot.viewHolders;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.application.chatbot.R;
import com.application.chatbot.Utils.GeneralUtils;
import com.application.chatbot.Utils.LinkTransformationMethod;
import com.application.chatbot.models.Message;

import static com.application.chatbot.adapters.MessageAdapter.MY;

public class MessageTextViewHolder extends BaseMessageViewHolder {

    TextView text;

    private Message message;
    private FrameLayout FrameLL;

    private static int _4dpInPx = -1;

    public MessageTextViewHolder(View itemView, View newMessageView) {
        super(itemView, newMessageView);
        text = itemView.findViewById(R.id.text);
        FrameLL = itemView.findViewById(R.id.msgFragment);

        text.setTransformationMethod(new LinkTransformationMethod());
        text.setMovementMethod(LinkMovementMethod.getInstance());

        if (_4dpInPx == -1) _4dpInPx = GeneralUtils.dpToPx(itemView.getContext(), 4);
    }

    @Override
    public void setData(Message message, int position) {
        super.setData(message, position);
        this.message = message;

        FrameLL.setBackgroundColor(message.isSelected() ? ContextCompat.getColor(context, R.color.colorWhite) : ContextCompat.getColor(context, R.color.colorTransparent));

        text.setText(message.getBody());
        text.setTextColor(Color.BLACK);

        text.setTextSize(14);

        if (getItemViewType() == MY) {
            animateView(position);
        }
    }

    private void animateView(int position) {
        if (animate && position > lastPosition) {

            itemView.post(new Runnable() {
                @Override
                public void run() {
                    float originalX = ll.getX();
                    final float originalY = itemView.getY();
                    int[] loc = new int[2];
                    newMessageView.getLocationOnScreen(loc);
                    ll.setX(loc[0] / 2);
                    itemView.setY(loc[1]);
                    ValueAnimator radiusAnimator = new ValueAnimator();
                    radiusAnimator.setFloatValues(80, _4dpInPx);
                    radiusAnimator.setDuration(850);
                    radiusAnimator.start();
                    ll.animate().x(originalX).setDuration(900).setInterpolator(new DecelerateInterpolator()).start();
                    itemView.animate().y(originalY - _4dpInPx).setDuration(750).setInterpolator(new DecelerateInterpolator()).start();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            itemView.animate().y(originalY + _4dpInPx).setDuration(250).setInterpolator(new DecelerateInterpolator()).start();
                        }
                    }, 750);
                }
            });
            lastPosition = position;
//            setAnimation(getAdapterPosition());
            animate = false;
        }
    }

}
