package xyz.gits.boot.system.enums;

/**
 * 资源类型
 *
 * @author songyinyin
 */
public enum ResourceType {

    /**
     * 系统
     */
    A("A", "系统"),

    /**
     * 菜单
     */
    B("B", "菜单"),

    /**
     * 按钮
     */
    C("C", "按钮"),

    /**
     * 链接
     */
    D("D", "链接");

    private String type;

    private String name;

    ResourceType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
