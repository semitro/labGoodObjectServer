package vt.smt.Data;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


public abstract class PhysicalObject
        implements Cleanable, Comparable<PhysicalObject>,Serializable {

    public PhysicalObject(PhysicalObject copy){
        this.setWeight(copy.getWeight());
        this.setisCleaning(copy.isClean());
        creationTime = LocalDateTime.now().atZone(ZoneId.systemDefault());
    }

    protected boolean isClean = true;

    protected double weight; // Вес

    protected ZonedDateTime creationTime;

    public ZonedDateTime getCreationTime() {
        return creationTime;
    }

    public void setisCleaning(boolean clean){
        isClean = clean;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight(){
        return weight;
    }

    public PhysicalObject(){
        creationTime = LocalDateTime.now().atZone(ZoneId.systemDefault());
    }

    public void cleanUp() {
        // Поставить вещь на место - прибрать
        isClean = true;
    }

    public boolean isClean(){return isClean;}

    @Override
    public int compareTo(PhysicalObject obj){
        return (int)Math.floor(this.getWeight()-obj.getWeight());
    }

    @Override
    public boolean equals(Object object){
        if(object instanceof PhysicalObject && this == object)
            return true;
        else
            return false;
    }

    @Override
    public String toString(){
        return new String();
    }

}
