package com.revogi.delite.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.revogi.delite.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 类描述：扫描图
 * 创建人：ZZ
 * 创建时间：2016-07-23 11:21
 * 修改人：Administrator
 * 修改时间：2016-07-23 11:21
 * 修改备注：
 */

public class ScanView extends View {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private Paint mPaint;// 画笔
    private Bitmap mScanBmp;// 执行扫描运动的图片
    private int mOffsetArgs = 0;// 扫描运动偏移量参数
    private Bitmap pointBitmap;// 标识设备的圆点
    private int mPointCount = 0;// 圆点总数
    private List<String> mPointArray = new ArrayList<String>();// 存放偏移量的map
    private Random mRandom = new Random();
    private int mWidth, mHeight;// 宽高
    private int mOutWidth;// 外圆宽度(w/4/5*2=w/10)
    private int mCx, mCy;// x、y轴中心点
    private int mOutsideRadius, mInsideRadius;// 外、内圆半径

    public ScanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ScanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScanView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        this.mContext = context;
        this.pointBitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable
                .ic_scan_light));

    }

    /**
     * 测量视图及其内容,以确定所测量的宽度和高度(测量获取控件尺寸).
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取控件区域宽高
        if (mWidth == 0 || mHeight == 0) {
            final int minimumWidth = getSuggestedMinimumWidth();
            final int minimumHeight = getSuggestedMinimumHeight();
            mWidth = resolveMeasured(widthMeasureSpec, minimumWidth);
            mHeight = resolveMeasured(heightMeasureSpec, minimumHeight);
            mScanBmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                    mContext.getResources(), R.drawable.ic_scan), mWidth
                    - mOutWidth, mWidth - mOutWidth, false);
            // 获取x/y轴中心点
            mCx = mWidth / 2;
            mCy = mHeight / 2;
            // 获取外圆宽度
            mOutWidth = mWidth / 10;
            // 计算外、内半径
            mOutsideRadius = mWidth / 2;// 外圆的半径
            mInsideRadius = (mWidth - mOutWidth) / 4 / 2;// 内圆的半径,除最外层,其它圆的半径=层数*insideRadius
        }
    }

    /**
     * 绘制视图--从外部向内部绘制
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 1.开始绘制园
        mPaint.setAntiAlias(true);
        //绘制外圆
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.colorScanViewOutsideCircle));// 外圆颜色
        canvas.drawCircle(mCx, mCy, mOutsideRadius, mPaint);
        // 开始绘制内4圆 整个园，下面绘制的都只是圆环
        mPaint.setColor(getResources().getColor(R.color.colorScanViewInsideCircle));//内圆颜色
        canvas.drawCircle(mCx, mCy, mInsideRadius * 4, mPaint);
        // 开始绘制内3圆环
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(getResources().getColor(R.color.colorScanViewRingCircle));//内圆环颜色
        canvas.drawCircle(mCx, mCy, mInsideRadius * 3, mPaint);
        // 开始绘制内2圆环
        canvas.drawCircle(mCx, mCy, mInsideRadius * 2, mPaint);
        // 开始绘制内1圆环
        canvas.drawCircle(mCx, mCy, mInsideRadius, mPaint);
        // 2.开始绘制对角线
        canvas.drawLine(mCx - mInsideRadius * 4, mCy, mCx + mInsideRadius * 4, mCy, mPaint);// 绘制0°~180°对角线
        canvas.drawLine(mCx, mCy - mInsideRadius * 4, mCx, mCy + mInsideRadius * 4, mPaint);//
        // 绘制90°~270°对角线
        // 根据角度绘制对角线
        int startX, startY, endX, endY;
        double radian;
        // 绘制45°~225°对角线
        // 计算开始位置x/y坐标点
        radian = Math.toRadians((double) 45);// 将角度转换为弧度
        startX = (int) (mCx + mInsideRadius * 4 * Math.cos(radian));// 通过圆心坐标、半径和当前角度计算当前圆周的某点横坐标
        startY = (int) (mCy + mInsideRadius * 4 * Math.sin(radian));// 通过圆心坐标、半径和当前角度计算当前圆周的某点纵坐标
        // 计算结束位置x/y坐标点
        radian = Math.toRadians((double) 45 + 180);
        endX = (int) (mCx + mInsideRadius * 4 * Math.cos(radian));
        endY = (int) (mCy + mInsideRadius * 4 * Math.sin(radian));
        canvas.drawLine(startX, startY, endX, endY, mPaint);
        // 绘制135°~315°对角线
        // 计算开始位置x/y坐标点
        radian = Math.toRadians((double) 135);
        startX = (int) (mCx + mInsideRadius * 4 * Math.cos(radian));
        startY = (int) (mCy + mInsideRadius * 4 * Math.sin(radian));
        // 计算结束位置x/y坐标点
        radian = Math.toRadians((double) 135 + 180);
        endX = (int) (mCx + mInsideRadius * 4 * Math.cos(radian));
        endY = (int) (mCy + mInsideRadius * 4 * Math.sin(radian));
        canvas.drawLine(startX, startY, endX, endY, mPaint);
        // 3.绘制扫描扇形图
        canvas.save();
        canvas.rotate(mOffsetArgs, mCx, mCy);// 绘制旋转角度,参数一：角度;参数二：x中心;参数三：y中心.
        mOffsetArgs += 3;
        canvas.drawBitmap(mScanBmp, mCx - mScanBmp.getWidth() / 2, mCy
                - mScanBmp.getHeight() / 2, null);// 绘制Bitmap扫描图片效果
        // 4.开始绘制动态点
        canvas.restore();
        if (mPointCount > 0) {// 当圆点总数>0时,进入下一层判断
            if (mPointCount > mPointArray.size()) {// 当圆点总数大于存储坐标点数目时,说明有增加,需要重新生成随机坐标点
                int mx = mInsideRadius + mRandom.nextInt(mInsideRadius * 6);
                int my = mInsideRadius + mRandom.nextInt(mInsideRadius * 6) + (mCy - mInsideRadius * 4);
                mPointArray.add(mx + "/" + my);
            }
            // 开始绘制坐标点
            for (int i = 0; i < mPointArray.size(); i++) {
                String[] result = mPointArray.get(i).split("/");
                canvas.drawBitmap(pointBitmap,
                        Integer.parseInt(result[0]),
                        Integer.parseInt(result[1]), null);
            }
        }
        this.invalidate();
    }

    /**
     * 新增动态点
     */
    public void addPoint() {
        mPointCount++;
        this.invalidate();
    }

    /**
     * 解析获取控件宽高
     *
     * @param measureSpec
     * @param desired
     * @return
     */
    private int resolveMeasured(int measureSpec, int desired) {
        int result;
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.UNSPECIFIED:
                result = desired;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(specSize, desired);
                break;
            case MeasureSpec.EXACTLY:
            default:
                result = specSize;
        }
        return result;
    }
}