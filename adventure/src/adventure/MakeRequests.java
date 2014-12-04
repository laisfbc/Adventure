/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adventure;

import java.awt.Color;
import java.util.Arrays;
import javalib.funworld.*;
import javalib.worldimages.*;

/**
 *
 * @author laisfb
 */
public class MakeRequests extends World {

    private final String str = "C:\\Users\\laisfb\\Documents\\GitHub\\Adventure\\adventure\\src\\images\\";
    int LEVEL;
    int SCORE;

    Client[] listOfClients;
    Request beingMade;
    
    RectangleImage boxRight;
    RectangleImage boxLeft;
    
    Posn[] boxesPositions = new Posn[9];
    FromFileImage[] boxes = new FromFileImage[9];
    
    MakeRequests(Client[] clients, int level, int score) {
        this.listOfClients = clients;
        this.LEVEL = level;
        this.SCORE = score;
        
        this.beingMade = new Request(new Food[0]);
        
        this.boxRight = new RectangleImage(new Posn(810, 846), 150, 40, Color.ORANGE);
        this.boxLeft = new RectangleImage(new Posn(90, 846), 150, 40, Color.ORANGE);
        
        int k = 0;
        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++) {
                k = 3*i + j;
                boxesPositions[k] = new Posn(300*j + 150, 250*i + 120);
                boxes[k] = new FromFileImage(boxesPositions[k], str + "box.png");
            }
    }
    
    @Override
    public WorldImage makeImage() {
        FromFileImage bg = new FromFileImage(new Posn(100,450), str + "kitchen.png");
        
        OverlayImages img = new OverlayImages(bg, this.boxRight);
        img = new OverlayImages(img, this.boxLeft);
        
        
        TextImage text = new TextImage(new Posn(800, 850), "DELIVER FOOD", Color.BLACK);
        text.size = 15;
        text.style = 1;
        img = new OverlayImages(img, text);
        

        text = new TextImage(new Posn(80, 850), "START OVER", Color.BLACK);
        text.size = 15;
        text.style = 1;
        img = new OverlayImages(img, text);
        
        FromFileImage box;
        String name;
        for (int i=0; i<9; i++) {
            img = new OverlayImages(img, boxes[i]);
                
            name = Request.everyFood[i].toString();
            text = new TextImage(new Posn(boxesPositions[i].x - 2*name.length() - 8, boxesPositions[i].y + 30), name, Color.BLACK);
            text.size = 20;
            text.style = 1;

            img = new OverlayImages(img, text);
        }
        
        RectangleImage whiteBox = new RectangleImage(new Posn(450, 850), 500, 80, Color.WHITE);
        img = new OverlayImages(img, whiteBox);
        
        FromFileImage food;
        for (int i=0; i<this.beingMade.size; i++) {
            food = (FromFileImage)this.beingMade.listOfFood[i].getImage();
            food.pinhole = new Posn(250 + i*70, 850);
            img = new OverlayImages(img, food);
        }
        
        return img;
    }
    
    @Override
    public World onMouseClicked(Posn loc) {
        
        // If clicked whithin the box of "deliver food"
        if (loc.inside(this.boxRight)) {
            //System.out.println("Deliver the food.");
            return new DeliverRequests(this.listOfClients, this.beingMade, this.LEVEL, 0, this.SCORE);
        }
        
        // If clicked whithin the box of "start over"
        else if (loc.inside(this.boxLeft)) {
            //System.out.println("Starting Over.");        
            this.beingMade = new Request(new Food[0]);          
        }
        
        else {
            int j=0;
            for (int i=0; i<9; i++) {
                if (loc.inside(boxes[i])) {
                    this.beingMade = this.beingMade.addFood(this.beingMade.everyFood[i]);
                    
                    //System.out.println(Request.everyFood[i].toString());
                }
            }
        }
        
        //System.out.println("Size: " + this.beingMade.size);        
        return this;
    }
    
    // Two "MakeRequests" are equal if they have the same list of clients and same list of requests made
    public boolean equals(World w) {
        if (w instanceof MakeRequests) {
            return Arrays.equals(this.listOfClients, ((MakeRequests)w).listOfClients) &&
                   beingMade.equals(((MakeRequests)w).beingMade);
        }
        
        return false;
    }
    
}