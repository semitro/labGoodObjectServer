package vt.smt.Commands;

import com.sun.istack.internal.Nullable;

import java.awt.image.BufferedImage;

/**
 * Зафиксировать изменения в базе данных
 * Фотографируем посмевшего сделать коммит нашей базы данных.
 */
public class CommitChanges implements ServerCommand {
    private BufferedImage commitator;
    public CommitChanges(@Nullable BufferedImage commitator){
        this.commitator =  commitator;
    }
    public BufferedImage getCommitator(){
        return commitator;
    }
}
