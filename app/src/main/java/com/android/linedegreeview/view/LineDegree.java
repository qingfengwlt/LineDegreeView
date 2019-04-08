package com.android.linedegreeview.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.android.linedegreeview.R;


/**
 * @author wlt
 * created at 2019/4/8 下午3:47
 */
public class LineDegree extends View {
    private Paint mPaint;
    private Paint mTextPaint;

    private int degreeCount;//几等分
    private int lineDegreeColor;
    private int degreeTextColor;

    private String startDegreeText;
    private String centerDegreeText;
    private String endDegreeText;

    private boolean isShortLineStartY;

    public LineDegree(Context context) {
        this(context, null);
    }

    public LineDegree(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineDegree(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LineDegree);
        degreeCount = typedArray.getInt(R.styleable.LineDegree_degreeCount, 1);
        lineDegreeColor = typedArray.getColor(R.styleable.LineDegree_degreeLineColor, ContextCompat.getColor(context, R.color.colorPrimary));
        degreeTextColor = typedArray.getColor(R.styleable.LineDegree_degreeTextColor, ContextCompat.getColor(context, R.color.colorAccent));
        startDegreeText = typedArray.getString(R.styleable.LineDegree_degreeStartText);
        centerDegreeText = typedArray.getString(R.styleable.LineDegree_degreeCenterText);
        endDegreeText = typedArray.getString(R.styleable.LineDegree_degreeEndText);
        isShortLineStartY = typedArray.getBoolean(R.styleable.LineDegree_degreeIsShortLineStartY, false);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(lineDegreeColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(degreeTextColor);
        mTextPaint.setTextSize(sp2px(12));
        float endDegreeTextWidth = mTextPaint.measureText(endDegreeText);//计算宽度
        int mHeight = getHeight();
        int size = (getWidth() - getPaddingLeft() - getPaddingRight()) / (degreeCount - 1);
        int centerY = mHeight / 2;
        for (int i = 0; i < degreeCount; i++) {
            if (i % 2 == 0) {
                canvas.drawLine(i * size + getPaddingLeft(), isShortLineStartY ? (centerY / 4) : 0, i * size + getPaddingLeft(), centerY / 2, mPaint);
            } else {
                canvas.drawLine(i * size + getPaddingLeft(), isShortLineStartY ? 0 : (centerY / 4), i * size + getPaddingLeft(), centerY / 2, mPaint);
            }
        }
        if (!TextUtils.isEmpty(startDegreeText))
            canvas.drawText(startDegreeText, 0, centerY * 2f, mTextPaint);
        if (!TextUtils.isEmpty(centerDegreeText))
            canvas.drawText(centerDegreeText, (degreeCount - 1) * size / 2, centerY * 2f, mTextPaint);
        if (!TextUtils.isEmpty(endDegreeText))
            canvas.drawText(endDegreeText, (degreeCount - 1) * size - endDegreeTextWidth / 2, centerY * 2f, mTextPaint);

    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                Resources.getSystem().getDisplayMetrics());
    }

}
