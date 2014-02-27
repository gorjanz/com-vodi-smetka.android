package com.vodismetka.workers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.vodismetka.exceptions.FolderNotCreatedException;
import com.vodismetka.exceptions.TesseractNotInitializedException;

public class TessFactory {

	public static final String lang = "fs24B";
	public static final String DATA_PATH = Environment
			.getExternalStorageDirectory().toString() + "/VodiSmetka/";

	public static final String TAG = "TessFactory";

	private Context context;
	private TessBaseAPI tessBaseApi;

	public TessFactory(Context context) {
		this.context = context;
	}

	public TessBaseAPI getTess() throws TesseractNotInitializedException,
			FolderNotCreatedException {
		if (tessBaseApi == null){
			init();
			tessBaseApi = new TessBaseAPI();
		}
		if(tessBaseApi == null){
			Log.i(TAG, "TessBaseAPI still not initialized, just successfully copied");
		} else {
			Log.i(TAG, "TessBaseAPI initialized...");
		}
		return tessBaseApi;
	}

	private void init() throws TesseractNotInitializedException,
			FolderNotCreatedException {

		// paths to create folders for tessdata training file
		String[] paths = new String[] { DATA_PATH, DATA_PATH + "tessdata/" };

		for (String path : paths) {
			File dir = new File(path);
			if (!dir.exists()) {
				if (!dir.mkdirs()) {
					Log.v(TAG, "ERROR: Creation of directory " + path
							+ " on sdcard failed");
					throw new FolderNotCreatedException(
							"Could not create folders for tesseract training data...s");
				} else {
					Log.v(TAG, "Created directory " + path + " on sdcard");
				}
			}

		}

		// open lang.traineddata file within the assets folder
		// if it is not already there copy it to the sdcard
		if (!(new File(DATA_PATH + "tessdata/" + lang + ".traineddata"))
				.exists()) {
			try {

				AssetManager assetManager = context.getAssets();
				InputStream in = assetManager.open("tessdata/" + lang
						+ ".traineddata");
				// GZIPInputStream gin = new GZIPInputStream(in);
				OutputStream out = new FileOutputStream(DATA_PATH + "tessdata/"
						+ lang + ".traineddata");

				// Transfer bytes from in to out
				byte[] buf = new byte[1024];
				int len;
				// while ((lenf = gin.read(buff)) > 0) {
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				// gin.close();
				out.close();

				Log.v(TAG, "Copied " + lang + " traineddata");
			} catch (IOException e) {
				Log.e(TAG, "Was unable to copy " + lang + " traineddata "+ e.toString());
				throw new TesseractNotInitializedException("Could not copy language files... Unable to initialize Tesseract library");
			}
		}

	}
}
