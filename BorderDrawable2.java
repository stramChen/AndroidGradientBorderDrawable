package com.example.test;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;

import androidx.annotation.Nullable;

/**
 * @des: 渐变背景边框
 * @author: chensichuang@jd.com
 * @date: 2023/2/23 16:19
 */
public class BorderDrawable2 extends GradientDrawable {
   public int mLeftBorderWidth = 0;
   public int mRightBorderWidth = 0;
   public int mTopBorderWidth = 0;
   public int mBottomBorderWidth = 0;

   public float mBorderRadius =0;

   public float[] mBorderRadii;

   Path mPath = new Path();

   public BorderDrawable2(int borderWidth) {
      this(borderWidth,borderWidth,borderWidth,borderWidth);
   }

   @Override
   public void setCornerRadii(@Nullable float[] radii) {
      this.mBorderRadii = radii;
      super.setCornerRadii(radii);
   }

   @Override
   public void setCornerRadius(float radius) {
      this.mBorderRadius = radius;
      super.setCornerRadius(radius);
   }

   public BorderDrawable2(int leftBorderWidth, int rightBorderWidth, int topBorderWidth, int bottomBorderWidth) {
      mLeftBorderWidth = leftBorderWidth;
      mRightBorderWidth = rightBorderWidth;
      mTopBorderWidth = topBorderWidth;
      mBottomBorderWidth = bottomBorderWidth;
   }



   @Override
   public void draw(Canvas canvas) {
      int width = getBounds().width();
      int height = getBounds().height();
      if(Build.VERSION.SDK_INT >= 21){
         if(mBorderRadii != null){
            mPath.addRoundRect(mLeftBorderWidth,mTopBorderWidth,width-mRightBorderWidth,height-mBottomBorderWidth,mBorderRadii, Path.Direction.CW);
         }else {
            mPath.addRoundRect(mLeftBorderWidth,mTopBorderWidth,width-mRightBorderWidth,height-mBottomBorderWidth,mBorderRadius,mBorderRadius, Path.Direction.CW);
         }
      }else{
         if(mBorderRadii != null){
            mPath.addRoundRect(new RectF(mLeftBorderWidth,mTopBorderWidth,width-mRightBorderWidth,height-mBottomBorderWidth),mBorderRadii, Path.Direction.CW);
         }else {
            mPath.addRoundRect(new RectF(mLeftBorderWidth,mTopBorderWidth,width-mRightBorderWidth,height-mBottomBorderWidth),mBorderRadius,mBorderRadius, Path.Direction.CW);
         }
      }

      canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG|Paint.ANTI_ALIAS_FLAG));
      canvas.clipPath(mPath, Region.Op.DIFFERENCE);
      super.draw(canvas);
   }
}
