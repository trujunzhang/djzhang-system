package com.system.utils;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: djzhang
 * Date: 8/22/13
 * Time: 7:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class RenameUtils {


    private final File fold;

    public RenameUtils(String fold_path) {
        this.fold = new File(fold_path);
    }


    public void printFoldList(String include, String exinclude, boolean hasFilter) {
        File[] list = this.fold.listFiles();
        for (File file : list) {
            String name = file.getName();
            if (hasFilter && name.contains(exinclude)) {
                continue;
            }
            if (name.contains(include) || !hasFilter) {
                System.out.print("\"" + name + "\",");
            }
        }
    }

    public void rename(String[] rename_template) {
        int len = rename_template.length;
        if (len % 2 == 0) {
            len = len / 2;
            for (int i = 0; i < len; i++) {
                String src = rename_template[i];
                String dest = rename_template[i + len];
                File srcFile = new File(fold, src);
                File destFile = new File(fold, dest);
                boolean result = srcFile.renameTo(destFile);
                System.out.println("index=" + i + " result=" + result);
            }
        }
    }
}
