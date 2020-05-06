package com.orz.im.globallib.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.blankj.utilcode.util.LogUtils;
import com.orz.im.globallib.R;

import java.io.IOException;

/**
 * Created by Orz on .
 * 通知管理类
 */
public class NotificationManagerUtils {
    private static MediaPlayer mPlayer;
    private static final String CHANNEL_ID = "im_edu_default";
    private static final String CHANNEL_NAME = "IM_Default_Channel";
    public static void doNotify(Context context, String title, String content, int iconID) {
          NotificationManager manager = (NotificationManager)context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
          if (manager == null) {
              LogUtils.eTag("NotificationManagerUtils", "get NotificationManager failed");
          } else {
              NotificationCompat.Builder builder;
              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                  builder = new NotificationCompat.Builder(context, CHANNEL_ID);
                  NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                  manager.createNotificationChannel(channel);
              } else {
                  builder = new NotificationCompat.Builder(context);
              }
              Log.e("11111",title+"   "+ content+"   "+ iconID );

              builder.setTicker("收到一条新消息");
              builder.setContentTitle(title);
              builder.setContentText(content);
              builder.setSmallIcon(iconID);
              builder.setAutoCancel(true);
              builder.setDefaults(-1);//6
              builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), iconID));
              ring(context);
              Intent launch = context.getApplicationContext().getPackageManager().getLaunchIntentForPackage(context.getPackageName());
              builder.setContentIntent(PendingIntent.getActivity(context, (int) SystemClock.uptimeMillis(), launch, PendingIntent.FLAG_UPDATE_CURRENT));
              manager.notify("NotificationManagerUtils", 520, builder.build());
          }
      }

    /**
     * 响铃
     * @param context
     */

    private static void ring(Context context) {
        try {
            if (mPlayer == null) {
                mPlayer = new MediaPlayer();
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);//获取系统设置的通知铃声
                if (uri != null){
                    mPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
                    mPlayer.setDataSource(context, uri);
                    mPlayer.prepare();
                }else {
                    Uri uriSystem = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);//获取系统设置的通知铃声
                    mPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
                    mPlayer.setDataSource(context, uriSystem);
                    mPlayer.prepare();
                }
            }
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION) != 0) {
                mPlayer.start();
            }
            mPlayer.setOnCompletionListener(mp -> {
                mPlayer.release();
                mPlayer = null;
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean isNotificationEnabled(Context context) {
        return NotificationManagerCompat.from(context.getApplicationContext()).areNotificationsEnabled();
    }

    public static void openPush(AppCompatActivity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, activity.getPackageName());
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, activity.getApplicationInfo().uid);
            activity.startActivity(intent);
        } else {
            toPermissionSetting(activity);
        }
    }


    /**
     * 跳转到权限设置
     *
     * @param activity
     */
    public static void toPermissionSetting(AppCompatActivity activity) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            toSystemConfig(activity);
        } else {
            try {
                toApplicationInfo(activity);
            } catch (Exception e) {
                e.printStackTrace();
                toSystemConfig(activity);
            }
        }
    }

    /**
     * 应用信息界面
     *
     * @param activity
     */
    public static void toApplicationInfo(AppCompatActivity activity) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        activity.startActivity(localIntent);
    }

    /**
     * 系统设置界面
     *
     * @param activity
     */
    public static void toSystemConfig(AppCompatActivity activity) {
        try {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
