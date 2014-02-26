package com.vodismetka.workers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

public class ImageFactory {

	public static final String TAG = "ImageFactory";
	public static final String IMAGES_PATH = TessFactory.DATA_PATH + "images/";
	
	private Bitmap photo;
	private String _path;
	
	public ImageFactory(Bitmap img, String path) {
		_path = path;
		saveImage(img, path);
		photo = prepareImage();
		saveImage(photo, path);
	}
	
	public Bitmap getImg(){
		return photo;
	}
	
	private Bitmap prepareImage(){
		
		Bitmap bitmap = photo; 
		
		try {
			ExifInterface exif = new ExifInterface(_path);
			int exifOrientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);

			Log.v(TAG, "Orient: " + exifOrientation);

			int rotate = 0;

			switch (exifOrientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				rotate = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				rotate = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				rotate = 270;
				break;
			}

			Log.v(TAG, "Rotation: " + rotate);

			if (rotate != 0) {

				// Getting width & height of the given image.
				int w = bitmap.getWidth();
				int h = bitmap.getHeight();

				// Setting pre rotate
				Matrix mtx = new Matrix();
				mtx.preRotate(rotate);

				// Rotating Bitmap
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, false);
			}

			// Convert to ARGB_8888, required by tess
			bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

		} catch (IOException e) {
			Log.e(TAG, "Couldn't correct orientation: " + e.toString());
		}
		return bitmap;
	}
	
	public void saveImage(Bitmap finalBitmap, String fname) {

		String root = TessFactory.DATA_PATH;
		File myDir = new File(root + "saved_images/");
		myDir.mkdirs();
		File file = new File(myDir, fname);
		if (file.exists())
			file.delete();
		try {
			FileOutputStream out = new FileOutputStream(file);
			finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Bitmap loadImage(String photoPath){
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Bitmap bitmap = null;
		try{
			bitmap = BitmapFactory.decodeFile(photoPath, options);
		} catch (Exception e){
			Log.i(TAG, "Could not load bitmap from saved image file");
		}
		return bitmap;
	}

}
