package com.saka.customviewdemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class CustomDivider extends RecyclerView.ItemDecoration {
    private int layoutOrientation;
    private Paint textPaint;
    private Paint paint;
    private Drawable drawable;
    private Rect bounds = new Rect();

    public CustomDivider(Drawable drawable) {
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(30);
        textPaint.setColor(Color.BLACK);
        paint = new Paint();
        paint.setAntiAlias(true);
        this.drawable = drawable;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        Log.d("---", "getItemOffsets");
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            this.layoutOrientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
        }
        switch (this.layoutOrientation) {
            case LinearLayoutManager.VERTICAL:
                if (parent.getChildAdapterPosition(view) % 3 == 0) {
                    outRect.set(40, 0, 0, 100);

                } else {
                    outRect.set(40, 0, 0, 2);
                }
                break;
            case LinearLayoutManager.HORIZONTAL:
                break;
            default:
                break;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        Log.d("---", "onDraw");
        switch (this.layoutOrientation) {
            case LinearLayoutManager.VERTICAL:
                drawVertical(c, parent);
                break;
            case LinearLayoutManager.HORIZONTAL:
                drawHorizontal(c, parent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        Log.d("---", "onDrawOver");
        switch (this.layoutOrientation) {
            case LinearLayoutManager.VERTICAL:
                drawOverVertical(c, parent);
                break;
            case LinearLayoutManager.HORIZONTAL:
//                drawHorizontal(c, parent);
                break;
            default:
                break;
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        c.save();
        final int top;
        final int bottom;
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            c.clipRect(parent.getPaddingLeft(), top,
                    parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }


        c.restore();
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        c.save();
        final int left;
        final int right;
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            c.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            String text = String.valueOf(position+1);
            float w = textPaint.measureText(text);
            c.drawText(text, 20 - w / 2, view.getBottom() - view.getHeight() / 2 + textPaint.getFontMetrics().descent, textPaint);
        }
        c.restore();
    }

    private void drawOverVertical(Canvas c, RecyclerView parent) {
        c.save();
        final int left;
        final int right;
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            c.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        for (int i = 0; i < parent.getChildCount(); i++) {

            View view = parent.getChildAt(i);
//            if (parent.getChildAdapterPosition(view) % 3 != 0) {
//                continue;
//            }
            int top = view.getTop();
            int totalHeight = parent.getHeight() - parent.getPaddingTop() - parent.getPaddingBottom();
            float percentT = (float) top / totalHeight;
            drawable.setBounds((int) (view.getLeft() + 10 + view.getWidth() * percentT),
                    view.getTop(),
                    (int) (view.getLeft() + 58 + view.getWidth() * percentT),
                    view.getTop() + 48);
            drawable.draw(c);
        }
    }

}
