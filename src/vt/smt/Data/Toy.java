package vt.smt.Data;

/**
 *
 * Медведики внутривенные
 *
 */

public class Toy extends PhysicalObject{

    private String name;

    public void setName(String name) {
        this.name = name;
    }



    private Toy(){
        super();
    }

    public Toy(String name){
        super();
        this.name = name;
        weight = 0.5;
    }

    public Toy(Toy toy){
        this(toy.getName(),toy.getWeight(),toy.isClean());
    }

    public Toy(String name,double weight, boolean isClean){
        super();
        this.name  = name;
        this.weight = weight;
        this.isClean= isClean;
    }

    public String getName(){
        return name;
    }

    @Override
    public void cleanUp(){
        isClean = true;
    }

    @Override
    public String toString(){
        return  new String("Вес: " + this.getWeight() + " Имя: "  + this.getName() + " Убарно: " + this.isClean());
    }

    @Override
    public int hashCode() {
        return name.hashCode() + Double.hashCode(this.weight) + Boolean.hashCode(this.isClean);
    }

}