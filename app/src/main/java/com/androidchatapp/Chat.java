package com.androidchatapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class Chat extends AppCompatActivity {
    LinearLayout layout;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference1, reference2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        layout = (LinearLayout)findViewById(R.id.layout1);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);

        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://chitchat-ed18d.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://chitchat-ed18d.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", UserDetails.username);
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                }
            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if(userName.equals(UserDetails.username)){
                    addMessageBox( message, 1,"You");
                }
                else{
                    addMessageBox( message, 2,UserDetails.chatWith);
                    }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }
// Add the TextView here for messaging purpose
    public void addMessageBox(String message, int type,String user){
        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; "+c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
// Now formattedDate have current date/time
        Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();
//        TextView textView = new TextView(Chat.this);
//        textView.setText("\uD83D\uDC3C  "+user+" \n\t"+ message + "\n\n"+c.getTime());
//        textView.setTextSize(16);
//        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp.setMargins(5, 5, 5, 10);
//
//        textView.setPadding(10,10,10,10);
//
//        textView.setLayoutParams(lp);
        CardView card = new CardView(this);

        // Set the CardView layoutParams
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        card.setLayoutParams(params);
        params.setMargins(5,5,5,5);
        // Set CardView corner radius
        card.setRadius(5);
        card.setCardElevation(5);
        // Initialize a new TextView to put in CardView
        TextView tv = new TextView(this);
        tv.setLayoutParams(params);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        tv.setTextColor(Color.BLACK);

        // Put the TextView in CardView
        card.addView(tv);

        // Finally, add the
        if(type == 1) {
            params.setMargins(5,5,5,5);
            card.setLayoutParams(params);
            card.setCardBackgroundColor(Color.parseColor("#6ce3f5"));
            card.setForegroundGravity(Gravity.RIGHT);
            params.gravity=Gravity.RIGHT;
            card.setLayoutParams(params);
            tv.setText(message);
            tv.setMaxWidth(500);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            tv.setTextColor(Color.BLACK);

//            textView.setShadowLayer(10,5,5,R.color.colorAccent);
//            layout.setGravity(Gravity.RIGHT);
//            textView.setGravity(Gravity.RIGHT);
        }
        else{
            params.gravity=Gravity.LEFT;
            card.setLayoutParams(params);
            card.setCardBackgroundColor(Color.parseColor("#d3d4d4"));
            tv.setText(message);
            tv.setMaxWidth(250);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            tv.setTextColor(Color.BLACK);

//            layout.setGravity(Gravity.LEFT);
//            textView.setGravity(Gravity.LEFT);
        }
        layout.addView(card);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}