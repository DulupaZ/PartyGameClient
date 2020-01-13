package com.example.winterproject.struct;

import androidx.annotation.NonNull;

public class PlayerInputParcel {
    private float inputX;
    private float inputY;
    private Vector2 direction;

    public PlayerInputParcel(float inputX, float inputY, Vector2 direction) {
        this.inputX = inputX;
        this.inputY = inputY;
        this.direction = direction;
    }



    public float getInputX() {
        return inputX;
    }

    public void setInputX(float inputX) {
        this.inputX = inputX;
    }

    public float getInputY() {
        return inputY;
    }

    public void setInputY(float inputY) {
        this.inputY = inputY;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }


    @NonNull
    @Override
    public String toString() {
        return String.format("%f,%f,%f,%f\n",inputX,inputY,direction.x,direction.y);
    }
}
