package at.ac.univie.sketchup.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import at.ac.univie.sketchup.model.Sketch;
import at.ac.univie.sketchup.model.sketchObjects.TextBox;

public class PaintView extends View {

    private float mX, mY;
    private Paint mPaint;

    private Sketch sketch;

//    private Bitmap mBitmap;
//    private Canvas mCanvas;

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(70);
    }

    public void updateSketch(Sketch s){
        sketch = s;
    }

    public void init(DisplayMetrics metrics,Sketch s) {
        sketch = s;

//        int height = metrics.heightPixels;
//        int width = metrics.widthPixels;
//        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        mCanvas = new Canvas(mBitmap);
//        mCanvas.drawPaint(mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        for(TextBox textBox : sketch.getTextBoxes()) {
            canvas.drawText(textBox.getText(), textBox.getX(), textBox.getY(), mPaint);
        }

//        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mX = event.getX();
        mY = event.getY();

        TextBox textBox = new TextBox();
        textBox.setText(sketch.getCurrentText());
        textBox.setX(mX);
        textBox.setY(mY);
        sketch.addTextBox(textBox);
        invalidate();

        return true;
    }

    public void setText(String t) {
        sketch.setCurrentText(t);
    }
}
