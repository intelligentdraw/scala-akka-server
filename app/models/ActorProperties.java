package models;

public enum ActorProperties {
    HEAD_RADIUS(15);

    private int value;

    private ActorProperties(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
