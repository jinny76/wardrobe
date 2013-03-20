package jbolt.android.utils;

import jbolt.android.base.AppContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
        File sdfile = getRootDir();
        File destDir = new File(sdfile.getAbsolutePath() + file);
        return !destDir.exists();
    }

    public static String getSdCardPath() {
        File sdfile = getRootDir();
        return sdfile.getAbsolutePath();
    }

    public static void writeToSDCardFile(String file, byte[] out, boolean append) {
        String sDStateString = android.os.Environment.getExternalStorageState();
        if (sDStateString.equals(android.os.Environment.MEDIA_MOUNTED)) {
            try {
                Log.i(SDCardUtilities.class.getName(), ">>>>>>>>>>>>>Write file to " + file);
                File sdfile = getRootDir();
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
        File SDFile = getRootDir();
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
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(infoFile);
                fis.read(result);
            } catch (IOException e) {
                MessageHandler.showWarningMessage(AppContext.context, e);
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        Log.i(SDCardUtilities.class.getName(), e.getMessage());
                    }
                }
            }
        }
        return result;
    }

    public static void delete(String file) {
        File SDFile = getRootDir();
        File infoFile = new File(SDFile.getAbsolutePath() + file);
        if (infoFile.exists()) {
            if (infoFile.isDirectory()) {
                deleteAll(infoFile);
            } else {
                infoFile.delete();
            }
        }
    }

    /**
     * visit all a director and delete them in a list,write deleted files' name to list
     *
     * @param dir directory
     * @return deleted file name list
     */
    private static List deleteAll(File dir) {
        List allFiles = new ArrayList();
        File[] dirs = dir.listFiles();
        if (dirs != null) {
            List dirsList = Arrays.asList(dirs);
            if (dirsList == null) {
                try {
                    dir.delete();
                } catch (Exception e) {
                    Log.e(SDCardUtilities.class.getName(), e.getMessage());
                }
            } else {
                allFiles.addAll(dirsList);
                for (Iterator it = dirsList.iterator(); it.hasNext(); ) {
                    File _tempRoot = (java.io.File) it.next();
                    allFiles.addAll(deleteAll(_tempRoot));
                }
            }
        }
        boolean deleted = dir.delete();
        if (!deleted) {
            Log.e(SDCardUtilities.class.getName(), "Not delete " + dir.getAbsolutePath());
        }
        return allFiles;
    }

    public static File getRootDir() {
        return android.os.Environment.getExternalStorageDirectory();
    }
}
