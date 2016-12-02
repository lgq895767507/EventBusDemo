package com.flw.eventbusdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.flw.eventbusdemo.event.IdEvent;
import com.flw.eventbusdemo.event.MessageEvent;
import com.flw.eventbusdemo.subscribe.EventThread;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindViews({ R.id.button ,R.id.button2 , R.id.button3 })
    public List<Button> buttons;
    @BindView(R.id.textView)
    TextView textView;

    private EventThread eventThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //事件通过eventBus传值
    @OnClick(R.id.button)
    void sendMessage() {
        EventBus.getDefault().post(new MessageEvent("你好!"));
    }
    @OnClick(R.id.button2)
    void sendMessage2(){
        EventBus.getDefault().post(new IdEvent(47));
    }
    @OnClick(R.id.button3)
    void sendMessage3(){
        new Thread(){
            @Override
            public void run() {
                EventBus.getDefault().post(new MessageEvent("子线程发生的MessageEvent!"));
            }
        }.start();

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageEvent event) {

        if (event.getMsg() != null) {
            System.out.println("onEventMainThread:"+event.getMsg()+" "+Thread.currentThread().getName());
            textView.setText(event.getMsg());
        }else {
            System.out.println("event:"+event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(IdEvent event) {

        if (event != null) {
            System.out.println("onEventMainThread:"+event.getId()+" "+Thread.currentThread().getName());
            textView.setText(""+event.getId());
        }else {
            System.out.println("event:"+event);
        }
    }



    //注册eventBus
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        eventThread = new EventThread();
        eventThread.start();
    }

    //取消注册
    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        eventThread.unregister();
    }
}
