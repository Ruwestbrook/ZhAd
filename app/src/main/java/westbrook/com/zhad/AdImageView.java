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

/**
 * Created by westbrook on 2017/12/12.
 * 显示广告
 */

public class AdImageView extends android.support.v7.widget.AppCompatImageView {
    private static final String TAG = "MainActivity";
    public AdImageView(Context context) {
        this(context,null);
    }

    public AdImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AdImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        Log.d(TAG, "onSizeChanged: 改变大小");
        super.onSizeChanged(w, h, oldw, oldh);
        mMinDy = h;
        /* Return the view's drawable, or null if no drawable has been
         assigned.
         */
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        mBitmap = drawableToBitmap(drawable);
        mBitmapRectF = new RectF(0, 0, w, mBitmap.getHeight() * w / mBitmap.getWidth());
    }


    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
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

    public void setDy(int dy) {

        if (getDrawable() == null) {
            return;
        }
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
        canvas.save();
        canvas.translate(0, -mDy);  //canvas平移 往X轴正方向和Y轴正方向
        /*
        bitmap	Bitmap: The bitmap to be drawn
        This value must never be null.
        src	Rect: May be null. The subset of the bitmap to be drawn
        dst	RectF: The rectangle that the bitmap will be scaled/translated to fit into
        This value must never be null.
        paint	Paint: May be null. The paint used to draw the bitmap
         */
        canvas.drawBitmap(mBitmap, null, mBitmapRectF, null);
        /*
          This call balances a previous call to save(), and is used to remove all modifications to the matrix/clip state since the last save call.
         It is an error to call restore() more times than save() was called
         */

        canvas.restore();
    }
}
