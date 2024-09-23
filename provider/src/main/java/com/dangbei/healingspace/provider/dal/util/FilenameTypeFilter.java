package com.dangbei.healingspace.provider.dal.util;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by huke on 2019/10/23.
 *
 * @Description:
 */
public class FilenameTypeFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
//        //过滤当前app下的文件；
//        if (pathname.isDirectory() && pathname.getPath().contains("com.dangbei.lerad.screensaver")) {
//            return false;
//        }
        //过滤以.开头的文件夹或者文件；
        if (pathname.getName().startsWith(".")) {
            return false;
        }
        return true;
    }
}
