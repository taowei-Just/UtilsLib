package com.tao.utilslib.storage;

import android.os.Environment;
import android.os.StatFs;

import java.util.Locale;

public class StorageSpaceUtil {
    
    
   public  static  String  queryStorage(){
       StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
       //存储块总数量
       long blockCount = statFs.getBlockCount();
       //块大小
       long blockSize = statFs.getBlockSize();
       //可用块数量
       long availableCount = statFs.getAvailableBlocks();
       //剩余块数量，注：这个包含保留块（including reserved blocks）即应用无法使用的空间 
       long freeBlocks = statFs.getFreeBlocks();
       //这两个方法是直接输出总内存和可用空间，也有getFreeBytes
       //API level 18（JELLY_BEAN_MR2）引入
       long totalSize = statFs.getTotalBytes();
       long availableSize = statFs.getAvailableBytes();

       StringBuilder builder = new StringBuilder();
       builder.append("总存储大小：");
       builder.append(getUnit(blockSize*blockCount)); 
       builder.append("可用存储大小：");
       builder.append(getUnit(blockSize*availableCount));

       return builder.toString();

   }
    private  static  String[] units = {"B", "KB", "MB", "GB", "TB"};
    private static String getUnit(float size) {
        int index = 0;
        while (size > 1024 && index < 4) {
            size = size / 1024;
            index++;
        }
        return String.format(Locale.getDefault(), " %.2f %s", size, units[index]);
    }
 
    
}
