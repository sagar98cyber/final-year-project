package com.application.chatbot.Utils;

import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.TransformationMethod;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class LinkTransformationMethod implements TransformationMethod{

    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            Linkify.addLinks(textView, Linkify.WEB_URLS);
            if (textView.getText() == null || !(textView.getText() instanceof Spannable)) {
                return source;
            }
            Spannable text = (Spannable) textView.getText();

            StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
            StyleSpan italicSpan = new StyleSpan(Typeface.ITALIC);

            URLSpan[] spans = text.getSpans(0, textView.length(), URLSpan.class);
            for (int i = spans.length - 1; i >= 0; i--) {
                URLSpan oldSpan = spans[i];
                int start = text.getSpanStart(oldSpan);
                int end = text.getSpanEnd(oldSpan);
                String url = oldSpan.getURL();
                text.removeSpan(oldSpan);
                text.setSpan(new CustomTabsURLSpan(url), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            ArrayList<type> b = countMatches(textView.getText().toString(), '*');

            if (b.size() > 1) {
                int condition = 0;
                while (condition < b.size()) {
                    if ((b.size() - condition) <= 1) {
                        break;
                    }
                    text.setSpan(boldSpan, (b.get(condition).getData() + 1), (b.get(condition + 1).getData()), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text.setSpan(new RelativeSizeSpan(0f), b.get(condition).getData(), (b.get(condition).getData() + 1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text.setSpan(new RelativeSizeSpan(0f), (b.get(condition + 1).getData()), (b.get(condition + 1).getData() + 1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    condition = condition + 2;
                }
            }

            ArrayList<type> t = countMatches(textView.getText().toString(), '_');

            if (t.size() > 1) {
                int condition = 0;
                while (condition < t.size()) {
                    if ((t.size() - condition) <= 1) {
                        break;
                    }
                    text.setSpan(italicSpan, (t.get(condition).getData() + 1), (t.get(condition + 1).getData()), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text.setSpan(new RelativeSizeSpan(0f), t.get(condition).getData(), (t.get(condition).getData() + 1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text.setSpan(new RelativeSizeSpan(0f), (t.get(condition + 1).getData()), (t.get(condition + 1).getData() + 1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    condition = condition + 2;
                }
            }

            ArrayList<type> s = countMatches(textView.getText().toString(), '~');

            if (s.size() > 1) {
                int condition = 0;
                while (condition < s.size()) {
                    if ((s.size() - condition) <= 1) {
                        break;
                    }
                    text.setSpan(new StrikethroughSpan(), (s.get(condition).getData() + 1), (s.get(condition + 1).getData()), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text.setSpan(new RelativeSizeSpan(0f), s.get(condition).getData(), (s.get(condition).getData() + 1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text.setSpan(new RelativeSizeSpan(0f), (s.get(condition + 1).getData()), (s.get(condition + 1).getData() + 1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    condition = condition + 2;
                }
            }

            return text;
        }
        return source;
    }

    @Override
    public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int direction, Rect previouslyFocusedRect) {

    }

    public ArrayList<type> countMatches(String str, char c) {
        ArrayList<type> data = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c) {
                data.add(new type(i));
            }
        }
        return data;
    }

    public class type {
        int data;

        public type(int data) {
            this.data = data;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }
    }

}
