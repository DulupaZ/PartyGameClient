package com.example.winterproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.winterproject.listener.PlayerInputListener;
import com.example.winterproject.struct.PlayerInputParcel;
import com.example.winterproject.util.Debug;
import com.example.winterproject.view.ControllerView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class MainActivity extends AppCompatActivity {

    ControllerView ctrl;
    ImageView player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        player=findViewById(R.id.iv_player);
        ctrl=findViewById(R.id.iv_ctrl);
        ctrl.addPlayerInputEventListener(playerInputCallback);



            new Thread() {
                @Override
                public void run() {
                    connectToServer();
                }
            }.start();


/*
        new Thread(){
            @Override
            public void run() {
                super.run();
                while(true){
                    if(!playerInputs.isEmpty()) {
                        Debug.log("trigger");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                PlayerInputParcel input;
                                try{
                                    input = playerInputs.getFirst();}
                                catch (NoSuchElementException err){
                                    return;
                                }
                                player.setX(player.getX() + speed * input.getDirection().x);
                                player.setY(player.getY() + speed * input.getDirection().y);
                                playerInputs.removeFirst();
                            }
                        });

                    }
                }

            }
        }.start();
*/
    }


    private PlayerInputListener playerInputCallback=new PlayerInputListener() {
        @Override
        public void onPlayerInput(final PlayerInputParcel input) {
            //最後要在activity的層級把player input傳給server
            //server算出新位置，再傳給screen
            //把玩家的操作送給server
            //把player input 用字元的方式寫過去


            new Thread() {
                @Override
                public void run() {
                    sendToServer(input);
                }
            }.start();
        }
    };
    public static final String HOST="xxx.xxx.xxx.xx";   //測試的時候我有改回來!!
    public static final int PORT=7777;
    Socket socket=null;
    BufferedWriter writer;
    BufferedReader reader;

    private void connectToServer(){
        if (socket == null) {
            try {
                InetAddress serverIp = InetAddress.getByName(HOST);
                Debug.log("Before init socket");
                socket=new Socket(serverIp,PORT);

                Debug.log("After init socket");
                writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));

                Debug.log("succesfully connect!"+socket.isConnected());
            }
            catch (IOException e) {
                Debug.log("fail connect!");
                Debug.log("fail connect!"+e.getLocalizedMessage());
                e.printStackTrace();
            }

        }
    }
int count=0;
    private void  sendToServer(PlayerInputParcel input) {
        try {
            Debug.log((count++)+"th Input:"+input.toString());
            writer.write(input.toString());
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
