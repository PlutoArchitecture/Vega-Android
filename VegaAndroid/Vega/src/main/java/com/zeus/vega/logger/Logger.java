package com.zeus.vega.logger;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.util.Log.ASSERT;
import static android.util.Log.DEBUG;
import static android.util.Log.ERROR;
import static android.util.Log.INFO;
import static android.util.Log.VERBOSE;
import static android.util.Log.WARN;
import static android.util.Log.getStackTraceString;

/**
 *
 * @author minggo(戴统民)
 * @date 2020/9/15
 */
public class Logger {
    private static boolean mLogOn = false;
    private static boolean isDebugStackShow = true;

    private static int mLogLevel = -1;

    private static SimpleDateFormat format;
    private static String filePath;

    static {
        mLogLevel = VERBOSE;
    }

    public static void setLogOn(boolean on) {
        mLogOn = on;
    }

    public static boolean isLogOn() {
        return mLogOn;
    }

    public static void setLogLevel(int level) {
        mLogLevel = level;
    }


    public static void println(int priority, String tag, String msg, Throwable tr) {
        if (priority < mLogLevel || !mLogOn) {
            return;
        }
    }

    public static void println(int priority, String tag, String msg) {
        println(priority, tag, msg, null);
    }

    public static void v(String tag, String msg, Throwable tr) {
        println(VERBOSE, tag, msg, tr);
    }

    public static void v(String tag, String msg) {
        v(tag, msg, null);
    }

    public static void v(String msg, Object... objs) {
        if (objs != null && objs.length > 0) {
            msg = String.format(msg, objs);
        }
        v(generateTag(), msg);
    }

    public static void v(String msg, Throwable tr, Object... objs) {
        if (objs != null && objs.length > 0) {
            msg = String.format(msg, objs);
        }
        v(generateTag(), msg, tr);
    }

    public static void d(String tag, String msg, Throwable tr) {
        println(DEBUG, tag, msg, tr);
    }

    public static void d(String tag, String msg) {
        d(tag, msg, null);
    }

    public static void d(String msg, Object... objs) {
        if (objs != null && objs.length > 0) {
            msg = String.format(msg, objs);
        }
        d(generateTag(), msg);
    }

    public static void d(String msg, Throwable tr, Object... objs) {
        if (objs != null && objs.length > 0) {
            msg = String.format(msg, objs);
        }
        d(generateTag(), msg, tr);
    }

    public static void i(String tag, String msg, Throwable tr) {
        println(INFO, tag, msg, tr);
    }

    public static void i(String tag, String msg) {
        i(tag, msg, null);
    }

    public static void i(String msg, Object... objs) {
        if (objs != null && objs.length > 0) {
            msg = String.format(msg, objs);
        }
        i(generateTag(), msg);
    }

    public static void i(String msg, Throwable tr, Object... objs) {
        if (objs != null && objs.length > 0) {
            msg = String.format(msg, objs);
        }
        i(generateTag(), msg, tr);
    }

    public static void w(String tag, String msg, Throwable tr) {
        println(WARN, tag, msg, tr);
    }

    public static void w(String tag, String msg) {
        w(tag, msg, null);
    }

    public static void w(String msg, Object... objs) {
        if (objs != null && objs.length > 0) {
            msg = String.format(msg, objs);
        }
        w(generateTag(), msg);
    }

    public static void w(String msg, Throwable tr, Object... objs) {
        if (objs != null && objs.length > 0) {
            msg = String.format(msg, objs);
        }
        w(generateTag(), msg, tr);
    }

    public static void e(String tag, String msg, Throwable tr) {
        println(ERROR, tag, msg, tr);
    }

    public static void e(String tag, String msg) {
        e(tag, msg, null);
    }

    public static void e(String msg, Object... objs) {
        if (objs != null && objs.length > 0) {
            msg = String.format(msg, objs);
        }
        e(generateTag(), msg);
    }

    public static void e(String msg, Throwable tr, Object... objs) {
        if (objs != null && objs.length > 0) {
            msg = String.format(msg, objs);
        }
        e(generateTag(), msg, tr);
    }

    public static void wtf(String tag, String msg, Throwable tr) {
        println(ASSERT, tag, msg, tr);
    }

    public static void wtf(String tag, String msg) {
        wtf(tag, msg, null);
    }

    public static void wtf(String tag, Throwable tr) {
        wtf(tag, null, tr);
    }

    public static void wtf(String msg, Object... objs) {
        if (objs != null && objs.length > 0) {
            msg = String.format(msg, objs);
        }
        wtf(generateTag(), msg);
    }

    public static void wtf(String msg, Throwable tr, Object... objs) {
        if (objs != null && objs.length > 0) {
            msg = String.format(msg, objs);
        }
        wtf(generateTag(), msg, tr);
    }

    private static String generateTag() {
        StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        return tag;
    }

    //导出日志，adb pull /sdcard/filename
    public static void initLogToFile(Context context, String filename){
        //临时加的日志输出到文件
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
            filePath = context.getExternalCacheDir()
                    .getAbsolutePath() + File.separator + filename;
        } else {// 如果SD卡不存在，就保存到本应用的目录下
            filePath = context.getFilesDir().getAbsolutePath()
                    + File.separator + filename;
        }
        try {
            if (format == null){
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
            ltf("init","*****************************\n");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String getTime(){
        long timeStamp = System.currentTimeMillis();
        int mill = (int) (timeStamp % 1000);
        String date = format.format(new Date(timeStamp));
        return date + "." + (mill < 100?("0"+mill):mill);
    }

    public static void ltf(String tag, String msg, Object... objs){
        try {
            File logFile = new File(filePath);
            if (!logFile.exists()){
                try {
                    logFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String LOG_FORMAT = "%s E/%s:";
            FileWriter filerWriter = new FileWriter(logFile, true);
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(String.format(LOG_FORMAT, getTime(), tag));
            if (objs != null && objs.length > 0) {
                msg = String.format(msg, objs);
            }
            bufWriter.write(msg);
            bufWriter.newLine();
            bufWriter.flush();
            filerWriter.flush();
            bufWriter.close();
            filerWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        ltf("stop","*****************************\n");
    }

    /**
     * 正式包不会显示堆栈调试，测试包是否显示-> 根据isDebugStackShow变量控制
     * @param msg
     */
    public static void debug(Object msg) {
        if (isLogOn() && isDebugStackShow) {
            Log.e(generateTag(), getStackTraceString(new Throwable(msg.toString())));
        }
    }
}