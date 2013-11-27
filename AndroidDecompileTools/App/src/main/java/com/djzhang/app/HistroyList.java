package com.djzhang.app;

/**
 * Created by djzhang on 11/27/13.
 */
public final class HistroyList {

    public static final int QQ2013_452_1605_ANDROID = 1;
    public static final int BBM_FOR_ANDROID = 2;


    public static String getApkPublicFold(int type) {
        switch (type) {
            case QQ2013_452_1605_ANDROID:
                return "/Volumes/SHARE/MacSystem/Home/Users/djzhang/DevIntellijIdea/apk-decompile/Projects/qq2013_4.5.2.1605_android/App/src/main/res/values/public.xml";
            case BBM_FOR_ANDROID:
                return "/Volumes/SHARE/MacSystem/Home/Users/djzhang/DevIntellijIdea/apk-decompile/Projects/bbm-for-android/App/src/instrumentTest/res/values/public.xml";
        }
        return null;
    }

    public static String getSrcResourceIdJavaFold(int type) {
        switch (type) {
            case QQ2013_452_1605_ANDROID:
                return "/Volumes/SHARE/MacSystem/Home/Users/djzhang/DevIntellijIdea/apk-decompile/Projects/qq2013_4.5.2.1605_android/App/src/main/java";
            case BBM_FOR_ANDROID:
                return "/Volumes/SHARE/MacSystem/Home/Users/djzhang/DevIntellijIdea/apk-decompile/Projects/bbm-for-android/App/src/main/java";
        }
        return null;
    }
}
