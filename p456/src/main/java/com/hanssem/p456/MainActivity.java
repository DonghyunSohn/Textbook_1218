package com.hanssem.p456;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    TextView textView01, textView02;
    EditText editText01, editText02;

    /**
     * 메인 스레드의 핸들러
     */
    MainHandler mainHandler;

    /**
     * 새로 만든 스레드
     */
    ProcessThread thread1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainHandler = new MainHandler();
        thread1 = new ProcessThread();

        textView01 = (TextView) findViewById(R.id.textView01);
        textView02 = (TextView) findViewById(R.id.textView02);
        editText01 = (EditText) findViewById(R.id.editText01);
        editText02 = (EditText) findViewById(R.id.editText02);

        // 버튼 이벤트 처리
        Button processBtn = (Button) findViewById(R.id.processBtn);
        processBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String inStr = editText01.getText().toString();
                Message msgToSend = Message.obtain();
                msgToSend.obj = inStr;

                thread1.handler.sendMessage(msgToSend);
            }
        });

        thread1.start();

    }

    /**
     * 새로 정의한 스레드
     */
    class ProcessThread extends Thread {
        // 새로운 스레드를 위한 핸들러
        ProcessHandler handler;

        public ProcessThread() {
            handler = new ProcessHandler();
        }

        public void run() {
            // 루퍼 사용
            Looper.prepare();
            Looper.loop();
        }

    }

    class ProcessHandler extends Handler {
        public void handleMessage(Message msg) {
            Message resultMsg = Message.obtain();
            resultMsg.obj = msg.obj + " Donghyun!";

            mainHandler.sendMessage(resultMsg);
        }
    }

    class MainHandler extends Handler {
        public void handleMessage(Message msg) {
            String str = (String) msg.obj;
            editText02.setText(str);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
