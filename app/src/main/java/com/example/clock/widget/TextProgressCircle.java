package com.example.clock.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

@SuppressLint("DrawAllocation")
public class TextProgressCircle extends View {
	private Context mContext;
	private Paint mPaintBack;
	private Paint mPaintFore;
	private Paint mPaintText;
	private  String str;
	private int mLineWidth = 10;
	private int mLineColor = Color.WHITE;
	private float mTextSize = 80.0f;
	private RectF mRect;
	private float mProgress = 0;

	public TextProgressCircle(Context context) {
		this(context, null);
	}

	public TextProgressCircle(Context context, AttributeSet attr) {
		super(context, attr);
		mContext = context;
		mPaintBack = new Paint();
		mPaintBack.setAntiAlias(true);
		mPaintBack.setColor(Color.BLACK);
		mPaintBack.setStrokeWidth(mLineWidth);
		mPaintBack.setStyle(Style.STROKE);
		mPaintFore = new Paint();
		mPaintFore.setAntiAlias(true);
		mPaintFore.setColor(mLineColor);
		mPaintFore.setStrokeWidth(mLineWidth);
		mPaintFore.setStyle(Style.STROKE);
		mPaintText = new Paint();
		mPaintText.setAntiAlias(true);
		mPaintText.setColor(Color.BLACK);
		mPaintText.setTextSize(mTextSize);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		if (width <=0 || height <=0) {
			return;
		}
		int diameter = Math.min(width, height);
		mRect = new RectF((width-diameter)/2+mLineWidth, (height-diameter)/2+mLineWidth, 
				(width+diameter)/2-mLineWidth, (height+diameter)/2-mLineWidth);
		canvas.drawArc(mRect, 0, 360, false, mPaintBack);
		canvas.drawArc(mRect, 0, mProgress*360/100, true, mPaintFore);
		String text = str;
	    Rect rect = new Rect();
	    mPaintText.getTextBounds(text, 0, text.length(), rect);
	    int x = (getWidth() / 2) - rect.centerX();
	    int y = (getHeight() / 2) - rect.centerY();
	    canvas.drawText(text, x, y, mPaintText);
	}


	public void setProgress(float progress, String strs, float textSize) {
		mProgress = progress;
		this.str=strs;
		if (textSize > 0) {
			mTextSize = textSize;
			mPaintText.setTextSize(mTextSize);
		}
		invalidate();
	}
	


}
