/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure;

import java.util.Random;
import javalib.worldimages.Posn;

/**
 *
 * @author laisfb
 */
public class Test {

    private final static boolean testingMode = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // level , showOrders , time
        if (!testingMode) {
            TakeOrders take = new TakeOrders(1);
            take.bigBang(900,900,1);
        }
        else {
            
            for(int i=0; i<50; i++) {
                check_transitions();
                check_TakeOrders();
                check_MakeOrders();
                check_DeliverOrders();
            }
            System.out.println("All tests passed!");
        }

    }

    public static int randomInt(int max) {
	Random r = new Random();
	return r.nextInt(max + 1);
    }
    
    public static Posn randomPos() {
        int x = randomInt(900);
        int y = randomInt(900);
        return new Posn(x,y);
    }

    public static void check_transitions() {
        TakeOrders take = new TakeOrders(1);
        MakeOrders make = new MakeOrders(take.listOfClients, take.LEVEL, 0);
        DeliverOrders deliver = new DeliverOrders(make.listOfClients, make.beingMade, make.LEVEL, 0, make.SCORE);
        
        Posn pos = randomPos(); // new Posn(810, 846);
        // System.out.println("Pos: (" + pos.x + "," + pos.y + ")");
        // System.out.println("Box: (" + take.box.pinhole.x + "," + take.box.pinhole.y + ")");
        
        if (pos.inside(take.box) && !make.equals(take.onMouseClicked(pos)))
           throw new RuntimeException("ERROR IN: check_transitions (takeOrders -> makeOrders)");
        
        else if (pos.inside(make.boxRight) && !deliver.equals(make.onMouseClicked(pos)))
            throw new RuntimeException("ERROR IN: check_transitions (takeOrders -> makeOrders)");
        
    }
    
    public static void check_TakeOrders() {
        // In the TakeOrders world there are always between 1 and 3 clients
        
        // Each order must be have at least 3 elements, all different
        
        // If the client's order was "asked" again:
        //   a baloon with the same order is shown
        //   the score decreases
        //   the counter of time goes back to zero
    }
    
    public static void check_MakeOrders() {
        // Every time the MakeOrders world is created,
        //   the order being made is empty
        //   and there are always 9 boxes of food
        
        // Everytime a box is clicked, the order's size
        //   increase in one and a new image shows up
        
        // If the "Start Over" button is clicked,
        //   the order is restarted, meaning the size
        //   is zero, and all the images are gone
    }
    
    public static void check_DeliverOrders() {
        // In the DeliverOrders world, the list of clients
        //   is the same as the one of the world who called
        //   the MakeOrders world
        
        // If there are no clients being shown, then the 
        //   "nextLevel" world is called
        
        // When an order is delivered, it is either right or wrong
        //   if it's right, the client goes away and the score increases
        //   if it's wrong, the client stays and the score decreases
        
        // If the client's order was "asked" again:
        //   a baloon with the same order is shown
        //   the score decreases
        //   the counter of time goes back to zero

    }
}
