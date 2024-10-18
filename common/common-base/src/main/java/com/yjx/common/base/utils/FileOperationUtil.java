package com.yjx.common.base.utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author yjxbz
 */
public class FileOperationUtil {

    /**
     * 判断文件夹是否存在 不存在 就创建
     * @param filePath
     */
    public static String createDir(String filePath){
        try {
            Path dirPath = Paths.get(filePath);
            if(!dirPath.toFile().exists()){
                dirPath.toFile().mkdirs();
            }
            return dirPath.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除文件夹及子目录
     * @param dir
     * @throws Exception
     */
    public static void clearDir(String dir) throws Exception {
        File fileFolder = new File(dir);
        // 判断是否为文件夹
        if(fileFolder.isDirectory()){
            // 如果是文件夹 获取文件夹下的文件列表
            File[] fileArray = fileFolder.listFiles();
            if(fileArray != null){
                for (File file:fileArray){
                    // 如果是文件那么就删除文件
                    if(file.isFile()){
                        boolean isDeleted = file.delete();
                        if(!isDeleted){
                            System.out.printf("文件删除失败，当前文件 -> %s%n",file.getAbsolutePath());
                            throw new Exception(file.getAbsolutePath() + "文件删除失败");
                        }
                    }
                    // 如果不是文件是文件夹
                    else if(file.isDirectory()){
                        // 递归删除文件夹
                        createDir(file.getAbsolutePath());
                        // 删除空文件夹
                        boolean isDelete = file.delete();
                        if(!isDelete){
                            System.out.printf("文件删除失败，当前文件 -> %s%n",file.getAbsolutePath());
                            throw new Exception(file.getAbsolutePath() + "文件删除失败");
                        }
                    }
                }
            }
        }
        else {
            System.out.println("当前文件不是文件夹，请直接删除");
            throw new Exception("当前文件不是文件夹");
        }
    }
}
