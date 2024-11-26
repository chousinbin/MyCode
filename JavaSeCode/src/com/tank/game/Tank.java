package com.tank.game;

import java.util.Vector;

/**
 * @Project: JavaSeCode
 * @Author: SinbinZhou
 * @Date: 2024/11/22 20:20
 * @Description:
 */
public class Tank {
    // 坦克的坐标，指坦克左上角
    private int x;
    private int y;
    private int direction;
    private int speed = 1;
    private boolean isLive = true;
    private Vector<Bullet> bullets = new Vector<>();
    private long lastShotTime = 0; // 上次射击时间
    public static final int FIRE_INTERVAL = 333; // 333 毫秒限制，1 秒最多 3 次

    public void moveUp() {
        y = y - speed >= 0 ? y - speed :  y;
    }

    public void moveRight() {
        x = x + speed + 60 <= 1904 ? x + speed : x;
    }

    public void moveDown() {
        y = y + speed + 60 <= 1041 ? y + speed :  y;
    }

    public void moveLeft() {
        x = x - speed >= 0 ? x - speed : x;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Tank(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public Vector<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(Vector<Bullet> bullets) {
        this.bullets = bullets;
    }

    public long getLastShotTime() {
        return lastShotTime;
    }

    public void setLastShotTime(long lastShotTime) {
        this.lastShotTime = lastShotTime;
    }
}
