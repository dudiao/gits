package xyz.gits.boot.common.core.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 树构建工具
 *
 * @author songyinyin
 * @date 2020/1/19 16:23
 */
public class TreeUtil {

    /**
     * 两层循环实现建树
     * <p>树节点需要继承 {@link TreeNode}</p>
     *
     * @param treeNodes  传入的树节点集合
     * @param root       根节点
     * @param comparator 比较器，不进行排序时可传 null
     * @return 构建完成的树
     * @see TreeNode
     */
    public static <T extends TreeNode> List<T> bulid(List<T> treeNodes, Object root, Comparator comparator) {
        List<T> trees = new ArrayList<T>();

        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getParentId())) {
                trees.add(treeNode);
            }
            for (T it : treeNodes) {
                if (it.getParentId().equals(treeNode.getId())) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.add(it);
                }
            }
            if (comparator != null) {
                treeNode.getChildren().sort(comparator);
            }
        }
        return trees;
    }

}
