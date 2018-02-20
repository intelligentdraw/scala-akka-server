package models;

public enum BubbleProperties {
    WIDTH(135), HEIGHT(70), MARGIN(5);

    private int value;
    private BubbleProperties(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
