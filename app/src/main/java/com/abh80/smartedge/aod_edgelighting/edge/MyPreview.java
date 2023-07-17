package com.abh80.smartedge.aod_edgelighting.edge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;

public class MyPreview extends SurfaceView implements SurfaceHolder.Callback {
    private int[] arrColor;
    private Bitmap bm;
    private Bitmap bmBg;
    private int h;
    private Handler handler;
    private MyItem myItem;
    private Paint pBitmap;
    private PathMeasure pHole;
    private PathMeasure pRound;
    private Paint pSec;
    private Paint paint;
    private Path path;
    private Path pathHole;
    private float[] position;
    private RectF rect;
    private Rect rectBg;
    private Rect rectLogo;
    private float[] slope;
    private int w;
    private int x;
    private final Runnable runnable = new Runnable() {
        /* class com.colorwallpaper.lovephone.example.preview.MyPreview.AnonymousClass1 */

        public void run() {

            if (MyPreview.this.arrColor == null || MyPreview.this.arrColor.length != 1) {
                MyPreview.this.handler.postDelayed(this, 16);
                MyPreview.this.action();
                MyPreview myPreview = MyPreview.this;
                myPreview.draw(myPreview.getHolder());
            }

        }
    };

    public MyPreview(Context context) {
        super(context);
        init();
    }

    public MyPreview(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public MyPreview(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    private void init() {
        this.handler = new Handler();
        Paint paint2 = new Paint(1);
        this.paint = paint2;
        paint2.setStrokeCap(Paint.Cap.ROUND);
        this.paint.setStrokeJoin(Paint.Join.ROUND);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(-1);
        this.paint.setTextAlign(Paint.Align.CENTER);
        this.pSec = new Paint(this.paint);
        Paint paint3 = new Paint(1);
        this.pBitmap = paint3;
        paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        this.path = new Path();
        this.pathHole = new Path();
        this.position = new float[2];
        this.slope = new float[2];
        getHolder().addCallback(this);
        this.rect = new RectF();
        this.rectBg = new Rect();
        this.rectLogo = new Rect();
    }

    public void reset() {
        OtherUtils.setPath(this.w, this.h, this.path, this.pathHole, this.paint, this.myItem);
        this.pRound = new PathMeasure(this.path, false);
        this.pHole = new PathMeasure(this.pathHole, false);
        action();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void action() {
        if (this.myItem.getStyle() == 0) {
            if (this.myItem.isReverse()) {
                this.x -= this.myItem.getAnim();
            } else {
                this.x += this.myItem.getAnim();
            }
            OtherUtils.setPaint(this.x, 0, this.w, this.h, this.paint, this.myItem.getStyle(), this.arrColor);
            return;
        }
        switch (this.myItem.getLinearStyle()) {
            case 0:
                int anim = this.x + (this.myItem.getAnim() * 7);
                this.x = anim;
                int i = this.h;
                OtherUtils.setPaint(anim, 0, i + anim, i, this.paint, this.myItem.getStyle(), this.arrColor);
                return;
            case 1:
                int anim2 = this.x + (this.myItem.getAnim() * 7);
                this.x = anim2;
                int i2 = this.h;
                OtherUtils.setPaint(i2 - anim2, 0, -anim2, i2, this.paint, this.myItem.getStyle(), this.arrColor);
                return;
            case 2:
                int anim3 = this.x + (this.myItem.getAnim() * 7);
                this.x = anim3;
                int i3 = this.h;
                OtherUtils.setPaint(-anim3, 0, i3 - anim3, i3, this.paint, this.myItem.getStyle(), this.arrColor);
                return;
            case 3:
                int anim4 = this.x + (this.myItem.getAnim() * 7);
                this.x = anim4;
                int i4 = this.h;
                OtherUtils.setPaint(i4 + anim4, 0, anim4, i4, this.paint, this.myItem.getStyle(), this.arrColor);
                return;
            case 4:
                int anim5 = this.x + (this.myItem.getAnim() * 7);
                this.x = anim5;
                OtherUtils.setPaint(0, anim5, 0, this.h + anim5, this.paint, this.myItem.getStyle(), this.arrColor);
                return;
            case 5:
                int anim6 = this.x + (this.myItem.getAnim() * 7);
                this.x = anim6;
                OtherUtils.setPaint(0, -anim6, 0, this.h - anim6, this.paint, this.myItem.getStyle(), this.arrColor);
                return;
            case 6:
                int anim7 = this.x + (this.myItem.getAnim() * 7);
                this.x = anim7;
                OtherUtils.setPaint(anim7, 0, this.h + anim7, 0, this.paint, this.myItem.getStyle(), this.arrColor);
                return;
            case 7:
                int anim8 = this.x + (this.myItem.getAnim() * 7);
                this.x = anim8;
                OtherUtils.setPaint(-anim8, 0, this.h - anim8, 0, this.paint, this.myItem.getStyle(), this.arrColor);
                return;
            default:
                return;
        }
    }

    public void setMyItem(MyItem myItem2) {
        this.myItem = myItem2;
        if (myItem2.getStyleLogo() != null) {
            makeBmBg();
        }
        if (myItem2.getArrColor() != null) {
            this.arrColor = new int[(myItem2.getArrColor().size() + 1)];
            int i = 0;
            for (int i2 = 0; i2 < myItem2.getArrColor().size(); i2++) {
                this.arrColor[i] = myItem2.getArrColor().get(i2).intValue();
                i++;
            }
            this.arrColor[i] = myItem2.getArrColor().get(0).intValue();
        }
        updateBm();
        if (this.w != 0) {
            reset();
        }
    }

    public void startAnim() {
        this.handler.post(this.runnable);
    }

    public void stopAnim() {
        this.handler.removeCallbacks(this.runnable);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void draw(SurfaceHolder surfaceHolder) {
        int i;
        Canvas lockCanvas = surfaceHolder.lockCanvas();
        if (lockCanvas != null) {
            if (this.w == 0) {
                this.w = lockCanvas.getWidth();
                int height = lockCanvas.getHeight();
                this.h = height;
                this.rectBg.set(0, 0, this.w, height);
                setLocationLogo();
                reset();
            }
            int style = this.myItem.getStyleLogo().getStyle();
            if (style == 1) {
                lockCanvas.drawColor(ViewCompat.MEASURED_STATE_MASK);
                if (this.bmBg != null) {
                    if (this.paint.getStyle() != Paint.Style.FILL) {
                        this.paint.setStyle(Paint.Style.FILL);
                    }
                    lockCanvas.drawRect(this.rectLogo, this.paint);
                    lockCanvas.drawBitmap(this.bmBg, (Rect) null, this.rectLogo, this.pBitmap);
                }
                this.paint.setStrokeWidth((float) (this.myItem.getSizeLogo() / 30));
                OtherUtils.drawClock(lockCanvas, this.paint, this.pSec, this.rectLogo.left, this.rectLogo.top, this.myItem.getSizeLogo());
            } else if (style == 3) {
                lockCanvas.drawColor(ViewCompat.MEASURED_STATE_MASK);
                lockCanvas.drawBitmap(this.bmBg, (Rect) null, this.rectLogo, (Paint) null);
            } else if (style == 4) {
                if (this.paint.getStyle() != Paint.Style.FILL) {
                    this.paint.setStyle(Paint.Style.FILL);
                }
                String data = this.myItem.getStyleLogo().getData();
                lockCanvas.drawColor(ViewCompat.MEASURED_STATE_MASK);
                lockCanvas.save();
                int width = getWidth() / 2;
                int height2 = (int) (((float) (getHeight() / 2)) - ((this.paint.descent() + this.paint.ascent()) / 2.0f));
                int height3 = getHeight() / 2;
                Rect rect2 = new Rect();
                this.paint.getTextBounds(data, 0, data.length(), rect2);
                if (this.myItem.getxLogo() != -1) {
                    width = this.myItem.getxLogo();
                    i = (rect2.width() / 2) + width;
                } else {
                    i = width;
                }
                if (this.myItem.getyLogo() != -1) {
                    height2 = this.myItem.getyLogo();
                    height3 = (rect2.height() / 2) + height2;
                }
                lockCanvas.rotate((float) this.myItem.getStyleLogo().getValue(), (float) i, (float) height3);
                lockCanvas.drawText(data, (float) width, (float) height2, this.paint);
                lockCanvas.restore();
            } else if (style == 6) {
                lockCanvas.drawColor(ViewCompat.MEASURED_STATE_MASK);
                if (this.bmBg != null) {
                    if (this.paint.getStyle() != Paint.Style.FILL) {
                        this.paint.setStyle(Paint.Style.FILL);
                    }
                    lockCanvas.drawRect(this.rectLogo, this.paint);
                    lockCanvas.drawBitmap(this.bmBg, (Rect) null, this.rectLogo, this.pBitmap);
                }
            } else if (style == 7) {
                Bitmap bitmap = this.bmBg;
                if (bitmap != null) {
                    lockCanvas.drawBitmap(bitmap, (Rect) null, this.rectBg, (Paint) null);
                } else {
                    lockCanvas.drawColor(ViewCompat.MEASURED_STATE_MASK);
                }
            } else if (style == 8) {
                lockCanvas.drawColor(this.myItem.getStyleLogo().getColor());
            }


            this.paint.setStrokeWidth(this.myItem.getStroke());
            if (this.bm == null) {
                if (this.paint.getStyle() != Paint.Style.STROKE) {
                    this.paint.setStyle(Paint.Style.STROKE);
                }
                lockCanvas.drawPath(this.path, this.paint);
                lockCanvas.drawPath(this.pathHole, this.paint);
            } else {
                float stroke = this.myItem.getStroke() / 2.0f;
                float stroke2 = this.myItem.getStroke() / 2.0f;
                while (stroke2 < this.pRound.getLength()) {
                    this.pRound.getPosTan(stroke2, this.position, this.slope);
                    RectF rectF = this.rect;
                    float[] fArr = this.position;
                    rectF.set((float) ((int) ((fArr[0] - stroke) + 1.0f)), (float) ((int) ((fArr[1] - stroke) + 1.0f)), (float) ((int) ((fArr[0] + (this.myItem.getStroke() / 2.0f)) - 1.0f)), (float) ((int) ((this.position[1] + (this.myItem.getStroke() / 2.0f)) - 1.0f)));
                    if (Build.VERSION.SDK_INT >= 21) {
                        lockCanvas.saveLayer(this.rect, this.paint);
                    } else {
                        lockCanvas.save();
                    }
                    lockCanvas.drawRect(this.rect, this.paint);
                    lockCanvas.drawBitmap(this.bm, (Rect) null, this.rect, this.pBitmap);
                    lockCanvas.restore();
                    stroke2 += this.myItem.getStroke() * 2.0f;
                }
                float stroke3 = this.myItem.getStroke() / 2.0f;
                while (stroke3 < this.pHole.getLength()) {
                    this.pHole.getPosTan(stroke3, this.position, this.slope);
                    RectF rectF2 = this.rect;
                    float[] fArr2 = this.position;
                    rectF2.set((float) ((int) ((fArr2[0] - stroke) + 1.0f)), (float) ((int) ((fArr2[1] - stroke) + 1.0f)), (float) ((int) ((fArr2[0] + (this.myItem.getStroke() / 2.0f)) - 1.0f)), (float) ((int) ((this.position[1] + (this.myItem.getStroke() / 2.0f)) - 1.0f)));
                    if (Build.VERSION.SDK_INT >= 21) {
                        lockCanvas.saveLayer(this.rect, this.paint);
                    } else {
                        lockCanvas.save();
                    }
                    lockCanvas.drawRect(this.rect, this.paint);
                    lockCanvas.drawBitmap(this.bm, (Rect) null, this.rect, this.pBitmap);
                    lockCanvas.restore();
                    stroke3 += this.myItem.getStroke() * 2.0f;
                }
            }
            surfaceHolder.unlockCanvasAndPost(lockCanvas);
        }
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        surfaceHolder.setFormat(-3);
        startAnim();
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        stopAnim();
    }

    public int getW() {
        return this.w;
    }

    public int getH() {
        return this.h;
    }

    public int[] getArrColor() {
        return this.arrColor;
    }

    public void changeColor() {
        if (this.myItem.getArrColor() != null) {
            this.arrColor = new int[(this.myItem.getArrColor().size() + 1)];
            int i = 0;
            for (int i2 = 0; i2 < this.myItem.getArrColor().size(); i2++) {
                this.arrColor[i] = this.myItem.getArrColor().get(i2).intValue();
                i++;
            }
            this.arrColor[i] = this.myItem.getArrColor().get(0).intValue();
        }
    }

    public void onChangeStyle() {
        action();
    }

    public void updateBm() {
        if (this.myItem.getItemIcon() != null) {
            this.paint.setStyle(Paint.Style.FILL);
            this.bm = OtherUtils.getBm(VectorDrawableCreator.getVector(getContext(), this.myItem), (int) this.myItem.getStroke());
        } else if (this.myItem.getCusRound() == null || this.myItem.getCusRound().isEmpty() || ActivityCompat.checkSelfPermission(getContext(), "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            this.paint.setStyle(Paint.Style.STROKE);
            this.bm = null;
        } else {
            this.paint.setStyle(Paint.Style.FILL);
            Bitmap decodeFile = BitmapFactory.decodeFile(this.myItem.getCusRound());
            this.bm = decodeFile;
            if (decodeFile == null) {
                this.paint.setStyle(Paint.Style.STROKE);
            }
        }
    }

    public void updateBorder() {
        reset();
    }

    public void makeBmBg() {
        int style = this.myItem.getStyleLogo().getStyle();
        if (style == 1) {
            if (this.myItem.getStyleLogo().getData() != null) {
                this.bmBg = OtherUtils.getBitmapFromAsset(getContext(), this.myItem.getStyleLogo().getData());
            } else {
                this.bmBg = null;
            }
            setLocationLogo();
        } else if (style == 3) {
            this.bmBg = BitmapFactory.decodeFile(this.myItem.getStyleLogo().getData());
            setLocationLogo();
        } else if (style == 4) {
            this.bmBg = null;
            this.paint.setTextSize((float) this.myItem.getStyleLogo().getColor());
            if (this.myItem.getStyleLogo().getFont() != null) {
                this.paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), this.myItem.getStyleLogo().getFont()));
            } else {
                this.paint.setTypeface(null);
            }
        } else if (style == 6) {
            this.bmBg = OtherUtils.getBm(getContext(), this.myItem.getStyleLogo().getItemIcon(), 700);
            setLocationLogo();
        } else if (style == 7 && ActivityCompat.checkSelfPermission(getContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0) {
            new Thread(new Runnable() {
                public final void run() {
                    MyPreview.this.lambda$makeBmBg$0$MyPreview();
                }
            }).start();
        }
    }

    public /* synthetic */ void lambda$makeBmBg$0$MyPreview() {
        this.bmBg = BitmapFactory.decodeFile(this.myItem.getStyleLogo().getData());
    }

    public void setLocationLogo() {
        int i = this.myItem.getxLogo();
        int i2 = this.myItem.getyLogo();
        int sizeLogo = this.myItem.getSizeLogo();
        if (i == -1) {
            i = (this.w - sizeLogo) / 2;
        }
        if (i2 == -1) {
            i2 = (this.h - sizeLogo) / 2;
        }
        this.rectLogo.set(i, i2, i + sizeLogo, sizeLogo + i2);
        this.pSec.setStrokeWidth((float) (this.myItem.getSizeLogo() / 80));
    }
}
