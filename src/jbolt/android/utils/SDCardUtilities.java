package jbolt.android.utils;

import android.os.Environment;
import jbolt.android.base.AppContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <p>Title: SDCardUtilities</p>
 * <p>Description: SDCardUtilities</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IPACS e-Solutions (S) Pte Ltd</p>
 *
 * @author feng.xie
 */
public class SDCardUtilities {

    public static boolean isFileExist(String file) {
        File sdfile = SDCardUtilities.getRootDir();
        File destDir = new File(sdfile.getAbsolutePath() + file);
        return !destDir.exists();
    }

    public static String getSdCardPath() {
        File sdfile = SDCardUtilities.getRootDir();
        return sdfile.getAbsolutePath();
    }

    public static void writeToSDCardFile(String file, byte[] out, boolean append) {
        String sDStateString = android.os.Environment.getExternalStorageState();
        if (sDStateString.equals(android.os.Environment.MEDIA_MOUNTED)) {
            try {
                Log.i(SDCardUtilities.class.getName(), ">>>>>>>>>>>>>Write file to " + file);
                File sdfile = SDCardUtilities.getRootDir();
                File destDir = new File(sdfile.getAbsolutePath() + file);
                if (!destDir.exists()) {
                    File dir = destDir.getParentFile();
                    if (dir != null && !dir.exists()) {
                        dir.mkdirs();
                    }
                }
                if (!destDir.exists()) {
                    destDir.createNewFile();
                }
                FileOutputStream outputStream = new FileOutputStream(destDir, append);
                outputStream.write(out);
                outputStream.close();
                Log.i(SDCardUtilities.class.getName(), "------------Write file successfully");
            } catch (Exception e) {
                Log.e(SDCardUtilities.class.getName(), "------------Write file error");
                MessageHandler.showWarningMessage(AppContext.context, e);
            }
        } else if (sDStateString.endsWith(android.os.Environment.MEDIA_MOUNTED_READ_ONLY)) {
            MessageHandler.showWarningMessage(AppContext.context, "Hasn't permission for writing sdcard file!");
        }
    }

    /**
     * read file to byte[] with specified file path
     *
     * @param file Given file path
     * @return <code>byte[]</code> file content
     */
    public static byte[] readSDCardFile(String file) {
        File SDFile = SDCardUtilities.getRootDir();
        File infoFile = new File(SDFile.getAbsolutePath() + file);
        return readByFile(infoFile);
    }

    public static byte[] readFile(String file) {
        File infoFile = new File(file);
        return readByFile(infoFile);
    }

    public static byte[] readByFile(File infoFile) {
        byte[] result = null;
        if (infoFile.exists()) {
            result = new byte[(int) infoFile.length()];
            try {
                FileInputStream fis = new FileInputStream(infoFile);
                fis.read(result);
                fis.close();
            } catch (IOException e) {
                MessageHandler.showWarningMessage(AppContext.context, e);
            }
        }
        return result;
    }

    public static void delete(String file) {
        File SDFile = SDCardUtilities.getRootDir();
        File infoFile = new File(SDFile.getAbsolutePath() + file);
        if (infoFile.exists()) {
            infoFile.delete();
        }
    }

    public static File getRootDir() {
        File storageDirectory = Environment.getExternalStorageDirectory();
        if (storageDirectory == null) {
            storageDirectory = Environment.getRootDirectory();
        }

        return storageDirectory;
    }
}
