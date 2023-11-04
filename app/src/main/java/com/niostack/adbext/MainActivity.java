package com.niostack.adbext;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.TimeZone;

/**
 * need first:
 * adb shell pm grant com.niostack.adbext android.permission.CHANGE_CONFIGURATION
 * support:
 * adb shell am start -n com.niostack.adbext/.MainActivity --es timezone Europe/London
 * adb shell am start -n com.niostack.adbext/.MainActivity --es timezone Asia/Shanghai
 * adb shell am start -n com.niostack.adbext/.MainActivity --es language en
 * adb shell am start -n com.niostack.adbext/.MainActivity --es language zh
 */
public class MainActivity extends Activity {
    private final int REQUEST_PHONE_STATE_PERMISSION = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 检查是否已经获得了READ_PHONE_STATE权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // 如果没有获得权限，请求权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_PHONE_STATE_PERMISSION);
        } else {
            // 已经获得权限，可以执行需要该权限的操作
            // 在此处调用获取MCC和MNC的代码
            System.out.println("已经获得权限，可以执行需要该权限的操作");
            setMCCMNC();
        }
        setView();
    }

    private void setMCCMNC() {
        String mccmnc = getMccMnc(this, 0);
        TextView MCCMNC = findViewById(R.id.MCCMNC);
        MCCMNC.setText(mccmnc);
    }

    // 处理权限请求的回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PHONE_STATE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户授予了READ_PHONE_STATE权限，可以执行需要该权限的操作
                // 在此处调用获取MCC和MNC的代码
                System.out.println("用户授予了READ_PHONE_STATE权限");
                setMCCMNC();
            } else {
                // 用户拒绝了权限请求，可以在这里处理拒绝的情况
                System.out.println("用户拒绝了权限请求");
            }
        }
    }

    // 获取MCC和MNC
    public String getMccMnc(Context context, int subscriptionId) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (telephonyManager != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                SubscriptionManager subscriptionManager = SubscriptionManager.from(context);
                SubscriptionInfo subscriptionInfo = subscriptionManager.getActiveSubscriptionInfo(subscriptionId);

                if (subscriptionInfo != null) {
                    String mccMnc = subscriptionInfo.getMccString() + subscriptionInfo.getMncString();
                    return mccMnc;
                }
            } else {
                String mccMnc = telephonyManager.getSimOperator();
                return mccMnc;
            }
        }

        return null;
    }
    private void setView() {
        TextView et_current_timezone = findViewById(R.id.et_current_timezone);
        TextView et_current_language = findViewById(R.id.et_current_language);
        WebView tv_use_instructions_content = findViewById(R.id.tv_use_instructions_content);
        //获取当前手机系统语言
        String language = getResources().getConfiguration().locale.getLanguage();
        et_current_language.setText(language);
        //获取当前手机系统时区设置
        String timeZone = TimeZone.getDefault().getID();
        et_current_timezone.setText(timeZone);

        //使用说明

        String use_instructions_content = getString(R.string.use_instructions_content);
        tv_use_instructions_content.loadData(use_instructions_content, "text/html; charset=UTF-8", null);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //设置系统时区

        String timezone = intent.getStringExtra("timezone");
        if (timezone != null) {
            updateTimezone(timezone);
        }
        //设置系统语言
        String language = intent.getStringExtra("language");
        if (language != null) {
            updateLocale(language);
        }
        setView();
    }

    private void updateTimezone(String timezone) {
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarm.setTimeZone(timezone);
        Toast.makeText(this, "timezone updated to: " + timezone, Toast.LENGTH_SHORT).show();

    }

    private void updateLocale(String language) {
        try {
            Locale locale = new Locale(language);

            Class amnClass = Class.forName("android.app.ActivityManagerNative");
            Object amn = null;
            Configuration config = null;

            // amn = ActivityManagerNative.getDefault();
            Method methodGetDefault = amnClass.getMethod("getDefault");
            methodGetDefault.setAccessible(true);
            amn = methodGetDefault.invoke(amnClass);

            // config = amn.getConfiguration();
            Method methodGetConfiguration = amnClass.getMethod("getConfiguration");
            methodGetConfiguration.setAccessible(true);
            config = (Configuration) methodGetConfiguration.invoke(amn);

            // config.userSetLocale = true;
            Class configClass = config.getClass();
            Field f = configClass.getField("userSetLocale");
            f.setBoolean(config, true);

            // set the locale to the new value
            config.locale = locale;

            // amn.updateConfiguration(config);
            Method methodUpdateConfiguration = amnClass.getMethod("updateConfiguration", Configuration.class);
            methodUpdateConfiguration.setAccessible(true);
            methodUpdateConfiguration.invoke(amn, config);
            Toast.makeText(this, "language updated to: " + language, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "language updated failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
