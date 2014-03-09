package com.vodismetka.workers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

import com.vodismetka.exceptions.UnrecognizableDateException;
import com.vodismetka.exceptions.UnrecognizablePriceException;

public abstract class TextFactory {

	public static final String TAG = "TextFactory";

	public static int findVoGotovo(String text) throws UnrecognizablePriceException {
		Pattern voGotovo = Pattern.compile("(vo gotovo )(\\d)*,0*",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = voGotovo.matcher(text);
		int price = 0;
		if(matcher.find()) {
			Log.i(TAG,"Start index: " + matcher.start());
			Log.i(TAG,"End index: " + matcher.end() + " ");
			String matched = matcher.group();
			Log.i(TAG,matched);
			String[] parts = matched.split(",(\\d)*");
			Log.i(TAG,parts[0]);
			String[] parts2 = parts[0].split(" ");
			// int comma = parts[1].indexOf(",");
			// System.out.println("index" + comma);
			try{
				price = Integer.parseInt(parts2[2]);
			} catch(Exception e){
				throw new UnrecognizablePriceException(matched + " " + parts2[2]);
			}
			Log.i(TAG, Integer.toString(price));
		}
		return price;
	}

	public static int findVkupenPromet(String text) throws UnrecognizablePriceException {
		Pattern vkupenPromet = Pattern
				.compile("(\\w{0,1})(vkupen promet )(\\d)*,0*",
						Pattern.CASE_INSENSITIVE);
		Matcher matcher = vkupenPromet.matcher(text);
		int price = 0;
		if (matcher.find()) {
			Log.i(TAG,"Start index: " + matcher.start());
			Log.i(TAG,"End index: " + matcher.end() + " ");
			String matched = matcher.group();
			Log.i(TAG,matched);
			String[] parts = matched.split(",(\\d)*");
			Log.i(TAG,parts[0]);
			String[] parts2 = parts[0].split(" ");
			// int comma = parts[1].indexOf(",");
			// System.out.println("index" + comma);
			try{
				price = Integer.parseInt(parts2[2]);
			} catch(Exception e){
				throw new UnrecognizablePriceException(matched + " " + parts2[2]);
			}
			Log.i(TAG, Integer.toString(price));
		}
		return price;
	}
	
	public static String findDatum(String text) throws UnrecognizableDateException{
		Pattern datum = Pattern.compile("(.)(datum )(\\d){2,2}(.)(\\d){2,2}(.)(\\d){4,4}", Pattern.CASE_INSENSITIVE);
		Matcher matcher = datum.matcher(text);
		String date = "";
		if(matcher.find()){
			Log.i(TAG,"Start index: " + matcher.start());
			Log.i(TAG,"End index: " + matcher.end() + " ");
		    String matched = matcher.group();
		    Log.i(TAG,matched);
		} else {
			throw new UnrecognizableDateException("Date could not be recognized");
		}
		return date;
	}

	public static String fixString(String initialText) {
		String[] words = initialText.split(" ");

		Pattern[] vkupen = new Pattern[6];
		vkupen[0] = Pattern.compile("\\w{0,1}vkupen\\w{0,1}",
				Pattern.CASE_INSENSITIVE);
		vkupen[1] = Pattern.compile("\\w{0,2}kupen\\w{0,1}",
				Pattern.CASE_INSENSITIVE);
		vkupen[2] = Pattern.compile("\\w{0,1}vk\\w{0,1}pen\\w{0,1}",
				Pattern.CASE_INSENSITIVE);
		vkupen[3] = Pattern.compile("\\w{0,1}vku\\w{0,1}en\\w{0,1}",
				Pattern.CASE_INSENSITIVE);
		vkupen[4] = Pattern.compile("\\w{0,1}vkup\\w{1}n\\w{0,1}",
				Pattern.CASE_INSENSITIVE);
		vkupen[5] = Pattern.compile("\\w{0,1}vkupe\\w{0,2}",
				Pattern.CASE_INSENSITIVE);

		Pattern[] promet = new Pattern[6];
		promet[0] = Pattern.compile("\\w{0,1}promet\\w{0,1}",
				Pattern.CASE_INSENSITIVE);
		promet[1] = Pattern.compile("\\w{0,2}romet\\w{0,1}",
				Pattern.CASE_INSENSITIVE);
		promet[2] = Pattern.compile("\\w{0,1}pr\\w{0,1}met\\w{0,1}",
				Pattern.CASE_INSENSITIVE);
		promet[3] = Pattern.compile("\\w{0,1}pro\\w{0,1}et\\w{0,1}",
				Pattern.CASE_INSENSITIVE);
		promet[4] = Pattern.compile("\\w{0,1}prom\\w{1}t\\w{0,1}",
				Pattern.CASE_INSENSITIVE);
		promet[5] = Pattern.compile("\\w{0,1}prome\\w{0,2}",
				Pattern.CASE_INSENSITIVE);

		Pattern[] datum = new Pattern[7];
		datum[0] = Pattern.compile("^\\d,\\d*datum\\w{0,1}",
				Pattern.CASE_INSENSITIVE);
		datum[1] = Pattern.compile("\\w{0,1}datum\\w{0,1}",
				Pattern.CASE_INSENSITIVE);
		datum[2] = Pattern.compile("\\w{0,1}atum\\w{0,1}",
				Pattern.CASE_INSENSITIVE);
		datum[3] = Pattern.compile("d\\w{0,1}tum\\w{0,1}",
				Pattern.CASE_INSENSITIVE);
		datum[4] = Pattern.compile("\\w{0,1}da\\w{0,1}um\\w{0,1}",
				Pattern.CASE_INSENSITIVE);
		datum[5] = Pattern.compile("\\w{0,1}dat\\w{0,1}m\\w{0,1}",
				Pattern.CASE_INSENSITIVE);
		datum[6] = Pattern.compile("\\w{0,1}datu\\w{0,1}",
				Pattern.CASE_INSENSITIVE);

		for (int i = 0; i < words.length; i++) {
			String current = words[i];
			for (int j = 0; j < vkupen.length; j++) {
				Matcher vkupnoMatcher = vkupen[j].matcher(current);
				Matcher prometMatcher = promet[j].matcher(current);
				Matcher datumMatcher = datum[j].matcher(current);

				if (vkupnoMatcher.find()) {
					System.out.println("Fixed: " + words[i] + "--> " + "vkupen"
							+ " with pattern " + j);
					words[i] = "vkupen";
					break;
				}

				if (prometMatcher.find()) {
					System.out.println("Fixed: " + words[i] + "--> " + "promet"
							+ " with pattern " + j);
					words[i] = "promet";
					break;
				}

				if (datumMatcher.find()) {
					System.out.println("Fixed: " + words[i] + "--> " + "datum"
							+ " with pattern " + j);
					words[i] = "datum";
					break;
				}

			}
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < words.length; i++) {
			sb.append(words[i]);
			sb.append(" ");
		}
		return sb.toString();
	}

	
}
