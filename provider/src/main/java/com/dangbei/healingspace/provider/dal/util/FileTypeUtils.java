package com.dangbei.healingspace.provider.dal.util;


import android.annotation.SuppressLint;

import java.io.File;

public class FileTypeUtils {

    private static FileTypeUtils instance = null;
    private final String[][] MIME_MapTable = {
            //{后缀名，MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/x-msdownload"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"},
            {"", "*/*"}};

    public static FileTypeUtils getInstance() {

        synchronized (FileTypeUtils.class) {
            if (instance == null) {
                instance = new FileTypeUtils();
            }

            return instance;
        }
    }

    /**
     * 获取文件类型
     */
    @SuppressLint("DefaultLocale")
    public String getMIMEType(String filePath) {
        String type = "*/*";
        String fName = filePath;

        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }

        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "") {
            return type;
        }

        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (end.equals(MIME_MapTable[i][0])) {
                type = MIME_MapTable[i][1];
            }
        }
        return type;
    }

    public String getNoneTypeMime(String fileName) {

        if (fileName == null)
            return null;
        String ext = this.getExtensionName(fileName);
        if (ext != null)
            for (String[] s : MIME_MapTable) {
                if (s[0].endsWith(ext)) {
                    return s[1];
                }
            }
        return null;
    }

    public String getExtensionName(String filename) {

        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return null;
    }

    public String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    public int getFileType(File file) {

        String name = file.getName();
        // 文件名称
//		String fileName = this.getFileNameNoEx(name);
        // 文件后缀
        String fileExtension = this.getExtensionName(name);

        if (isMusic(fileExtension)) {
            return FileType.mp3;
        } else if (isPhoto(fileExtension)) {
            return FileType.img;
        } else if (isVideo(fileExtension)) {
            return FileType.video;
        } else if (isAndroidApp(fileExtension)) {
            return FileType.apk;
        } else if (isTxt(fileExtension)) {
            return FileType.txt;
        } else if (isOfficeExcel(fileExtension)) {
            return FileType.wps_excel;
        } else if (isOfficePdf(fileExtension)) {
            return FileType.wps_pdf;
        } else if (isOfficePpt(fileExtension)) {
            return FileType.wps_ppt;
        } else if (isOfficeWord(fileExtension)) {
            return FileType.wps_word;
        }
        return FileType.none;

    }

    public boolean isMusic(String extension) {
        if (extension == null)
            return false;

        final String ext = extension.toLowerCase();
        if (ext.equals("mp3") || ext.equals("m4a") || ext.equals("wav")
                || ext.equals("amr") || ext.equals("awb") || ext.equals("aac")
                || ext.equals("flac") || ext.equals("mid")
                || ext.equals("midi") || ext.equals("xmf")
                || ext.equals("rtttl") || ext.equals("rtx")
                || ext.equals("ota") || ext.equals("wma") || ext.equals("ra")
                || ext.equals("mka") || ext.equals("m3u") || ext.equals("pls")) {
            return true;
        }
        return false;
    }
    private static final String[] extension=new String[]{"jpg","jpeg","gif","png","bmp","wbmp",
            "tiff","tif","tga","exif","fpx","svg","cdr","pcd","dxf","ufo"};
    //pcx, psd

    public static boolean isPhoto(String extension) {
        if (extension == null)
            return false;

        final String ext = extension.toLowerCase();
        if (ext.equals("jpg") || ext.equals("jpeg") || ext.equals("gif")
                || ext.equals("png") || ext.equals("bmp") || ext.equals("wbmp") ||
                ext.equals("tif") || ext.equals("tiff") ||
                ext.equals("tga") || ext.equals("exif") || ext.equals("fpx") ||
                ext.equals("svg") ||  ext.equals("cdr") ||
                ext.equals("pcd") ||ext.equals("dxf") ||ext.equals("ufo") ) {
            return true;
        }
        return false;
    }

    public boolean isVideo(String extension) {
        if (extension == null)
            return false;

        final String ext = extension.toLowerCase();
        if (ext.equals("mpeg") || ext.equals("mp4") || ext.equals("mov")
                || ext.equals("m4v") || ext.equals("3gp") || ext.equals("3gpp")
                || ext.equals("3g2") || ext.equals("3gpp2")
                || ext.equals("avi") || ext.equals("divx") || ext.equals("wmv")
                || ext.equals("asf") || ext.equals("flv") || ext.equals("mkv")
                || ext.equals("mpg") || ext.equals("rmvb") || ext.equals("rm")
                || ext.equals("vob") || ext.equals("f4v") || ext.equals("swf")) {
            return true;
        }
        return false;
    }

    public boolean isAndroidApp(String extension) {
        if (extension == null)
            return false;

        final String ext = extension.toLowerCase();
        if (ext.equals("apk")) {
            return true;
        }
        return false;
    }

    public boolean isTxt(String extension) {
        if (extension == null)
            return false;

        final String ext = extension.toLowerCase();
        if (ext.equals("txt") || ext.equals("c") || ext.equals("h")
                || ext.equals("cpp") || ext.equals("conf") || ext.equals("htm")
                || ext.equals("html") || ext.equals("log") || ext.equals("sh")
                || ext.equals("rc") || ext.equals("xml") || ext.equals("prop")
                || ext.equals("java")) {
            return true;
        }
        return false;
    }

    public boolean isOfficeExcel(String extension) {

        if (extension == null)
            return false;

        final String ext = extension.toLowerCase();
        if (ext.equals("xls") || ext.equals("xlsx")) {
            return true;
        }
        return false;
    }

    public boolean isOfficePdf(String extension) {

        if (extension == null)
            return false;

        final String ext = extension.toLowerCase();
        if (ext.equals("pdf")) {
            return true;
        }
        return false;
    }

    public boolean isOfficePpt(String extension) {

        if (extension == null)
            return false;

        final String ext = extension.toLowerCase();
        if (ext.equals("ppt") || ext.equals("pptx") || ext.equals("pps")) {
            return true;
        }
        return false;
    }

    public boolean isOfficeWord(String extension) {

        if (extension == null)
            return false;

        final String ext = extension.toLowerCase();
        if (ext.equals("doc") || ext.equals("docx")) {
            return true;
        }
        return false;
    }

    public boolean isOffice(String name) {

        // 文件名称
//		String fileName = this.getFileNameNoEx(name);
        // 文件后缀
        String fileExtension = this.getExtensionName(name);

        if (fileExtension == null)
            return false; // wps doc docx xls xlsx pdf ppt pps pptx

        final String ext = fileExtension.toLowerCase();
        if (ext.equals("wps") || ext.equals("doc") || ext.equals("docx")
                || ext.equals("xls") || ext.equals("xlsx") || ext.equals("pdf")
                || ext.equals("ppt") || ext.equals("pps") || ext.equals("pptx")) {
            return true;
        }
        return false;
    }
}
