package com.application.chatbot.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.chatbot.R;
import com.application.chatbot.adapters.MessageAdapter;
import com.application.chatbot.models.Attachment;
import com.application.chatbot.models.AttachmentTypes;
import com.application.chatbot.models.Message;
import com.application.chatbot.viewHolders.BaseMessageViewHolder;

import java.util.ArrayList;
import java.util.Locale;

import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class ActivityHome extends AppCompatActivity implements View.OnClickListener, AIListener, TextToSpeech.OnInitListener {

    private Toolbar toolBar;
    private RecyclerView recyclerView;
    private EditText etMessage;
    private AppCompatImageButton btnSend;

    final AIConfiguration config = new AIConfiguration("73d0f1f8eb2e4d368d51356a6e210867",
            AIConfiguration.SupportedLanguages.English,
            AIConfiguration.RecognitionEngine.System);

    AIDataService aiDataService;
    AIRequest aiRequest;

    private AIService aiService;
    TextToSpeech assist;

    private MessageAdapter messageAdapter;
    private ArrayList<Message> dataList = new ArrayList<>();

    private long exitTime = 0;
    private Long timestamp = 0l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolBar = findViewById(R.id.HomeBar);
        toolBar.setTitle("AUTO RESPONSE SYSTEM SAKEC");

        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        initUi();

        if (assist == null) {
            assist = new TextToSpeech(getApplicationContext(), this);
        }

        aiDataService = new AIDataService(this, config);
        aiRequest = new AIRequest();

        aiService = AIService.getService(this, config);
        aiService.setListener(this);

        messageAdapter = new MessageAdapter(this, dataList, "MyID", etMessage);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);

        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(messageAdapter);
    }

    private void initUi() {
        recyclerView = findViewById(R.id.rvChat);
        etMessage = findViewById(R.id.new_message);
        btnSend = findViewById(R.id.send);

        btnSend.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        exitApp();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                if (!TextUtils.isEmpty(etMessage.getText().toString().trim())) {
                    sendMessage(etMessage.getText().toString(), AttachmentTypes.NONE_TEXT, null);
                    etMessage.setText("");
                }
                break;
        }
    }

    private void sendMessage(String messageBody, @AttachmentTypes.AttachmentType int attachmentType, Attachment attachment) {
        timestamp = getUnixTime();
        Message message = new Message();
        message.setAttachmentType(attachmentType);
        if (attachmentType != AttachmentTypes.NONE_TEXT)
            message.setAttachment(attachment);
        else
            BaseMessageViewHolder.animate = true;
        message.setBody(messageBody);
        message.setId("m-" + timestamp);
        message.setDate(System.currentTimeMillis());
        message.setTimestamp(timestamp);
        message.setSenderId("MyID");
        message.setSenderName("User");
        message.setSent(true);
        message.setDelivered(false);
        message.setRead(false);
        message.setRecipientId("AiID");

        dataList.add(message);

        messageAdapter.setData(dataList);
        messageAdapter.notifyDataSetChanged();

        aiRequest.setQuery(messageBody);

        new AsyncTask<AIRequest, Void, AIResponse>() {
            @Override
            protected AIResponse doInBackground(AIRequest... requests) {
                final AIRequest request = requests[0];
                try {
                    final AIResponse response = aiDataService.request(aiRequest);

                    return response;
                } catch (AIServiceException e) {
                }
                return null;
            }

            @Override
            protected void onPostExecute(AIResponse aiResponse) {
                if (aiResponse != null) {
                    Result result = aiResponse.getResult();
                    Log.d("FAB", aiResponse.toString());
                    sendReply(AttachmentTypes.NONE_TEXT, null, aiResponse);
                }
            }
        }.execute(aiRequest);

    }

    private void sendReply(@AttachmentTypes.AttachmentType int attachmentType, Attachment attachment, AIResponse aiResponse) {
        Result result = aiResponse.getResult();
        String msg = result.getFulfillment().getSpeech();

        timestamp = getUnixTime();
        Message message = new Message();
        message.setAttachmentType(attachmentType);
        if (attachmentType != AttachmentTypes.NONE_TEXT)
            message.setAttachment(attachment);
        else
            BaseMessageViewHolder.animate = true;
        message.setBody(msg);
        message.setId("mi-" + timestamp);
        message.setDate(System.currentTimeMillis());
        message.setTimestamp(timestamp + 1);
        message.setSenderId("AiID");
        message.setSenderName("AI-Bot");
        message.setSent(true);
        message.setDelivered(false);
        message.setRead(false);
        message.setRecipientId("MyID");

        dataList.add(message);

        messageAdapter.setData(dataList);
        messageAdapter.notifyDataSetChanged();
    }

    public void exitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, getString(R.string.msg_exit), Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    public long getUnixTime() {
        return System.currentTimeMillis() / 1000L;
    }

    @Override
    public void onResult(AIResponse response) {
        Result result = response.getResult();
        sendMessage(result.getResolvedQuery(), AttachmentTypes.NONE_TEXT, null);
        etMessage.setText("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (assist != null) {
            assist = new TextToSpeech(getApplicationContext(), this);
        }
    }

    public void HomeButtonOnClick(final View view) {
        aiService.startListening();
    }

    @Override
    public void onError(final AIError error) {
        //resultTextView.setText(error.toString());
    }

    @Override
    public void onListeningStarted() {
    }

    @Override
    public void onListeningCanceled() {
    }

    @Override
    public void onListeningFinished() {
    }

    @Override
    public void onAudioLevel(final float level) {
    }

    @Override
    public void onDestroy() {
        if (assist != null) {
            assist.stop();
            assist.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status != TextToSpeech.ERROR) {
            assist.setLanguage(Locale.ENGLISH);
            assist.speak("Hello, How may i help you today.", TextToSpeech.QUEUE_ADD, null);
        }
    }

    public void onPause() {
        if (assist != null) {
            assist.stop();
            assist.shutdown();
        }
        super.onPause();
    }

}
