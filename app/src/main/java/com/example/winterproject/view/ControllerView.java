package com.example.winterproject.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.winterproject.listener.PlayerInputListener;
import com.example.winterproject.struct.PlayerInputParcel;
import com.example.winterproject.struct.Vector2;
import com.example.winterproject.util.Debug;

import java.util.ArrayList;
import java.util.List;

public class ControllerView extends View {
    private final float InputThreshold=300;
    private List<PlayerInputListener> listeners;


    public ControllerView(Context context) {
        super(context);
        init();
    }

    public ControllerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init(){
        listeners=new ArrayList<>();
    }

    private float downX,downY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Debug.log("touch");
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                downX=event.getX();
                downY=event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float deltaX=(event.getX()-downX);
                float deltaY=(event.getY()-downY);
                //沒有移動就不產出PlayerInput包裹
                if(deltaX==0&&deltaY==0)
                    return false;
                //移動方向是，第一次按下的位置  到  某一刻手指位置  的單位向量
                Vector2 direction=new Vector2(deltaX,deltaY).normalize();
                playerInput(new PlayerInputParcel(deltaX,deltaY,direction));

                return true;
            case MotionEvent.ACTION_UP:

                return false;
        }

        return super.onTouchEvent(event);
    }


    public void addPlayerInputEventListener(PlayerInputListener listener) {
        listeners.add(listener);
    }
    public void removePlayerInputEventListener(PlayerInputListener listener){
        listeners.remove(listener);
    }

    private void playerInput(PlayerInputParcel parcel){
        for(PlayerInputListener listener:listeners)
            listener.onPlayerInput(parcel);
    }
}
