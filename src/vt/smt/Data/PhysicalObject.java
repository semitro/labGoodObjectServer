package vt.smt.Data;

import sun.font.PhysicalFont;

import java.io.Serializable;

/**
 * Created by semitro on 03.12.16.
 */
public abstract class PhysicalObject
        implements Cleanable, Comparable<PhysicalObject>,Serializable {

    public PhysicalObject(PhysicalObject copy){
        this.setWeight(copy.getWeight());
        this.setisCleaning(copy.isClean());
    }
    // Вещи, которые могут быть в доме
    protected boolean isClean = true;
    protected double weight; // Вес
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
