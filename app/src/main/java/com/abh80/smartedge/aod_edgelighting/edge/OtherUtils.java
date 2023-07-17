package com.abh80.smartedge.aod_edgelighting.edge;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.net.MailTo;


import com.abh80.smartedge.R;
import com.abh80.smartedge.aod_edgelighting.item.ItemIcon;
import com.abh80.smartedge.aod_edgelighting.service.DrawServiceApply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class OtherUtils {
    public static Bitmap getBitmapFromAsset(Context context, String str) {
        try {
            InputStream open = context.getAssets().open(str);
            Bitmap decodeStream = BitmapFactory.decodeStream(open);
            open.close();
            return decodeStream;
        } catch (IOException unused) {
            return null;
        }
    }

    public static ArrayList<String> getFaceClock(Context context) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            String[] list = context.getAssets().list("clock");
            if (list != null && list.length > 0) {
                for (String str : list) {
                    arrayList.add("file:///android_asset/clock/" + str);
                }
            }
        } catch (IOException unused) {
        }
        return arrayList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x0270  */
    /* JADX WARNING: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
    public static void setPath(int i, int i2, Path path, Path path2, Paint paint, MyItem myItem) {
        boolean z;
        if (i != 0 || myItem != null) {
            float stroke = myItem.getStroke() / 2.0f;
            path2.reset();
            path.reset();
            if (myItem.getTypeScreen() == 203) {
                float f = (float) i;
                float f2 = f - stroke;
                float f3 = ((float) i2) - stroke;
                float f4 = myItem.getrTop() * 2.0f;
                float f5 = myItem.getrBot() * 2.0f;
                float f6 = myItem.getwInf();
                float f7 = myItem.gethInf();
                float f8 = myItem.getrTopInf();
                float f9 = f2 - f4;
                path.moveTo(f9, stroke);
                float f10 = stroke + f4;
                path.arcTo(new RectF(f9, stroke, f2, f10), -90.0f, 90.0f);
                float f11 = f3 - f5;
                path.arcTo(new RectF(f2 - f5, f11, f2, f3), 0.0f, 90.0f);
                path.arcTo(new RectF(stroke, f11, stroke + f5, f3), 90.0f, 90.0f);
                path.arcTo(new RectF(stroke, stroke, f10, f10), 180.0f, 90.0f);
                if (myItem.isInfinityType()) {
                    if (f7 < f8) {
                        f7 = f8;
                    }
                    float f12 = (f2 - f6) / 2.0f;
                    float f13 = stroke + f8;
                    path.arcTo(new RectF(f12 - f8, stroke, f12, f13), -90.0f, 90.0f);
                    float f14 = f7 + stroke;
                    float f15 = (f2 + f6) / 2.0f;
                    path.arcTo(new RectF(f12, f14, f15, f14 + f6), -180.0f, -180.0f);
                    path.arcTo(new RectF(f15, stroke, f15 + f8, f13), -180.0f, 90.0f);
                } else {
                    float f16 = myItem.getwInf() * 2.0f;
                    float sqrt = (float) (((double) f16) * (Math.sqrt(2.0d) - 1.0d));
                    float f17 = (float) (i / 2);
                    float f18 = f16 + stroke;
                    path.arcTo(new RectF(f17 - f16, stroke, f17, f18), -90.0f, 45.0f);
                    path.arcTo(new RectF((f - sqrt) / 2.0f, ((-sqrt) / 2.0f) + stroke, (f + sqrt) / 2.0f, (sqrt / 2.0f) + stroke), 135.0f, -90.0f);
                    path.arcTo(new RectF(f17, stroke, f16 + f17, f18), -135.0f, 45.0f);
                }
                path.lineTo(f9, stroke);
            } else if (myItem.getTypeScreen() == 201) {
                float f19 = ((float) i) - stroke;
                float f20 = ((float) i2) - stroke;
                float f21 = myItem.getrTop() * 2.0f;
                float f22 = myItem.getrBot() * 2.0f;
                float f23 = f19 - f21;
                path.moveTo(f23, stroke);
                float f24 = f21 + stroke;
                path.arcTo(new RectF(f23, stroke, f19, f24), -90.0f, 90.0f);
                float f25 = f20 - f22;
                path.arcTo(new RectF(f19 - f22, f25, f19, f20), 0.0f, 90.0f);
                path.arcTo(new RectF(stroke, f25, f22 + stroke, f20), 90.0f, 90.0f);
                path.arcTo(new RectF(stroke, stroke, f24, f24), 180.0f, 90.0f);
                float f26 = myItem.getxTop();
                float raTop = myItem.getRaTop();
                float f27 = myItem.getxBot();
                float raBot = myItem.getRaBot();
                float height = myItem.getHeight();
                float f28 = raTop + raBot;
                if (height < f28) {
                    height = f28;
                }
                float f29 = raBot * 2.0f;
                if (f27 < f29) {
                    f27 = f29;
                }
                if (f26 < f27) {
                    f26 = f27;
                }
                float degrees = (float) Math.toDegrees(Math.atan((double) ((height * 2.0f) / (f26 - f27))));
                double d = (double) (degrees / 2.0f);
                float tan = (float) (((double) raTop) * Math.tan(Math.toRadians(d)));
                float tan2 = (float) (((double) raBot) * Math.tan(Math.toRadians(d)));
                float f30 = ((f19 - f26) / 2.0f) - tan;
                float f31 = (raTop * 2.0f) + stroke;
                path.arcTo(new RectF((f30 - raTop) + stroke, stroke, f30 + raTop + stroke, f31), -90.0f, degrees);
                float f32 = ((f19 - f27) / 2.0f) + tan2;
                float f33 = height + stroke;
                float f34 = f33 - f29;
                float f35 = -degrees;
                path.arcTo(new RectF((f32 - raBot) + stroke, f34, f32 + raBot + stroke, f33), degrees + 90.0f, f35);
                float f36 = ((f27 + f19) / 2.0f) - tan2;
                path.arcTo(new RectF(f36 - raBot, f34, f36 + raBot, f33), 90.0f, f35);
                float f37 = ((f19 + f26) / 2.0f) + tan;
                path.arcTo(new RectF(f37 - raTop, stroke, f37 + raTop, f31), -90.0f - degrees, degrees);
                path.lineTo(f23, stroke);
            } else {
                if (myItem.getTypeScreen() == 202 && myItem.isHoleType()) {
                    path2.addCircle((float) myItem.getxC(), (float) myItem.getyC(), myItem.getwC() / 2.0f, Path.Direction.CW);
                } else if (myItem.getTypeScreen() == 202 && !myItem.isHoleType()) {
                    float hCVar = myItem.gethC() / 2.0f;
                    path2.addRoundRect(new RectF((float) myItem.getxC(), (float) myItem.getyC(), ((float) myItem.getxC()) + myItem.getwC(), ((float) myItem.getyC()) + myItem.gethC()), new float[]{hCVar, hCVar, hCVar, hCVar, hCVar, hCVar, hCVar, hCVar}, Path.Direction.CW);
                }
                path.addRoundRect(new RectF(stroke, stroke, ((float) i) - stroke, ((float) i2) - stroke), new float[]{myItem.getrTop(), myItem.getrTop(), myItem.getrTop(), myItem.getrTop(), myItem.getrBot(), myItem.getrBot(), myItem.getrBot(), myItem.getrBot()}, Path.Direction.CW);

            }
        }
    }

    public static void setPaint(int i, int i2, int i3, int i4, Paint paint, int i5, int[] iArr) {
        if (iArr == null || iArr.length <= 0) {
            paint.setColor(-1);
            paint.setShader(null);
        } else if (iArr.length <= 1) {
            paint.setShader(null);
            paint.setColor(iArr[0]);
        } else if (i5 == 0) {
            float f = (float) (i3 / 2);
            float f2 = (float) (i4 / 2);
            SweepGradient sweepGradient = new SweepGradient(f, f2, iArr, (float[]) null);
            Matrix matrix = new Matrix();
            sweepGradient.getLocalMatrix(matrix);
            matrix.setRotate((float) i, f, f2);
            sweepGradient.setLocalMatrix(matrix);
            paint.setShader(sweepGradient);
        } else {
            paint.setShader(new LinearGradient((float) i, (float) i2, (float) i3, (float) i4, iArr, (float[]) null, Shader.TileMode.REPEAT));
        }
    }

    public static String readAssets(Context context, String str) {
        try {
            InputStream open = context.getAssets().open(str);
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            return new String(bArr);
        } catch (IOException unused) {
            return "";
        }
    }

    public static Bitmap getBm(Drawable drawable, int i) {
        int i2 = i - 1;
        drawable.setBounds(0, 0, i2, i2);
        Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        drawable.draw(new Canvas(createBitmap));
        return createBitmap;
    }

    public static Bitmap getBm(Context context, ItemIcon itemIcon, int i) {
        Drawable vector = VectorDrawableCreator.getVector(context, itemIcon, i);
        int i2 = i - 1;
        vector.setBounds(0, 0, i2, i2);
        Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        vector.draw(new Canvas(createBitmap));
        return createBitmap;
    }

    public static ArrayList<MyItem> makeThemeDefault(Context context, float dimension, ItemIcon itemIcon) {
//        float dimension = context.getResources().getDimension(R.dimen.max_stroke5);
        ArrayList<MyItem> arrayList = new ArrayList<>();
        arrayList.add(new MyItem(dimension, "Default", null, Color.parseColor("#fe0000"), Color.parseColor("#fdff06"), Color.parseColor("#00fffc"), Color.parseColor("#0c00ff"), Color.parseColor("#ff00ea")));
        arrayList.add(new MyItem(dimension, "Default 1", itemIcon, Color.parseColor("#5400b4"), Color.parseColor("#0061d3"), Color.parseColor("#009148"), Color.parseColor("#cfd600"), Color.parseColor("#e76c00"), Color.parseColor("#c11321")));
        arrayList.add(new MyItem(dimension, "Default 2", itemIcon, Color.parseColor("#ff0000"), Color.parseColor("#48ff00"), Color.parseColor("#00fcff"), Color.parseColor("#0006ff"), Color.parseColor("#ff0000"), Color.parseColor("#00ff18")));
        arrayList.add(new MyItem(dimension, "Default 3", itemIcon, Color.parseColor("#ff0000"), Color.parseColor("#001eff"), Color.parseColor("#ffe400"), Color.parseColor("#00ff00"), Color.parseColor("#ff009c"), Color.parseColor("#e4ff00")));
        arrayList.add(new MyItem(dimension, "Default 4", itemIcon, Color.parseColor("#ff0000"), Color.parseColor("#02051a"), Color.parseColor("#ffe400"), Color.parseColor("#011301"), Color.parseColor("#ff009c")));
        arrayList.add(new MyItem(dimension, "Default 5", itemIcon, Color.parseColor("#ffffff"), Color.parseColor("#00f0ff"), Color.parseColor("#3000ff")));
        arrayList.add(new MyItem(dimension, "Default 6", itemIcon, Color.parseColor("#fffc00"), Color.parseColor("#484900"), Color.parseColor("#2aff00"), Color.parseColor("#022606"), Color.parseColor("#e7fc34")));
        arrayList.add(new MyItem(dimension, "Default 7", itemIcon, Color.parseColor("#5400b4"), Color.parseColor("#0061d3"), Color.parseColor("#009148"), Color.parseColor("#cfd600"), Color.parseColor("#e76c00"), Color.parseColor("#c11321")));
        arrayList.add(new MyItem(dimension, "Default 8", itemIcon, Color.parseColor("#fffc00"), Color.parseColor("#484900"), Color.parseColor("#2aff00"), Color.parseColor("#022606"), Color.parseColor("#e7fc34")));
        arrayList.add(new MyItem(dimension, "Default 9", itemIcon, Color.parseColor("#1e00ff"), Color.parseColor("#00fffc"), Color.parseColor("#ff0606"), Color.parseColor("#18ff00"), Color.parseColor("#00fffc")));
        return arrayList;
    }

    public static ArrayList<MyItem> makeThemeDrawableColor(Context context, float dimension, ItemIcon itemIcon,int speed,int color1,int color2,int color3,int color4,int color5) {
        ArrayList<MyItem> arrayList = new ArrayList<>();
        arrayList.add(new MyItem(dimension, "Default", itemIcon,speed , color1,color2, color3, color4,color5));
        return arrayList;
    }
    public static ArrayList<MyItem> makeThemeCustom4(Context context, float dimension, ItemIcon itemIcon,int speed,int color1,int color2,int color3,int color4) {
        ArrayList<MyItem> arrayList = new ArrayList<>();
        arrayList.add(new MyItem(dimension, "Default", itemIcon,speed, color1,color2, color3, color4));
        return arrayList;
    }
    public static ArrayList<MyItem> makeThemeCustom3(Context context, float dimension, ItemIcon itemIcon,int speed,int color1,int color2,int color3) {
        ArrayList<MyItem> arrayList = new ArrayList<>();
        arrayList.add(new MyItem(dimension, "Default", itemIcon,speed, color1,color2, color3));
        return arrayList;
    }
    public static ArrayList<MyItem> makeThemeCustom2(Context context, float dimension, ItemIcon itemIcon,int speed,int color1,int color2) {
        ArrayList<MyItem> arrayList = new ArrayList<>();
        arrayList.add(new MyItem(dimension, "Default", itemIcon, speed,color1,color2));
        return arrayList;
    }
    public static ArrayList<MyItem> makeThemeSingle(Context context, float dimension, ItemIcon itemIcon,int speed,int color1,int color2) {
        ArrayList<MyItem> arrayList = new ArrayList<>();
        arrayList.add(new MyItem(dimension, "Default", itemIcon,speed, color1,color2));
        return arrayList;
    }
    public static ArrayList<MyItem> makeThemeMultiColor(Context context, float dimension, ItemIcon itemIcon,int speed,int color1,int color2,int color3,int color4,int color5) {
        ArrayList<MyItem> arrayList = new ArrayList<>();
        arrayList.add(new MyItem(dimension, "Default", itemIcon,speed, color1,color2, color3, color4,color5));
        return arrayList;
    }

    private static void anim(final View view, Animation animation, final boolean z) {
        animation.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }

            @SuppressLint("WrongConstant")
            public void onAnimationEnd(Animation animation) {
                if (z) {
                    view.setVisibility(4);
                } else {
                    view.setVisibility(0);
                }
            }
        });
        view.startAnimation(animation);
    }

    public static void anim(View view, int i, boolean z) {

        anim(view, AnimationUtils.loadAnimation(view.getContext(), i), z);

    }

    @SuppressLint("WrongConstant")
    public static GradientDrawable oval(int i) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(1);
        gradientDrawable.setSize(256, 256);
        gradientDrawable.setColor(i);
        return gradientDrawable;
    }

    public static void saveFile(String str, Bitmap bitmap) throws Throwable {
        Throwable th;
        Exception e;
        if (bitmap != null) {
            FileOutputStream fileOutputStream = null;
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(str);
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream2);
                    try {
                        fileOutputStream2.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                } catch (Exception e3) {
                    e = e3;
                    fileOutputStream = fileOutputStream2;
                    try {
                        e.printStackTrace();
                        if (fileOutputStream == null) {
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (fileOutputStream != null) {
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Exception e5) {
                e = e5;
                e.printStackTrace();
                if (fileOutputStream == null) {
                    fileOutputStream.close();
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0021  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x002c A[SYNTHETIC, Splitter:B:21:0x002c] */
    /* JADX WARNING: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    public static void saveFilePng(String str, Bitmap bitmap) throws Throwable {
        Throwable th;
        Exception e;
        if (bitmap != null) {
            FileOutputStream fileOutputStream = null;
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(str);
                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream2);
                    try {
                        fileOutputStream2.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                } catch (Exception e3) {
                    e = e3;
                    fileOutputStream = fileOutputStream2;
                    try {
                        e.printStackTrace();
                        if (fileOutputStream == null) {
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (fileOutputStream != null) {
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Exception e5) {
                e = e5;
                e.printStackTrace();
                if (fileOutputStream == null) {
                    fileOutputStream.close();
                }
            }
        }
    }

    public static String getStore(Context context) {
        if (Build.VERSION.SDK_INT >= 29) {
            File externalFilesDir = context.getExternalFilesDir(null);
            if (externalFilesDir != null) {
                return externalFilesDir.getAbsolutePath();
            }
            return "/storage/emulated/0/Android/data/" + context.getPackageName();
        }
        return Environment.getExternalStorageDirectory() + "/Android/data/" + context.getPackageName();
    }

    @SuppressLint("WrongConstant")
//    public static boolean checkLiveOn(Context context) {
//        WallpaperManager wallpaperManager;
//        if (Build.VERSION.SDK_INT >= 23) {
//            wallpaperManager = (WallpaperManager) context.getSystemService("aod_wallpaper");
//        } else {
//            wallpaperManager = WallpaperManager.getInstance(context);
//        }
//        WallpaperInfo wallpaperInfo = wallpaperManager.getWallpaperInfo();
//        return wallpaperInfo != null && wallpaperInfo.getComponent().equals(new ComponentName(context, MyService.class));
//    }

    public static boolean checkDraw(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            return Settings.canDrawOverlays(context);
        }
        return true;
    }

    public static void startDraw(Context context) {
        if (checkDraw(context)) {
            setWallpaperDefault(context);
            context.startService(new Intent(context, DrawServiceApply.class));
        } else if (Build.VERSION.SDK_INT >= 23) {
            context.startActivity(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + context.getPackageName())));
        }
    }


    public static void setWallpaperDefault(Context context) {
        if (ActivityCompat.checkSelfPermission(context, "android.permission.READ_EXTERNAL_STORAGE") == 0) {
            new Thread(() -> OtherUtils.setWallpaperDefault(context)).start();
        }
    }

    @SuppressLint("MissingPermission")


    public static void drawClock(Canvas canvas, Paint paint, Paint paint2, int i, int i2, int i3) {
        Calendar instance = Calendar.getInstance();
        int i4 = instance.get(11);
        int i5 = instance.get(12);
        int i6 = instance.get(14) + (instance.get(13) * 1000);
        float f = (float) i3;
        int i7 = i3 / 2;
        float f2 = (float) (i7 + i);
        float f3 = (float) (i7 + i2);
        double d = (double) ((3.0f * f) / 10.0f);
        double d2 = (double) (((((float) i6) * 6.0f) / 1000.0f) - 90.0f);
        canvas.drawLine(f2, f3, f2 + ((float) (Math.cos(Math.toRadians(d2)) * d)), f3 + ((float) (d * Math.sin(Math.toRadians(d2)))), paint2);
        double d3 = (double) ((2.7f * f) / 10.0f);
        double d4 = (double) ((i5 * 6) - 90);
        canvas.drawLine(f2, f3, f2 + ((float) (Math.cos(Math.toRadians(d4)) * d3)), f3 + ((float) (d3 * Math.sin(Math.toRadians(d4)))), paint);
        double radians = Math.toRadians((double) ((((i4 * 60) + i5) / 2) - 90));
        double d5 = (double) ((f * 2.1f) / 10.0f);
        canvas.drawLine(f2, f3, f2 + ((float) (Math.cos(radians) * d5)), f3 + ((float) (d5 * Math.sin(radians))), paint);
    }

    @SuppressLint("WrongConstant")
    public static void feedback(Context context) {
        String[] strArr = {ConstMy.EMAIL};
        Intent intent = new Intent("android.intent.action.SENDTO");
        intent.setData(Uri.parse(MailTo.MAILTO_SCHEME));
        intent.putExtra("android.intent.extra.EMAIL", strArr);
        intent.putExtra("android.intent.extra.SUBJECT", ConstMy.TITLE_EMAIL);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(context, context.getString(R.string.no_email), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("WrongConstant")
    public static void ratePkg(Context context, String str) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + str));
        intent.addFlags(268435456);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    public static void openPolicy(Context context) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(ConstMy.POLICY));
            context.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(context, context.getString(R.string.no_browser), Toast.LENGTH_SHORT).show();
        }
    }
}
