package models;

public enum DiagramProperties {
    SHORT_WIDTH(500),
    LONG_WIDTH(750);

    private int value;

    private DiagramProperties(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
