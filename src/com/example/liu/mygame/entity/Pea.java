package com.example.liu.mygame.entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.example.liu.mygame.global.Config;
import com.example.liu.mygame.model.BaseModel;
import com.example.liu.mygame.model.Plant;
import com.example.liu.mygame.view.GameView;

//豌豆射手实体类
public class Pea extends BaseModel implements Plant {
	private int locationX;
	private int locationY;
	private boolean isAlife;
	// 图片帧数组的下标
	private int frameIndex = 0;
	// 一个标记通过此标记确定此处是否有植物
	private int mapIndex;
	// 控制产生子弹的时间
	private long lastBirthTime;
	// 摆动速度控制，两帧一动
	private boolean swingSpeed;

	public Pea(int locationX, int locationY, int mapIndex) {
		this.locationX = locationX;
		this.locationY = locationY;
		this.mapIndex = mapIndex;
		isAlife = true;
		swingSpeed = false;
	}

	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		if (isAlife) {
			// 这里绘入的bitmap就需要在绘制自己的时候换自己的帧动画以形成动态效果
			// 这个组在Config中已经初始化好了
			canvas.drawBitmap(Config.peaFrames[frameIndex], locationX,
					locationY, paint);
			// 用此变量让数组变化
			// 通过这样的取模方法，可以让这个frameIndex值不超过7
			// 当frameIndex为8时会变为0，避免数组越界
			if (!swingSpeed) {
				frameIndex = (++frameIndex) % 8;
				swingSpeed = false;
			} else {
				swingSpeed = true;
			}

			// 用此处判断来确定每10秒一个子弹的产生
			if (System.currentTimeMillis() - lastBirthTime > 10000) {
				lastBirthTime = System.currentTimeMillis();
				giveBirth2Bullet();
			}
		}
	}

	// 产生子弹
	// 子弹具有生命，然后两种情况，被点击则转换状态，移动到上方子弹的标志处，过一段时间不点击则死亡消失
	// 产生在花的位置上
	private void giveBirth2Bullet() {
		// 首先要有子弹的图层集合，处于第三层，那么就需要操作集合，就需要调用GameView.getInstance
		GameView.getInstance().giveBirth2Bullet(locationX, locationY);
	}

	@Override
	public int getModelWidth() {
		// TODO Auto-generated method stub
		return Config.peaFrames[0].getWidth();
	}

	public int getLocationX() {
		return locationX;
	}

	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}

	public int getLocationY() {
		return locationY;
	}

	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}

	public boolean isAlife() {
		return isAlife;
	}

	public void setAlife(boolean isAlife) {
		this.isAlife = isAlife;
	}

	@Override
	public int getmapIndex() {
		// TODO Auto-generated method stub
		return mapIndex;
	}
}
