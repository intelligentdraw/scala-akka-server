package models;

public enum DiagramProperties {
    WIDTH(500);

    private int value;

    private DiagramProperties(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
