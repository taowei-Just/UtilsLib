package com.tao.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import com.tao.globle.Gloable;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tao.other.PictureLoaderThread;
import com.tao.utilslib.log.LogUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;

public class BitmapUtils {

	public static boolean saveBitmap2Local(String path, Bitmap bitmap) {
		
		boolean saved =false ;

		// 创建文件对象，用来存储新的图像文件
		File file = new File(path);
		// 创建文件
		try {
			if (file.exists())
				file.delete();
			else  
				file.getParentFile().mkdirs();
			file.createNewFile();
			// 定义文件输出流
			FileOutputStream fOut = new FileOutputStream(file);
			// 将bitmap存储为png格式的图片
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			saved =true ;
			fOut.flush();// 刷新文件流
			fOut.close();
		} catch (Exception e) {
			if (file.exists())
				file.delete();
			e.printStackTrace();
			LogUtil.e(path, " 保存图片异常 "+e.toString());
		}
		return  saved ;
	}

	public static void initImageLoader(Context context) {

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs().build();

		ImageLoader.getInstance().init(config);
	}

	public static boolean checkLocalImg(String url) {

		String nameForURl = PictureLoaderThread.getNameForURl(url);
		File file = new File(Gloable.PicturePath + "/" + nameForURl);
		if (file.exists()) {

			return true;
		}

		return false;

	}

	public static  Bitmap getDiskBitmap(String pathString) {
		Bitmap bitmap = null;
		try {
			File file = new File(pathString);
			if (file.exists()) {
				bitmap = BitmapFactory.decodeFile(pathString);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return bitmap;
	}
	
	
	
	public static  Bitmap runInPreviewFrame(byte[] data, Camera camera) {
		// camera.setOneShotPreviewCallback(null);
		// 处理data
		Bitmap bitmap = null;
		if (camera != null) {
			Camera.Size previewSize = camera.getParameters().getPreviewSize();// 获取尺寸,格式转换的时候要用到
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			LogUtil.e("bitmap", " 转换 图片数据  previewSize.width "
					+ previewSize.width + "   previewSize.height   "
					+ previewSize.height);

			newOpts.inJustDecodeBounds = true;
			YuvImage yuvimage = new YuvImage(data, ImageFormat.NV21,
					previewSize.width, previewSize.height, null);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			yuvimage.compressToJpeg(new Rect(0, 0, previewSize.width,
					previewSize.height), 80, baos);// 80--JPG图片的质量[0-100],100最高

			byte[] rawImage = baos.toByteArray();

			// 将rawImage转换成bitmap

			BitmapFactory.Options options = new BitmapFactory.Options();

			options.inPreferredConfig = Bitmap.Config.RGB_565;

			bitmap = BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length, options);
		}

		return bitmap;
	}

}
