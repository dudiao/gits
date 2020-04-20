package xyz.gits.boot.system.enums;

/**
 *显示类型type
 * @author luojie
 * @date 2019/11/19
 */
public enum VisibleType {

    /**
     *显示
     */
    SHOW("1","显示"),

    /**
     *不显示
     */
    UN_SHOW("0","不显示")
    ;


    private String type;

    private String message;

    VisibleType(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
