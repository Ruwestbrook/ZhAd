package westbrook.com.zhad;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by westbrook on 2017/12/12.
 * 显示广告 核心内容
 *  随着屏幕的移动  广告也随之移动 在最底部时,显示的是广告的头部,屏幕往上移动,广告的图片往下移动
 */

public class AdImageView extends android.support.v7.widget.AppCompatImageView {
    private static final String TAG = "MainActivity";
    private Context context;
    public AdImageView(Context context) {
        this(context,null);
    }

    public AdImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AdImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
       this.context=context;
    }
    private RectF mBitmapRectF;
    private Bitmap mBitmap;
    private int mMinDy;
    private int mDy;
    /**
     * This is called during layout when the size of this view has changed. If
     * you were just added to the view hierarchy, you're called with the old
     * values of 0.
     *
     * @param w Current width of this view.
     * @param h Current height of this view.
     * @param oldw Old width of this view.
     * @param oldh Old height of this view.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMinDy = h;
        /* Return the view's drawable, or null if no drawable has been
         assigned.
         */
        Drawable drawable = getDrawable();
        if (drawable == null) {
            Log.d(TAG, "onSizeChanged: 返回");
            return;
        }
        mBitmap = drawableToBitmap(drawable); //5760   3240
        mBitmapRectF = new RectF(0, 0, w, mBitmap.getHeight() * w / mBitmap.getWidth());//w :right

    }


    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            Log.d(TAG, "drawableToBitmap: "+bd.getIntrinsicWidth());
            Log.d(TAG, "drawableToBitmap: "+bd.getIntrinsicHeight());
            return bd.getBitmap();
        }

        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    /*
      广告总绘制的高度不变,但是绘制的图片起始位置会发生变化,但其高度也是不会变化的,所以只要确定绘制需要图片的起始位置就🆗了
      假设总高度是200 ,广告刚好出现,不绘制,上移50,那么显示图片的区域是0,50,移动200,显示的是0,200 但是大于两百之后,起始位置需要往下移
      移动300时,图片的区域是100,300



      dy  当前视图原点距离距离底部的高度  即View上移的高度
     */
    public void setDy(int dy) {
        if (getDrawable() == null) {
            return;
        }
        //mMinDy 视图的高度
        //mDy  当前图片起始位置的高度

        //当上移距离小于图片需要显示的高度的时候 mDy=0
        mDy = dy - mMinDy;
        if (mDy <= 0) {
            mDy = 0;
        }
        if (mDy > mBitmapRectF.height() - mMinDy) {
            mDy = (int) (mBitmapRectF.height() - mMinDy);
        }
        //重绘
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap == null) {
            return;
        }
        Log.d(TAG, "onDraw: "+canvas.getHeight());
        canvas.save();
        //往下平移  以该视图的左上角为原点  canvas的话的起始位置
        canvas.translate(0, -mDy);  //canvas平移 往X轴正方向和Y轴正方向   负值代表往上移动
        /*
        bitmap	Bitmap: The bitmap to be drawn
        This value must never be null.
        src	Rect: May be null. The subset of the bitmap to be drawn
        dst	RectF: The rectangle that the bitmap will be scaled/translated to fit into
        This value must never be null.
        paint	Paint: May be null. The paint used to draw the bitmap
        将bitmap画在RectF这个矩形中
         */
        canvas.drawBitmap(mBitmap,  null, mBitmapRectF, null);
        Log.d(TAG, "onDraw:画完后的高度 "+canvas.getHeight());
        /*
          This call balances a previous call to save(), and is used to remove all modifications to the matrix/clip state since the last save call.
         It is an error to call restore() more times than save() was called
         */

        canvas.restore();
    }
}
