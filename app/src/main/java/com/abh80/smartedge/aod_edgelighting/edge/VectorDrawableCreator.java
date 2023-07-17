package com.abh80.smartedge.aod_edgelighting.edge;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;

import androidx.core.view.MotionEventCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;


import com.abh80.smartedge.aod_edgelighting.item.ItemIcon;
import com.abh80.smartedge.aod_edgelighting.item.ItemPaths;

import org.xmlpull.v1.XmlPullParser;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class VectorDrawableCreator {

    private static final int[] BIN_XML_ATTRS = {16843093, 16843097, 16843778, 16843779, 16843780, 16843781};
    private static final byte[][] BIN_XML_STRINGS = {"width".getBytes(), "height".getBytes(), "viewportWidth".getBytes(), "viewportHeight".getBytes(), "fillColor".getBytes(), "pathData".getBytes(), "path".getBytes(), "vector".getBytes(), "http://schemas.android.com/apk/res/android".getBytes()};
    private static final short CHUNK_TYPE_END_TAG = 259;
    private static final short CHUNK_TYPE_RES_MAP = 384;
    private static final short CHUNK_TYPE_START_TAG = 258;
    private static final short CHUNK_TYPE_STR_POOL = 1;
    private static final short CHUNK_TYPE_XML = 3;
    private static final short VALUE_TYPE_COLOR = 7424;
    private static final short VALUE_TYPE_DIMENSION = 1280;
    private static final short VALUE_TYPE_FLOAT = 1024;
    private static final short VALUE_TYPE_STRING = 768;

    public static Drawable getVector(Context context, MyItem myItem) {
        ArrayList arrayList = new ArrayList();
        Iterator<ItemPaths> it = myItem.getItemIcon().paths.iterator();
        while (it.hasNext()) {
            ItemPaths next = it.next();
            arrayList.add(new PathData(next.data, Color.parseColor("#" + next.opacity + "ffffff")));
        }
        return getVectorDrawable(context, 80, 80, 29.0f, 29.0f, arrayList);
    }

    public static Drawable getVector(Context context, ItemIcon itemIcon, int i) {
        ArrayList arrayList = new ArrayList();
        Iterator<ItemPaths> it = itemIcon.paths.iterator();
        while (it.hasNext()) {
            ItemPaths next = it.next();
            arrayList.add(new PathData(next.data, Color.parseColor("#" + next.opacity + "ffffff")));
        }
        return getVectorDrawable(context, i, i, 64.0f, 64.0f, arrayList);
    }

    public static Drawable getVectorDrawable(Context context, int i, int i2, float f, float f2, List<PathData> list) {
        byte[] createBinaryDrawableXml = createBinaryDrawableXml(i, i2, f, f2, list);
        try {
            Class<?> cls = Class.forName("android.content.res.XmlBlock");
            Constructor<?> constructor = cls.getConstructor(byte[].class);
            Method declaredMethod = cls.getDeclaredMethod("newParser");
            constructor.setAccessible(true);
            declaredMethod.setAccessible(true);
            XmlPullParser xmlPullParser = (XmlPullParser) declaredMethod.invoke(constructor.newInstance(createBinaryDrawableXml), new Object[0]);
            if (Build.VERSION.SDK_INT >= 24) {
                return Drawable.createFromXml(context.getResources(), xmlPullParser);
            }
            AttributeSet asAttributeSet = Xml.asAttributeSet(xmlPullParser);
            for (int next = xmlPullParser.next(); next != 2; next = xmlPullParser.next()) {
            }
            return VectorDrawableCompat.createFromXmlInner(context.getResources(), xmlPullParser, asAttributeSet, null);
        } catch (Exception e) {
            Log.e(VectorDrawableCreator.class.getSimpleName(), "Vector creation failed", e);
            return null;
        }
    }

    private static byte[] createBinaryDrawableXml(int i, int i2, float f, float f2, List<PathData> list) {
        ArrayList<byte[]> arrayList = new ArrayList(Arrays.asList(BIN_XML_STRINGS));
        for (PathData pathData : list) {
            arrayList.add(pathData.data);
        }
        ByteBuffer allocate = ByteBuffer.allocate(8192);
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        allocate.putShort((short) 3);
        allocate.putShort((short) 8);
        int position = allocate.position();
        allocate.position(allocate.position() + 4);
        int position2 = allocate.position();
        allocate.putShort((short) 1);
        allocate.putShort((short) 28);
        int position3 = allocate.position();
        allocate.position(allocate.position() + 4);
        allocate.putInt(arrayList.size());
        allocate.putInt(0);
        allocate.putInt(256);
        int position4 = allocate.position();
        allocate.position(allocate.position() + 4);
        allocate.putInt(0);
        int i3 = 0;
        for (byte[] bArr : arrayList) {
            allocate.putInt(i3);
            i3 += bArr.length + (bArr.length > 127 ? 5 : 3);
        }
        int position5 = allocate.position();
        allocate.putInt(position4, allocate.position() - position2);
        allocate.position(position5);
        for (byte[] bArr2 : arrayList) {
            if (bArr2.length > 127) {
                byte length = (byte) (((bArr2.length & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | 32768) >>> 8);
                byte length2 = (byte) (bArr2.length & 255);
                allocate.put(length);
                allocate.put(length2);
                allocate.put(length);
                allocate.put(length2);
            } else {
                byte length3 = (byte) bArr2.length;
                allocate.put(length3);
                allocate.put(length3);
            }
            allocate.put(bArr2);
            allocate.put((byte) 0);
        }
        if (allocate.position() % 4 != 0) {
            allocate.put(new byte[(4 - (allocate.position() % 4))]);
        }
        int position6 = allocate.position();
        allocate.putInt(position3, allocate.position() - position2);
        allocate.position(position6);
        allocate.putShort(CHUNK_TYPE_RES_MAP);
        allocate.putShort((short) 8);
        int[] iArr = BIN_XML_ATTRS;
        allocate.putInt((iArr.length * 4) + 8);
        for (int i4 : iArr) {
            allocate.putInt(i4);
        }
        int position7 = allocate.position();
        int putStartTag = putStartTag(allocate, 7, 4);
        putAttribute(allocate, 0, -1, VALUE_TYPE_DIMENSION, (i << 8) + 1);
        putAttribute(allocate, 1, -1, VALUE_TYPE_DIMENSION, (i2 << 8) + 1);
        putAttribute(allocate, 2, -1, VALUE_TYPE_FLOAT, Float.floatToRawIntBits(f));
        putAttribute(allocate, 3, -1, VALUE_TYPE_FLOAT, Float.floatToRawIntBits(f2));
        int position8 = allocate.position();
        allocate.putInt(putStartTag, allocate.position() - position7);
        allocate.position(position8);
        for (int i5 = 0; i5 < list.size(); i5++) {
            int position9 = allocate.position();
            int putStartTag2 = putStartTag(allocate, 6, 2);
            putAttribute(allocate, 4, -1, VALUE_TYPE_COLOR, list.get(i5).color);
            int i6 = i5 + 9;
            putAttribute(allocate, 5, i6, VALUE_TYPE_STRING, i6);
            int position10 = allocate.position();
            allocate.putInt(putStartTag2, allocate.position() - position9);
            allocate.position(position10);
            putEndTag(allocate, 6);
        }
        putEndTag(allocate, 7);
        int position11 = allocate.position();
        allocate.putInt(position, allocate.position());
        allocate.position(position11);
        byte[] bArr3 = new byte[allocate.position()];
        allocate.clear();
        allocate.get(bArr3);
        return bArr3;
    }

    private static int putStartTag(ByteBuffer byteBuffer, int i, int i2) {
        byteBuffer.putShort(CHUNK_TYPE_START_TAG);
        byteBuffer.putShort((short) 16);
        int position = byteBuffer.position();
        byteBuffer.putInt(0);
        byteBuffer.putInt(0);
        byteBuffer.putInt(-1);
        byteBuffer.putInt(-1);
        byteBuffer.putInt(i);
        byteBuffer.putShort((short) 20);
        byteBuffer.putShort((short) 20);
        byteBuffer.putShort((short) i2);
        byteBuffer.putShort((short) 0);
        byteBuffer.putShort((short) 0);
        byteBuffer.putShort((short) 0);
        return position;
    }

    private static void putEndTag(ByteBuffer byteBuffer, int i) {
        byteBuffer.putShort(CHUNK_TYPE_END_TAG);
        byteBuffer.putShort((short) 16);
        byteBuffer.putInt(24);
        byteBuffer.putInt(0);
        byteBuffer.putInt(-1);
        byteBuffer.putInt(-1);
        byteBuffer.putInt(i);
    }

    private static void putAttribute(ByteBuffer byteBuffer, int i, int i2, short s, int i3) {
        byteBuffer.putInt(8);
        byteBuffer.putInt(i);
        byteBuffer.putInt(i2);
        byteBuffer.putShort((short) 8);
        byteBuffer.putShort(s);
        byteBuffer.putInt(i3);
    }

    public static class PathData {
        public int color;
        public byte[] data;

        public PathData(byte[] bArr, int i) {
            this.data = bArr;
            this.color = i;
        }

        public PathData(String str, int i) {
            this(str.getBytes(StandardCharsets.UTF_8), i);
        }
    }
}
