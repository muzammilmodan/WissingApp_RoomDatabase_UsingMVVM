package com.wission.appUtils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GlobalMethod {

    private static ProgressDialog pDialog;

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void ShowKeyboard(Context context, EditText etEditText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etEditText, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void HideKeyboard(Context context, View view) {
        if(view!= null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showProgressDialog(Context context) {
        pDialog = new ProgressDialog(context);
        try {
            pDialog.setMessage("Please wait");
            if (pDialog.isShowing()) {
                pDialog.dismiss();
                pDialog.show();
            } else {
                pDialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void hideProgressDialog(Context context) {
        pDialog.dismiss();
    }

    public static boolean isProgressDialogShowing(Context context){
        boolean isProgressBarShow = false;

        if(pDialog!= null && pDialog.isShowing()){
            isProgressBarShow = true;
        }else{
            isProgressBarShow = false;
        }
        return isProgressBarShow;
    }

    public static String bitmapToBase64(Bitmap bmp) {
        String strBase64 = "";
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            strBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strBase64;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

        public static File bitmapToFile(Context context, Bitmap bitmap){
            File f = new File(context.getCacheDir(), "temp");
            try {
                f.createNewFile();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return f;
        }

    public static Uri getUriFromBitMap(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

//    // TODO: 28-09-2018 full image path to Bitmap by Sakib START
    public static Bitmap getImageFromLocalStorage(String path) {
        try {
            File f = new File(path);
            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, 10, 10);

            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = 2 / 2;
            final int halfWidth = 2 / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    static public String getDateFormattedDateFromDayMonthYear(int day, int month, int year) {
        String newDate = "";
        try {
            return getMonthMMM(month + "") + " " + getTwoDigits(day) + ", " + year;
        } catch (Exception e) {
            newDate = year + "/" + month + "/" + day;
        }
        return newDate;
    }

    public static String getMonthMMM(String date) {
        if (date != null) {
            if (date.equals("1") || date.equals("01")) {
                date = "Jan";
            } else if (date.equals("2") || date.equals("02")) {
                date = "Feb";
            } else if (date.equals("3") || date.equals("03")) {
                date = "Mar";
            } else if (date.equals("4") || date.equals("04")) {
                date = "Apr";
            } else if (date.equals("5") || date.equals("05")) {
                date = "May";
            } else if (date.equals("6") || date.equals("06")) {
                date = "Jun";
            } else if (date.equals("7") || date.equals("07")) {
                date = "Jul";
            } else if (date.equals("8") || date.equals("08")) {
                date = "Aug";
            } else if (date.equals("9") || date.equals("09")) {
                date = "Sep";
            } else if (date.equals("10") || date.equals("10")) {
                date = "Oct";
            } else if (date.equals("11") || date.equals("11")) {
                date = "Nov";
            } else if (date.equals("12") || date.equals("12")) {
                date = "Dec";
            }
        }
        return date;
    }

    public static String getTwoDigits(int i) {
        if (i < 10) {
            return "0" + i;
        } else {
            return i + "";
        }
    }

    static public String getDateFormattedDateTimeFromDateTime(String oldDate) {
        String newDate = "";
        try {
            String[] date = oldDate.split(" ")[0].split("-");
            String[] time = oldDate.split(" ")[1].split(":");
            return date[2] + " " + getMonthMMM(date[1]) + " " + date[0] + " " + (Integer.parseInt(time[0]) > 12 ? (Integer.parseInt(time[0]) - 12) : time[0]) + ":" + time[1] + " " + (Integer.parseInt(time[0]) > 12 ? "PM" : "AM");
        } catch (Exception e) {
            newDate = oldDate;
        }
        return newDate;
    }

    public static void showInternetAlert(Context context) {
        new AlertDialog.Builder(context).setIcon(0).setTitle(GlobalData.STR_INTERNET_ALERT_TITLE).setMessage(GlobalData.STR_INETRNET_ALERT_MESSAGE)
                .setCancelable(true).setNeutralButton("OK", null).show();
    }

    public static String formateMilliSeccond(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }
        finalTimerString = finalTimerString + minutes + ":" + secondsString;
        return finalTimerString;
    }

}
