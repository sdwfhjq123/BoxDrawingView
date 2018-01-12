package com.yinhao.boxdrawingview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

/**
 * Created by hp on 2018/1/12.
 */

public class BoxDrawingView extends View {
    private static final String TAG = "RectDrawingView";

    private RectF mCurRect;
    private ArrayList<RectF> mRects = new ArrayList<>();
    private Paint mRectPaint;

    /**
     * 从代码中创建视图.
     *
     * @param context 设备上下文
     * @author artzok
     **/
    public BoxDrawingView(Context context) {
        super(context);
    }

    /**
     * 从xml布局文件中创建视图.
     *
     * @param context 设备上下文
     * @param attrs   xml属性集
     * @author artzok
     **/
    public BoxDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRectPaint = new Paint();
        mRectPaint.setColor(0x22ff0000);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF curr = new PointF(event.getX(), event.getY());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mCurRect = new RectF();
                mCurRect.left = curr.x;
                mCurRect.top = curr.y;
                mRects.add(mCurRect);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mCurRect != null) {
                    mCurRect.right = curr.x;
                    mCurRect.bottom = curr.y;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                mCurRect = null;
                break;
            case MotionEvent.ACTION_CANCEL:
                mCurRect = null;
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (RectF rect : mRects) {
            float left = Math.min(rect.left, rect.right);
            float right = Math.max(rect.left, rect.right);
            float top = Math.min(rect.top, rect.bottom);
            float bottom = Math.max(rect.top, rect.bottom);
            canvas.drawRect(left, top, right, bottom, mRectPaint);
        }

        super.onDraw(canvas);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        Parcelable superData = super.onSaveInstanceState();
        bundle.putParcelable("super_data", superData);
        bundle.putParcelableArrayList("save_data", mRects);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle) state;
        Parcelable superData = bundle.getParcelable("super_data");
        mRects = bundle.getParcelableArrayList("save_data");
        super.onRestoreInstanceState(superData);
    }
}
