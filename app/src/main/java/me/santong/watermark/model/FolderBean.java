package me.santong.watermark.model;

import java.util.List;

import me.santong.watermark.framework.Entity;

/**
 * Created by santong.
 * At 16/5/20 22:44
 */
public class FolderBean extends Entity {
    private String coverPath;   // 封面路径
    private String name;
    private String dir;
    private int size;
    private List<String> pathList;

    public FolderBean() {
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<String> getPathList() {
        return pathList;
    }

    public void setPathList(List<String> pathList) {
        this.pathList = pathList;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
        int lastIndexOf = this.dir.lastIndexOf("/");
        this.name = this.dir.substring(lastIndexOf + 1);
    }
}
