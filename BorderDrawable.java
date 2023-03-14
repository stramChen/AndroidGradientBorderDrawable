package com.example.test;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.Nullable;

/**
 * @des: 渐变背景边框
 * @author: chensichuang@jd.com
 * @date: 2023/2/23 16:19
 */
public class BorderDrawable extends Drawable {
   public int mLeftBorderWidth = 0;
   public int mRightBorderWidth = 0;
   public int mTopBorderWidth = 0;
   public int mBottomBorderWidth = 0;

   public float mBorderRadius =0;

   public float[] mBorderRadii;

   Path mOuterPath = new Path();
   Path mInnerPath = new Path();

   private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

   private Shader mShader;

   private int mColor;

   private int[] mColors;

   private Integer mWidth;

   private Integer mHeight;

   private RectF mRectF;

   public BorderDrawable(int borderWidth) {
      this(borderWidth,borderWidth,borderWidth,borderWidth);
   }

   public void setCornerRadii(@Nullable float[] radii) {
      this.mBorderRadii = radii;
   }

   public void setCornerRadius(float radius) {
      this.mBorderRadius = radius;
   }

   public void setColor(int color){
      mColor = color;
      mPaint.setColor(mColor);
   }

   public void setColors(int[] colors){
      mColors = colors;
   }

   public BorderDrawable(int leftBorderWidth, int rightBorderWidth, int topBorderWidth, int bottomBorderWidth) {
      mLeftBorderWidth = leftBorderWidth;
      mRightBorderWidth = rightBorderWidth;
      mTopBorderWidth = topBorderWidth;
      mBottomBorderWidth = bottomBorderWidth;
   }


   @Override
   public void draw(Canvas canvas) {
      int width = getBounds().width();
      int height = getBounds().height();
      if(mWidth == null || mHeight == null || mWidth != width || mHeight != height){
         mOuterPath.reset();
         mInnerPath.reset();
         if(Build.VERSION.SDK_INT >= 21){
            if(mBorderRadii != null){
               mOuterPath.addRoundRect(0,0,width,height,mBorderRadii, Path.Direction.CW);
               mInnerPath.addRoundRect(mLeftBorderWidth,mTopBorderWidth,width-mRightBorderWidth,height-mBottomBorderWidth,mBorderRadii, Path.Direction.CW);
            }else {
               mOuterPath.addRoundRect(0,0,width,height,mBorderRadius,mBorderRadius, Path.Direction.CW);
               mInnerPath.addRoundRect(mLeftBorderWidth,mTopBorderWidth,width-mRightBorderWidth,height-mBottomBorderWidth,mBorderRadius,mBorderRadius, Path.Direction.CW);
            }
         }else {
            if(mBorderRadii != null){
               mOuterPath.addRoundRect(new RectF(0,0,width,height),mBorderRadii, Path.Direction.CW);
               mInnerPath.addRoundRect(new RectF(mLeftBorderWidth,mTopBorderWidth,width-mRightBorderWidth,height-mBottomBorderWidth),mBorderRadii, Path.Direction.CW);
            }else {
               mOuterPath.addRoundRect(new RectF(0,0,width,height),mBorderRadius,mBorderRadius, Path.Direction.CW);
               mInnerPath.addRoundRect(new RectF(mLeftBorderWidth,mTopBorderWidth,width-mRightBorderWidth,height-mBottomBorderWidth),mBorderRadius,mBorderRadius, Path.Direction.CW);
            }
         }

         if(null != mColors){
            mShader = new LinearGradient(0, 0, width, 0,
                    mColors, null, Shader.TileMode.REPEAT);
            mPaint.setShader(mShader);
         }
         mRectF = new RectF(0,0,width,height);
         mWidth = width;
         mHeight = height;
      }

      int layerId = canvas.saveLayer(mRectF, null, Canvas.ALL_SAVE_FLAG);
      canvas.drawPath(mOuterPath,mPaint);
      mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
      canvas.drawPath(mInnerPath,mPaint);
      mPaint.setXfermode(null);
      canvas.restoreToCount(layerId);
   }

   @Override
   public void setAlpha(int alpha) {

   }

   @Override
   public void setColorFilter(@Nullable ColorFilter colorFilter) {

   }

   @Override
   public int getOpacity() {
      return PixelFormat.UNKNOWN;
   }
}
