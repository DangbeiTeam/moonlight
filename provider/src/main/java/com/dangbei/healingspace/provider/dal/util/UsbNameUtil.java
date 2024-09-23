package com.dangbei.healingspace.provider.dal.util;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static android.content.Context.USB_SERVICE;

import com.dangbei.xlog.XLog;

/**
 * Created by $xuzhili on 2018/5/15.
 * dangbei network
 */

public class UsbNameUtil {

    private static final String TAG = UsbNameUtil.class.getSimpleName();

    /**
     * 查询当前usb的数量
     *
     * @return
     */
    public static int getDetectedUsbDevicesNum(Context context) {
        UsbManager service = (UsbManager) context.getSystemService(USB_SERVICE);
        if (service != null) {
            return service.getDeviceList().size();
        }
        return 0;
    }


    public static Set<String> getUsbName(Context context) {
        Set<String> usbName = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StorageManager mStorageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            Class<?> volumeInfoClazz = null;
            Method getDescriptionComparator = null;
            Method getBestVolumeDescription = null;
            Method getVolumes = null;
            Method isMountedReadable = null;
            Method getType = null;
            Method getPath = null;
            List<?> volumes = null;
            try {
                volumeInfoClazz = Class.forName("android.os.storage.VolumeInfo");
                getDescriptionComparator = volumeInfoClazz.getMethod("getDescriptionComparator");
                getBestVolumeDescription = StorageManager.class.getMethod("getBestVolumeDescription", volumeInfoClazz);
                getVolumes = StorageManager.class.getMethod("getVolumes");
                isMountedReadable = volumeInfoClazz.getMethod("isMountedReadable");
                getType = volumeInfoClazz.getMethod("getType");
                getPath = volumeInfoClazz.getMethod("getPath");
                volumes = (List<?>) getVolumes.invoke(mStorageManager);
                usbName = new HashSet<>();
                for (Object vol : volumes) {
                    if (vol != null && (boolean) isMountedReadable.invoke(vol) && (int) getType.invoke(vol) == 0) {
                        File path2 = (File) getPath.invoke(vol);
                        String p1 = (String) getBestVolumeDescription.invoke(mStorageManager, vol);
                        String p2 = path2.getPath();
                        usbName.add(p2);
                        XLog.d(TAG, "-----------path2-----------------" + p1);                             //打印U盘卷标名称
                        XLog.d(TAG, "-----------path2 @@@@@-----------------" + p2);         //打印U盘路径
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            HashSet<String> allSDPath = getAllSDPath();
            if (allSDPath != null) {
                Iterator<String> iterator = allSDPath.iterator();
                while (iterator.hasNext()) {
                    String next = iterator.next();
                    String sdCardPath = null;
                    File directory = Environment.getExternalStorageDirectory();
                    if (directory != null) {
                        sdCardPath = directory.getPath();
                    }
                    XLog.d(TAG, "-----------path3:----------------" + next);
                    if (next != null && sdCardPath != null
                            && (next.contains(sdCardPath) || sdCardPath.contains(next))) {
                        iterator.remove();
                    }
                }
                usbName = new HashSet<>(allSDPath);
            }
        }
        return usbName;
    }

    /**
     * 获取本地外挂设备，包括sd卡。忆典u盘sd:/mnt/internal_sd---disk info:/mnt/internal_sd;/mnt/usb_storage/USB_DISK1/udisk0;
     *
     * @return
     */
    public static HashSet<String> getAllSDPath() {
        HashSet<String> paths = new HashSet<>();
        // 得到路径
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                XLog.d("SdcardUtils", "line----" + line);
                if (line.contains("secure")
                        || line.contains("asec") || line.contains("legacy")
                        || line.contains("shell") || line.contains("private")
                        || line.contains("obb") || line.contains("media")
                        || line.contains("smb") || line.contains("Boot0loader")
                        || line.contains("Reserve")
                        || line.contains("runtime")
                        || line.contains("bootloader")
                        || line.contains("storage/emulated")) {
                    continue;
                }
                if (line.contains("fat")) {
                    String columns[] = line.split(" ");
                    if (columns.length > 1) {
                        String column = columns[1];
//                        Log.d("findUsb", "findUsb--" + column);
                        if (column != null && column.contains("/")) {
                            paths.add(column);
                        }
                    }
                }
//                /dev/fuse /storage/udisk0 fuse rw,nosuid,nodev,noexec,relatime,user_id=1023,group_id=1023,default_permissions,allow_other 0 0

                else if ((line.contains("fuse") && (line.contains("/storage/udisk") || line.contains("/storage/usbotg")))
                        || line.contains("/dev/block/vold")) {
                    String columns[] = line.split(" ");
                    if (columns.length > 1) {
                        String column = columns[1];
//                        Log.d("SdcardUtils", "findUsb--" + column);
                        paths.add(column);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return paths;
    }

}
