package card.creature;
import card.product.CarnivoreFood;
import card.product.HerbivoreFood;
import exceptionkerajaan.BaseException;
import player.*;



public class Omnivore extends Creature implements Animals{
    
    public Omnivore() {
        super();
    }

    public Omnivore(String name, String pathToImg, Player owner) {
        super(name, pathToImg, owner);
    }
    
    // without owner
    public Omnivore(String name, String pathToImg) {
        super(name, pathToImg);
    }
    public void eat(CarnivoreFood food) throws BaseException{
        System.out.println("Eating " + food.getName() + "..." );
        this.increaseWeight(food.getAdditionalWeight());
    }

    public void eat(HerbivoreFood food) throws BaseException{
        System.out.println("Eating " + food.getName() + "..." );
        this.increaseWeight(food.getAdditionalWeight());
        
    }


}
