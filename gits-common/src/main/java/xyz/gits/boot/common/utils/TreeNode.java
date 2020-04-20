package xyz.gits.boot.common.utils;

import lombok.Data;

import java.util.List;

/**
 * 树形节点
 *
 * @author songyinyin
 * @date 2020/1/19 16:23
 */
@Data
public class TreeNode<T> {

    protected Object id;

    protected Object parentId;

    List<T> children;

    public void add(T node) {
        children.add(node);
    }
}
