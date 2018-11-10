package com.turingoal.bts.wps.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.blankj.utilcode.util.ImageUtils;
import com.turingoal.common.android.util.lang.TgDateUtil;

import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.PointerPointer;
import org.bytedeco.javacpp.opencv_core;

import java.io.File;
import java.io.FileDescriptor;
import java.io.InputStream;
import java.util.Date;

import static org.bytedeco.javacpp.opencv_core.NORM_MINMAX;
import static org.bytedeco.javacpp.opencv_core.normalize;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2HSV;
import static org.bytedeco.javacpp.opencv_imgproc.calcHist;
import static org.bytedeco.javacpp.opencv_imgproc.compareHist;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;

/**
 * 图片处理工具类
 */
public class TgImageUtil {
    public static final int DEFAULT_MAX_SIZE = 1024 * 500; // 500k

    private TgImageUtil() {
        throw new Error("工具类不能实例化！");
    }

    /**
     * 基于opoencv通过直方图对比图片相似度
     */
    public static double matchByHist(final String imgPath1, final String imgPath2) {
        // 储存基准图像和对比图像的矩阵
        opencv_core.Mat srcBse = imread(imgPath1);
        opencv_core.Mat srcTest1 = imread(imgPath2);
        // 转换到 HSV
        opencv_core.Mat hsvBase = new opencv_core.Mat();
        opencv_core.Mat hsvTest1 = new opencv_core.Mat();
        cvtColor(srcBse, hsvBase, CV_BGR2HSV);
        cvtColor(srcTest1, hsvTest1, CV_BGR2HSV);
        // 对hue通道使用30个bin,对saturatoin通道使用32个bin
        int hBins = 50;
        int sBins = 60;
        int[] histSize = {hBins, sBins};
        // hue的取值范围从0到256, saturation取值范围从0到180
        float[] hRanges = {0, 256};
        float[] sRanges = {0, 180};
        float[][] ranges = {hRanges, sRanges};
        // 使用第0和第1通道
        int[] channels = {0, 1};
        // 直方图
        opencv_core.Mat histBase = new opencv_core.Mat();
        opencv_core.Mat histTest1 = new opencv_core.Mat();
        // 计算HSV图像的直方图
        calcHist(hsvBase, 1, new IntPointer(channels), new opencv_core.Mat(), histBase, 2, new IntPointer(histSize),
                new PointerPointer(ranges), true, false);
        normalize(histBase, histBase, 0, 1, NORM_MINMAX, -1, new opencv_core.Mat());
        calcHist(hsvTest1, 1, new IntPointer(channels), new opencv_core.Mat(), histTest1, 2, new IntPointer(histSize),
                new PointerPointer(ranges), true, false);
        normalize(histTest1, histTest1, 0, 1, NORM_MINMAX, -1, new opencv_core.Mat());
        // 应用不同的直方图对比方法,0,1,2,3
        return compareHist(histBase, histTest1, 0);
    }

    /**
     * bitmap转byteArr
     *
     * @param bitmap bitmap对象
     * @param format 格式
     * @return 字节数组
     */
    public static byte[] bitmap2Bytes(final Bitmap bitmap, final Bitmap.CompressFormat format) {
        return ImageUtils.bitmap2Bytes(bitmap, format);
    }

    /**
     * byteArr转bitmap
     *
     * @param bytes 字节数组
     * @return bitmap
     */
    public static Bitmap bytes2Bitmap(final byte[] bytes) {
        return ImageUtils.bytes2Bitmap(bytes);
    }

    /**
     * drawable转bitmap
     *
     * @param drawable drawable对象
     * @return bitmap
     */
    public static Bitmap drawable2Bitmap(final Drawable drawable) {
        return ImageUtils.drawable2Bitmap(drawable);
    }

    /**
     * bitmap转drawable
     *
     * @param bitmap bitmap对象
     * @return drawable
     */
    public static Drawable bitmap2Drawable(final Bitmap bitmap) {
        return ImageUtils.bitmap2Drawable(bitmap);
    }

    /**
     * drawable转byteArr
     *
     * @param drawable drawable对象
     * @param format   格式
     * @return 字节数组
     */
    public static byte[] drawable2Bytes(final Drawable drawable, final Bitmap.CompressFormat format) {
        return ImageUtils.drawable2Bytes(drawable, format);
    }

    /**
     * byteArr转drawable
     *
     * @param bytes 字节数组
     * @return drawable
     */
    public static Drawable bytes2Drawable(final byte[] bytes) {
        return ImageUtils.bytes2Drawable(bytes);
    }

    /**
     * view转Bitmap
     *
     * @param view 视图
     * @return bitmap
     */
    public static Bitmap view2Bitmap(final View view) {
        return ImageUtils.view2Bitmap(view);
    }

    /**
     * 获得Bitmap
     */
    public static Bitmap getBitmap(final File file) {
        return ImageUtils.getBitmap(file);
    }

    /**
     * 获取bitmap
     *
     * @param file      文件
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(final File file, final int maxWidth, final int maxHeight) {
        return ImageUtils.getBitmap(file, maxWidth, maxHeight);
    }

    /**
     * 获取bitmap
     */
    public static Bitmap getBitmap(String filePath) {
        return ImageUtils.getBitmap(filePath);
    }

    /**
     * 获取bitmap
     *
     * @param filePath  文件路径
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(final String filePath, final int maxWidth, final int maxHeight) {
        return ImageUtils.getBitmap(filePath, maxWidth, maxHeight);
    }

    /**
     * 获取bitmap
     *
     * @param is 输入流
     * @return bitmap
     */
    public static Bitmap getBitmap(final InputStream is) {
        return ImageUtils.getBitmap(is);
    }

    /**
     * 获取bitmap
     *
     * @param is        输入流
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(final InputStream is, final int maxWidth, final int maxHeight) {
        return ImageUtils.getBitmap(is, maxWidth, maxHeight);
    }

    /**
     * 获取bitmap
     *
     * @param data   数据
     * @param offset 偏移量
     * @return bitmap
     */
    public static Bitmap getBitmap(final byte[] data, final int offset) {
        return ImageUtils.getBitmap(data, offset);
    }

    /**
     * 获取bitmap
     *
     * @param data      数据
     * @param offset    偏移量
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(final byte[] data, final int offset, final int maxWidth, final int maxHeight) {
        return ImageUtils.getBitmap(data, offset, maxWidth, maxHeight);
    }

    /**
     * 获取bitmap
     *
     * @param id 资源id
     * @return bitmap
     */
    public static Bitmap getBitmap(final int id) {
        return ImageUtils.getBitmap(id);
    }

    /**
     * 获取bitmap
     *
     * @param id        资源id
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(final int id, final int maxWidth, final int maxHeight) {
        return ImageUtils.getBitmap(id, maxWidth, maxHeight);
    }

    /**
     * 获取bitmap
     *
     * @param fd 文件描述
     * @return bitmap
     */
    public static Bitmap getBitmap(final FileDescriptor fd) {
        return ImageUtils.getBitmap(fd);
    }

    /**
     * 获取bitmap
     *
     * @param fd        文件描述
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    public static Bitmap getBitmap(final FileDescriptor fd, final int maxWidth, final int maxHeight) {
        return ImageUtils.getBitmap(fd, maxWidth, maxHeight);
    }

    /**
     * 缩放图片
     *
     * @param src       源图片
     * @param newWidth  新宽度
     * @param newHeight 新高度
     * @return 缩放后的图片
     */
    public static Bitmap scale(final Bitmap src, final int newWidth, final int newHeight) {
        return ImageUtils.scale(src, newWidth, newHeight);
    }

    /**
     * 缩放图片
     *
     * @param src       源图片
     * @param newWidth  新宽度
     * @param newHeight 新高度
     * @param recycle   是否回收
     * @return 缩放后的图片
     */
    public static Bitmap scale(final Bitmap src, final int newWidth, final int newHeight, final boolean recycle) {
        return ImageUtils.scale(src, newWidth, newHeight, recycle);
    }

    /**
     * 缩放图片
     *
     * @param src         源图片
     * @param scaleWidth  缩放宽度倍数
     * @param scaleHeight 缩放高度倍数
     * @return 缩放后的图片
     */
    public static Bitmap scale(final Bitmap src, final float scaleWidth, final float scaleHeight) {
        return ImageUtils.scale(src, scaleWidth, scaleHeight);
    }

    /**
     * 缩放图片
     *
     * @param src         源图片
     * @param scaleWidth  缩放宽度倍数
     * @param scaleHeight 缩放高度倍数
     * @param recycle     是否回收
     * @return 缩放后的图片
     */
    public static Bitmap scale(final Bitmap src, final float scaleWidth, final float scaleHeight, final boolean recycle) {
        return ImageUtils.scale(src, scaleWidth, scaleHeight, recycle);
    }

    /**
     * 裁剪图片
     *
     * @param src    源图片
     * @param x      开始坐标x
     * @param y      开始坐标y
     * @param width  裁剪宽度
     * @param height 裁剪高度
     * @return 裁剪后的图片
     */
    public static Bitmap clip(final Bitmap src, final int x, final int y, final int width, final int height) {
        return ImageUtils.clip(src, x, y, width, height);
    }

    /**
     * 裁剪图片
     *
     * @param src     源图片
     * @param x       开始坐标x
     * @param y       开始坐标y
     * @param width   裁剪宽度
     * @param height  裁剪高度
     * @param recycle 是否回收
     * @return 裁剪后的图片
     */
    public static Bitmap clip(final Bitmap src, final int x, final int y, final int width, final int height, final boolean recycle) {
        return ImageUtils.clip(src, x, y, width, height, recycle);
    }

    /**
     * 倾斜图片
     *
     * @param src 源图片
     * @param kx  倾斜因子x
     * @param ky  倾斜因子y
     * @return 倾斜后的图片
     */
    public static Bitmap skew(final Bitmap src, final float kx, final float ky) {
        return ImageUtils.skew(src, kx, ky);
    }

    /**
     * 倾斜图片
     *
     * @param src     源图片
     * @param kx      倾斜因子x
     * @param ky      倾斜因子y
     * @param recycle 是否回收
     * @return 倾斜后的图片
     */
    public static Bitmap skew(final Bitmap src, final float kx, final float ky, final boolean recycle) {
        return ImageUtils.skew(src, kx, ky, recycle);
    }

    /**
     * 倾斜图片
     *
     * @param src 源图片
     * @param kx  倾斜因子x
     * @param ky  倾斜因子y
     * @param px  平移因子x
     * @param py  平移因子y
     * @return 倾斜后的图片
     */
    public static Bitmap skew(final Bitmap src, final float kx, final float ky, final float px, final float py) {
        return ImageUtils.skew(src, kx, ky, px, py);
    }

    /**
     * 倾斜图片
     *
     * @param src     源图片
     * @param kx      倾斜因子x
     * @param ky      倾斜因子y
     * @param px      平移因子x
     * @param py      平移因子y
     * @param recycle 是否回收
     * @return 倾斜后的图片
     */
    public static Bitmap skew(final Bitmap src, final float kx, final float ky, final float px, final float py, final boolean recycle) {
        return ImageUtils.skew(src, kx, ky, px, py, recycle);
    }

    /**
     * 旋转图片
     *
     * @param src     源图片
     * @param degrees 旋转角度
     * @param px      旋转点横坐标
     * @param py      旋转点纵坐标
     * @return 旋转后的图片
     */
    public static Bitmap rotate(final Bitmap src, final int degrees, final float px, final float py) {
        return ImageUtils.rotate(src, degrees, px, py);
    }

    /**
     * 旋转图片
     *
     * @param src     源图片
     * @param degrees 旋转角度
     * @param px      旋转点横坐标
     * @param py      旋转点纵坐标
     * @param recycle 是否回收
     * @return 旋转后的图片
     */
    public static Bitmap rotate(final Bitmap src, final int degrees, final float px, final float py, final boolean recycle) {
        return ImageUtils.rotate(src, degrees, px, py, recycle);
    }

    /**
     * 获取图片旋转角度
     *
     * @param filePath 文件路径
     * @return 旋转角度
     */
    public static int getRotateDegree(final String filePath) {
        return ImageUtils.getRotateDegree(filePath);
    }

    /**
     * 转为圆形图片
     *
     * @param src 源图片
     * @return 圆形图片
     */
    public static Bitmap toRound(final Bitmap src) {
        return ImageUtils.toRound(src);
    }

    /**
     * 转为圆形图片
     *
     * @param src     源图片
     * @param recycle 是否回收
     * @return 圆形图片
     */
    public static Bitmap toRound(final Bitmap src, final boolean recycle) {
        return ImageUtils.toRound(src, recycle);
    }

    /**
     * 转为圆角图片
     *
     * @param src    源图片
     * @param radius 圆角的度数
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(final Bitmap src, final float radius) {
        return ImageUtils.toRoundCorner(src, radius);
    }

    /**
     * 转为圆角图片
     *
     * @param src     源图片
     * @param radius  圆角的度数
     * @param recycle 是否回收
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(final Bitmap src, final float radius, final boolean recycle) {
        return ImageUtils.toRoundCorner(src, radius, recycle);
    }

    /**
     * 快速模糊
     * <p>先缩小原图，对小图进行模糊，再放大回原先尺寸</p>
     *
     * @param src    源图片
     * @param scale  缩放比例(0...1)
     * @param radius 模糊半径
     * @return 模糊后的图片
     */
    public static Bitmap fastBlur(final Bitmap src,
                                  @FloatRange(from = 0, to = 1, fromInclusive = false) final float scale,
                                  @FloatRange(from = 0, to = 25, fromInclusive = false) final float radius) {
        return ImageUtils.fastBlur(src, scale, radius);
    }

    /**
     * 快速模糊图片
     * <p>先缩小原图，对小图进行模糊，再放大回原先尺寸</p>
     *
     * @param src     源图片
     * @param scale   缩放比例(0...1)
     * @param radius  模糊半径(0...25)
     * @param recycle 是否回收
     * @return 模糊后的图片
     */
    public static Bitmap fastBlur(final Bitmap src,
                                  @FloatRange(from = 0, to = 1, fromInclusive = false) final float scale,
                                  @FloatRange(from = 0, to = 25, fromInclusive = false) final float radius,
                                  boolean recycle) {
        return ImageUtils.fastBlur(src, scale, radius, recycle);
    }

    /**
     * renderScript模糊图片
     * <p>API大于17</p>
     *
     * @param src    源图片
     * @param radius 模糊半径(0...25)
     * @return 模糊后的图片
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static Bitmap renderScriptBlur(final Bitmap src,
                                          @FloatRange(from = 0, to = 25, fromInclusive = false) final float radius) {
        return ImageUtils.renderScriptBlur(src, radius);
    }

    /**
     * stack模糊图片
     *
     * @param src     源图片
     * @param radius  模糊半径
     * @param recycle 是否回收
     * @return stack模糊后的图片
     */
    public static Bitmap stackBlur(final Bitmap src, final int radius, final boolean recycle) {
        return ImageUtils.stackBlur(src, radius, recycle);
    }


    /**
     * 添加圆形颜色边框
     *
     * @param src        源图片
     * @param borderSize 边框宽度
     * @param color      边框的颜色值
     * @param recycle    是否回收
     * @return 带颜色边框图
     */
    public static Bitmap addFrame(final Bitmap src, final int borderSize, final int color, final boolean recycle) {
        return ImageUtils.addCircleBorder(src, borderSize, color, recycle);
    }

    /**
     * 添加圆角颜色边框
     *
     * @param src          源图片
     * @param borderSize   边框宽度
     * @param color        边框的颜色值
     * @param borderRedius 边框角度
     * @return 带颜色边框图
     */
    public static Bitmap addCircleBorder(final Bitmap src, final int borderSize, final int color, final float borderRedius, final boolean recycle) {
        return ImageUtils.addCornerBorder(src, borderSize, color, borderRedius, recycle);
    }

    /**
     * 添加倒影
     *
     * @param src              源图片的
     * @param reflectionHeight 倒影高度
     * @return 带倒影图片
     */
    public static Bitmap addReflection(final Bitmap src, final int reflectionHeight) {
        return ImageUtils.addReflection(src, reflectionHeight);
    }

    /**
     * 添加倒影
     *
     * @param src              源图片的
     * @param reflectionHeight 倒影高度
     * @param recycle          是否回收
     * @return 带倒影图片
     */
    public static Bitmap addReflection(final Bitmap src, final int reflectionHeight, final boolean recycle) {
        return ImageUtils.addReflection(src, reflectionHeight, recycle);
    }

    /**
     * 添加文字水印
     *
     * @param src      源图片
     * @param content  水印文本
     * @param textSize 水印字体大小
     * @param color    水印字体颜色
     * @param x        起始坐标x
     * @param y        起始坐标y
     * @return 带有文字水印的图片
     */
    public static Bitmap addTextWatermark(final Bitmap src,
                                          final String content,
                                          final int textSize,
                                          final int color,
                                          final float x,
                                          final float y) {
        return ImageUtils.addTextWatermark(src, content, textSize, color, x, y);
    }

    /**
     * 添加文字水印
     *
     * @param src      源图片
     * @param content  水印文本
     * @param textSize 水印字体大小
     * @param color    水印字体颜色
     * @param x        起始坐标x
     * @param y        起始坐标y
     * @param recycle  是否回收
     * @return 带有文字水印的图片
     */
    public static Bitmap addTextWatermark(final Bitmap src,
                                          final String content,
                                          final float textSize,
                                          final int color,
                                          final float x,
                                          final float y,
                                          final boolean recycle) {
        return ImageUtils.addTextWatermark(src, content, textSize, color, x, y, recycle);
    }

    /**
     * 添加图片水印
     *
     * @param src       源图片
     * @param watermark 图片水印
     * @param x         起始坐标x
     * @param y         起始坐标y
     * @param alpha     透明度
     * @return 带有图片水印的图片
     */
    public static Bitmap addImageWatermark(final Bitmap src, final Bitmap watermark, final int x, final int y, final int alpha) {
        return ImageUtils.addImageWatermark(src, watermark, x, y, alpha);
    }

    /**
     * 添加图片水印
     *
     * @param src       源图片
     * @param watermark 图片水印
     * @param x         起始坐标x
     * @param y         起始坐标y
     * @param alpha     透明度
     * @param recycle   是否回收
     * @return 带有图片水印的图片
     */
    public static Bitmap addImageWatermark(final Bitmap src, final Bitmap watermark, final int x, final int y, final int alpha, final boolean recycle) {
        return ImageUtils.addImageWatermark(src, watermark, x, y, alpha, recycle);
    }

    /**
     * 转为alpha位图
     *
     * @param src 源图片
     * @return alpha位图
     */
    public static Bitmap toAlpha(final Bitmap src) {
        return ImageUtils.toAlpha(src);
    }

    /**
     * 转为alpha位图
     *
     * @param src     源图片
     * @param recycle 是否回收
     * @return alpha位图
     */
    public static Bitmap toAlpha(final Bitmap src, final Boolean recycle) {
        return ImageUtils.toAlpha(src, recycle);
    }

    /**
     * 转为灰度图片
     *
     * @param src 源图片
     * @return 灰度图
     */
    public static Bitmap toGray(final Bitmap src) {
        return ImageUtils.toGray(src);
    }

    /**
     * 转为灰度图片
     *
     * @param src     源图片
     * @param recycle 是否回收
     * @return 灰度图
     */
    public static Bitmap toGray(final Bitmap src, final boolean recycle) {
        return ImageUtils.toGray(src, recycle);
    }

    /**
     * 保存图片
     *
     * @param src      源图片
     * @param filePath 要保存到的文件路径
     * @param format   格式
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean save(final Bitmap src, final String filePath, final Bitmap.CompressFormat format) {
        return ImageUtils.save(src, filePath, format);
    }

    /**
     * 保存图片
     *
     * @param src    源图片
     * @param file   要保存到的文件
     * @param format 格式
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean save(final Bitmap src, final File file, final Bitmap.CompressFormat format) {
        return ImageUtils.save(src, file, format);
    }

    /**
     * 保存图片
     *
     * @param src      源图片
     * @param filePath 要保存到的文件路径
     * @param format   格式
     * @param recycle  是否回收
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean save(final Bitmap src, final String filePath, final Bitmap.CompressFormat format, final boolean recycle) {
        return ImageUtils.save(src, filePath, format, recycle);
    }

    /**
     * 保存图片
     *
     * @param src     源图片
     * @param file    要保存到的文件
     * @param format  格式
     * @param recycle 是否回收
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean save(final Bitmap src, final File file, final Bitmap.CompressFormat format, final boolean recycle) {
        return ImageUtils.save(src, file, format, recycle);
    }

    /**
     * 根据文件名判断文件是否为图片
     *
     * @param file 　文件
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isImage(final File file) {
        return ImageUtils.isImage(file);
    }

    /**
     * 根据文件名判断文件是否为图片
     *
     * @param filePath 　文件路径
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isImage(final String filePath) {
        return ImageUtils.isImage(filePath);
    }

    /**
     * 获取图片类型
     *
     * @param filePath 文件路径
     * @return 图片类型
     */
    public static String getImageType(final String filePath) {
        return ImageUtils.getImageType(filePath);
    }

    /**
     * 获取图片类型
     *
     * @param file 文件
     * @return 图片类型
     */
    public static String getImageType(final File file) {
        return ImageUtils.getImageType(file);
    }

    /**
     * 按缩放压缩
     *
     * @param src       源图片
     * @param newWidth  新宽度
     * @param newHeight 新高度
     * @return 缩放压缩后的图片
     */
    public static Bitmap compressByScale(final Bitmap src, final int newWidth, final int newHeight) {
        return ImageUtils.compressByScale(src, newWidth, newHeight);
    }

    /**
     * 按缩放压缩
     *
     * @param src       源图片
     * @param newWidth  新宽度
     * @param newHeight 新高度
     * @param recycle   是否回收
     * @return 缩放压缩后的图片
     */
    public static Bitmap compressByScale(final Bitmap src, final int newWidth, final int newHeight, final boolean recycle) {
        return ImageUtils.compressByScale(src, newWidth, newHeight, recycle);
    }

    /**
     * 按缩放压缩
     *
     * @param src         源图片
     * @param scaleWidth  缩放宽度倍数
     * @param scaleHeight 缩放高度倍数
     * @return 缩放压缩后的图片
     */
    public static Bitmap compressByScale(final Bitmap src, final float scaleWidth, final float scaleHeight) {
        return ImageUtils.compressByScale(src, scaleWidth, scaleHeight);
    }

    /**
     * 按缩放压缩
     *
     * @param src         源图片
     * @param scaleWidth  缩放宽度倍数
     * @param scaleHeight 缩放高度倍数
     * @param recycle     是否回收
     * @return 缩放压缩后的图片
     */
    public static Bitmap compressByScale(final Bitmap src, final float scaleWidth, final float scaleHeight, final boolean recycle) {
        return ImageUtils.compressByScale(src, scaleWidth, scaleHeight, recycle);
    }

    /**
     * 按质量压缩
     *
     * @param src     源图片
     * @param quality 质量
     * @return 质量压缩后的图片
     */
    public static Bitmap compressByQuality(final Bitmap src, @IntRange(from = 0, to = 100) final int quality) {
        return ImageUtils.compressByQuality(src, quality);
    }

    /**
     * 按质量压缩
     *
     * @param src     源图片
     * @param quality 质量
     * @param recycle 是否回收
     * @return 质量压缩后的图片
     */
    public static Bitmap compressByQuality(final Bitmap src, @IntRange(from = 0, to = 100) final int quality, final boolean recycle) {
        return ImageUtils.compressByQuality(src, quality, recycle);
    }

    /**
     * 按质量压缩
     *
     * @param src         源图片
     * @param maxByteSize 允许最大值字节数
     * @return 质量压缩压缩过的图片
     */
    public static Bitmap compressByQuality(final Bitmap src, final long maxByteSize) {
        return ImageUtils.compressByQuality(src, maxByteSize);
    }

    /**
     * 按质量压缩
     *
     * @param src         源图片
     * @param maxByteSize 允许最大值字节数
     * @param recycle     是否回收
     * @return 质量压缩压缩过的图片
     */
    public static Bitmap compressByQuality(final Bitmap src, final long maxByteSize, final boolean recycle) {
        return ImageUtils.compressByQuality(src, maxByteSize, recycle);
    }

    /**
     * 按采样大小压缩
     *
     * @param src        源图片
     * @param sampleSize 采样率大小
     * @return 按采样率压缩后的图片
     */
    public static Bitmap compressBySampleSize(final Bitmap src, final int sampleSize) {
        return ImageUtils.compressByQuality(src, sampleSize);
    }

    /**
     * 按采样大小压缩
     *
     * @param src        源图片
     * @param sampleSize 采样率大小
     * @param recycle    是否回收
     * @return 按采样率压缩后的图片
     */
    public static Bitmap compressBySampleSize(final Bitmap src, final int sampleSize, final boolean recycle) {
        return ImageUtils.compressByQuality(src, sampleSize, recycle);
    }

    /**
     * 压缩图片并保存，固定款高比例
     *
     * @param file      图片文件
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return 压缩之后的图片
     */
//    public static void compressAndSave(final File file, final int maxWidth, final int maxHeight, final String targetFielPath) {
//        Bitmap bitmap = ImageUtils.getBitmap(file, maxWidth, maxHeight);
//        //  Bitmap bitmap = ImageUtils.getBitmap(file, maxWidth, maxHeight);
//        ImageUtils.save(bitmap, targetFielPath, Bitmap.CompressFormat.JPEG, true);
//    }

    /**
     * 压缩图片并保存添加时间水印，固定款高比例
     */
    public static void compressAndSaveWithTimeWatermark(final String filePath, final int maxWidth, final int maxHeight, final String targetFielPath) {
        Bitmap bitmap = getBitmap(filePath, maxWidth, maxHeight);
        // 添加水印
        bitmap = addTextWatermark(bitmap, TgDateUtil.date2String(new Date(), TgDateUtil.FORMAT_YYYY_MM_DD_HH_MM_SS), 18, Color.RED, 10f, 10f);
        save(bitmap, targetFielPath, Bitmap.CompressFormat.JPEG, true);
    }


    /**
     * calculateInSampleSize
     *
     * @param options   图片流
     * @param reqWidth  目标宽度
     * @param reqHeight 目标高度
     * @return 合适的压缩比率
     */
//    public static int calculateInSampleSize(final BitmapFactory.Options options, final int reqWidth, final int reqHeight) {
//        if (reqWidth == 0 || reqHeight == 0) {
//            return 1;
//        }
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
//        if (height > reqHeight || width > reqWidth) {
//            final int heightRatio = Math.round((float) height / (float) reqHeight);
//            final int widthRatio = Math.round((float) width / (float) reqWidth);
//            if (heightRatio < widthRatio) {
//                inSampleSize = heightRatio;
//            } else {
//                inSampleSize = widthRatio;
//            }
//        }
//        return inSampleSize;
//    }

    /**
     * 往SD卡写入图片的方法
     */
    public static void savaImageToSdCard(final String imagePath, final Bitmap bitmap, final Activity context) throws Exception {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        File file = new File(imagePath);
        if (!file.getParentFile().exists()) {
            boolean f = file.getParentFile().mkdirs(); // 创建文件夹
        }
        ImageUtils.save(bitmap, file, Bitmap.CompressFormat.JPEG, true);
    }
}
