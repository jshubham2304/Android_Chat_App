package com.androidchatapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Chat extends AppCompatActivity {
    LinearLayout layout;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference1, reference2,ref1,ref2;
    List<String> text=new ArrayList<>();
    List<String> time=new ArrayList<>();
    RecyclerView rec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);

        rec=(RecyclerView)findViewById(R.id.msgrecycler);
        rec.setLayoutManager(new LinearLayoutManager(this));
        rec.setHasFixedSize(true);

        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://chitchat-ed18d.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://chitchat-ed18d.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();
                messageArea.setText("");
                Date time = Calendar.getInstance().getTime();
                if(!messageText.equals("")){
                    ref1=reference1.push();
                    ref2=reference2.push();
                    ref1.child("message").setValue(messageText);
                    ref1.child("user").setValue(UserDetails.username);
                    ref1.child("time").setValue(time.toString());

                    ref2.child("message").setValue(messageText);
                    ref2.child("user").setValue(UserDetails.username);
                    ref2.child("time").setValue(time.toString());
//                    Map<String, String> map = new HashMap<String, String>();
//                    map.put("message", messageText);
//                    map.put("user", UserDetails.username);
//                    reference1.push().setValue(map);
//                    reference2.push().setValue(map);
                }
                else {}
            }
        });

        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                text.clear();
                time.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {
                        String message = snapshot.child("message").getValue().toString();
                        String userName = snapshot.child("user").getValue().toString();
                        text.add(message);
                        time.add(userName);

                    } catch (Exception e) {
                        //Toast.makeText(Chat.this, ""+e, Toast.LENGTH_SHORT).show();
                    }
                }
                messageadapter messageadapter = new messageadapter(Chat.this, text, time);
                rec.setAdapter(messageadapter);
                rec.scrollToPosition(text.size()-1);
//
//                if(userName.equals(UserDetails.username)){
//                    addMessageBox( message, 1,"You");
//                }
//                else{
//                    addMessageBox( message, 2,UserDetails.chatWith);
//                    }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
// Add the TextView here for messaging purpose
    public void addMessageBox(String message, int type,String user){

// Now formattedDate have current date/time
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