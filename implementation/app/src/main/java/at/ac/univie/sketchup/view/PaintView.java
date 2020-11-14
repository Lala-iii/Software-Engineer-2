package at.ac.univie.sketchup.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.sketchup.model.Circle;
import at.ac.univie.sketchup.model.Line;
import at.ac.univie.sketchup.model.Quadrangle;
import at.ac.univie.sketchup.model.Shape;
import at.ac.univie.sketchup.model.ElementType;
import at.ac.univie.sketchup.model.Sketch;
import at.ac.univie.sketchup.model.Triangle;
import at.ac.univie.sketchup.model.sketchObjects.TextBox;

public class PaintView extends View {

    private float mX, mY;
    private Paint mPaint;

    private Sketch sketch;
    private ElementType type;

    private List<Shape> elements;
    public Line currentLine;
    private Circle currentCircle;
    private Quadrangle currentQuadrangle;
    private Triangle currentTriangle;
    private TextBox textBox;

    double xBegin = 0;
    double yBegin = 0;
    double xEnd = 0;
    double yEnd = 0;

//    private Bitmap mBitmap;
//    private Canvas mCanvas;

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(70);
        type = ElementType.NONE;
        elements = new ArrayList<>();
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
    public void setSketch(Sketch s) {
        this.sketch = s;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        for(TextBox textBox : sketch.getTextBoxes()) {
            canvas.drawText(textBox.getText(), textBox.getAnchor().x, textBox.getAnchor().y, mPaint);
        }

        for (Shape s : elements) {
            if (s.getType() == ElementType.LINE) {
                Line l = (Line) s;
                canvas.drawLine(l.getAnchor().x, l.getAnchor().y, l.getEnd().x, l.getEnd().y, mPaint);
            }
            if (s.getType() == ElementType.CIRCLE) {
                Circle c = (Circle) s;
                canvas.drawCircle(c.getAnchor().x, c.getAnchor().y, (int) c.getRadius(), mPaint);
            }
            if (s.getType() == ElementType.QUADRANGLE) canvas.drawRect(drawQuadrangle((Quadrangle)s), mPaint);
            if (s.getType() == ElementType.TRIANGLE) canvas.drawPath(drawTriangle((Triangle)s), mPaint);
        }



//        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();

        if (textBox != null)
            canvas.drawText(textBox.getText(), textBox.getAnchor().x, textBox.getAnchor().y, mPaint);

        if (currentLine != null && currentLine.getEnd() != null)
            canvas.drawLine(currentLine.getAnchor().x, currentLine.getAnchor().y, currentLine.getEnd().x, currentLine.getEnd().y, mPaint);

        if (currentCircle != null && currentCircle.getRadius() > 0)
            canvas.drawCircle(currentCircle.getAnchor().x, currentCircle.getAnchor().y, (int) currentCircle.getRadius(), mPaint);

        if (currentTriangle != null && currentTriangle.getWidth() > 0) {
            canvas.drawPath(drawTriangle(currentTriangle), mPaint);
        }

        if (currentQuadrangle != null && currentQuadrangle.getEnd() != null)
            canvas.drawRect(drawQuadrangle(currentQuadrangle), mPaint);
    }

    private Rect drawQuadrangle(Quadrangle q) {
        return new Rect(q.getAnchor().x, q.getAnchor().y, q.getEnd().x, q.getEnd().y);
    }

    private Path drawTriangle(Triangle t) {
        Path path = new Path();
        int width = (int) t.getWidth();
        path.moveTo(t.getAnchor().x, t.getAnchor().y - width); // Top
        path.lineTo(t.getAnchor().x - width, t.getAnchor().y + width); // Bottom left
        path.lineTo(t.getAnchor().x + width, t.getAnchor().y + width); // Bottom right
        path.lineTo(t.getAnchor().x, t.getAnchor().y - width); // Back to top
        path.close();
        return path;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


//        textBox = new TextBox(new Point((int) event.getX(), (int) event.getY()));
//        textBox.setText(sketch.getCurrentText());
//        textBox.setX(xBegin);
//        textBox.setY(yBegin);
//        sketch.addTextBox(textBox);


        if (type == ElementType.NONE) return true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (type == ElementType.LINE)
                    currentLine = new Line(type, "Line", 1, Color.valueOf(Color.BLACK), new Point((int) event.getX(), (int) event.getY()), null);

                if (type == ElementType.CIRCLE) {
                    xBegin = event.getX();
                    yBegin = event.getY();
                    currentCircle = new Circle(type, "Circle", 1, Color.valueOf(Color.BLACK), new Point((int) xBegin, (int) yBegin), 0);
                }

                if (type == ElementType.QUADRANGLE) {
                    currentQuadrangle = new Quadrangle(type, "Quadrangle", 1,
                            Color.valueOf(Color.BLACK), new Point((int) event.getX(), (int) event.getY()), null);
                }

                if (type == ElementType.TRIANGLE) {
                    xBegin = event.getX();
                    yBegin = event.getY();
                    currentTriangle = new Triangle(type, "line", 1, Color.valueOf(Color.BLACK), new Point((int) xBegin, (int) yBegin), 0);
                }
                if (type == ElementType.TEXT) {
                    textBox = new TextBox(new Point((int) event.getX(), (int) event.getY()));
                    textBox.setText(sketch.getCurrentText());
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (type == ElementType.LINE) currentLine.setEnd(new Point((int) event.getX(), (int) event.getY()));
                if (type == ElementType.CIRCLE) {
                    xEnd = event.getX();
                    yEnd = event.getY();
                    currentCircle.setRadius(getDiff());
                }

                if (type == ElementType.QUADRANGLE) currentQuadrangle.setEnd(new Point((int) event.getX(), (int) event.getY()));


                if (type == ElementType.TRIANGLE) {
                    xEnd = event.getX();
                    yEnd = event.getY();
                    currentTriangle.setWidth(getDiff());
                }
                if (type == ElementType.TEXT) {
                    textBox = new TextBox(new Point((int) event.getX(), (int) event.getY()));
                    textBox.setText(sketch.getCurrentText());
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (type == ElementType.LINE) elements.add(currentLine);
                if (type == ElementType.CIRCLE) elements.add(currentCircle);
                if (type == ElementType.QUADRANGLE) elements.add(currentQuadrangle);
                if (type == ElementType.TRIANGLE) elements.add(currentTriangle);
                if (type == ElementType.TEXT) sketch.addTextBox(textBox);

//                type = ElementType.NONE;

                break;
        }

        invalidate();
        return true;
    }

    private double getDiff() {
        return Math.sqrt(Math.pow(Math.abs(xEnd - xBegin), 2) + Math.pow(Math.abs(yEnd - yBegin), 2));
    }

    public void setText(String t) {
        sketch.setCurrentText(t);
    }

    public void setType(ElementType type) {
        this.type = type;
    }
}
