package com.system.tools;

import com.system.utils.RenameUtils;

/**
 * Created with IntelliJ IDEA.
 * User: djzhang
 * Date: 8/22/13
 * Time: 7:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class RenameBatch {

    private String fold_Path = "/Volumes/SHARE/Downloads/Project 企业移动客户端/08-22/新奥特高亮 copy";
    //private String fold_Path = "/Volumes/SHARE/MacSystem/Home/Users/djzhang/DevIntellijIdea/gdsjprojects/enterpriseTemplate/mobile/android/enterIdAndroid/trunk/res/drawable-hdpi";

    String[] rename_Template = {
            "11.png", "21.png", "31.png", "41.png", "51.png",
            "widget_bar_plaza_over.png",
            "widget_bar_conversation_over.png",
            "widget_bar_contracts_over.png",
            "widget_bar_buddy_list_over.png",
            "widget_bar_personal_info_over.png",
    };

    String include = "_nor";
    String exinclude = "over";

    boolean hasFilter = false;

    private RenameUtils renameUtils;

    public RenameBatch() {
        renameUtils = new RenameUtils(fold_Path);
        //renameUtils.printFoldList(include, exinclude, hasFilter);
        renameUtils.rename(rename_Template);
    }

    public static void main(String[] args) {
        RenameBatch renameBatch = new RenameBatch();

    }

}
