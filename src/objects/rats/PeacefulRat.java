package objects.rats;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.image.Image;
import objects.GameObject;
import tile.Direction;
import tile.Tile;


/**
 *
 * @author fahds
 */
/**
 * The class Peaceful rat extends rat
 */ 
public class PeacefulRat extends Rat {
    
    private boolean sterile;
    private boolean adult;
    private boolean pregnant;
    private String gender;
    private int timeToGiveBirth;
    private int timeToDevelop;
    private  Timer timer;
    private int numberOfBabies;
    private Image maleRatImage;
    private Image babyRatImage;
    private Image femaleRatImage;
    private Image pregnantFemaleRatImage;
    /** 
	 * Peaceful rat
	 * @param standingOn  the standing on
	 * @param sterile  the sterile
	 * @param adult  the adult
	 * @param pregnant  the pregnant
	 * @param gender  the gender
	 * @param int  the int
	 * @param timeToDevelop  the time to develop
	 * @param speed  the speed
	 * @param directionOfMovement  the direction of movement
	 * @return public
	 */
    public PeacefulRat (Tile standingOn, boolean sterile, boolean adult, boolean pregnant, String gender,
                        int timeToGiveBirth, int timeToDevelop, int speed, Direction directionOfMovement) {
        super(standingOn, speed,  directionOfMovement);
        
        
        
        this.sterile  = sterile;
        this.adult = adult;
        this.pregnant = pregnant;
        this.gender = gender;
        this.timeToDevelop = timeToDevelop;
        this.timeToGiveBirth = timeToGiveBirth; 
        
        if (!this.adult) {
            
            this.growUp();
        }
        if (this.pregnant) {
            
            this.bePregnant();
        }
        
        maleRatImage = new Image("file:resources/maleRat.png");
        babyRatImage = new Image ("file:resources/babyRat.png");
        femaleRatImage = new Image ("file:resources/femaleRat.png");
        pregnantFemaleRatImage = new Image ("file:resources/pregnantFemaleRat.png");
        decideIcon(adult, pregnant, gender);
        
    }
    
	/**
	 * Decide icon
	 * @param adult  the adult
	 * @param pregnant  the pregnant
	 * @param gender  the gender
	 */
    private void decideIcon (boolean adult, boolean pregnant, String gender) {
        
        Image decidedIcon;
        
        if (!adult) {
            
            decidedIcon = babyRatImage;
        } 
        else if (gender.equalsIgnoreCase("m") ){
            
            decidedIcon = maleRatImage;
        }
        else if (pregnant) {
            
             decidedIcon = pregnantFemaleRatImage;  
        }
        else {
            
            decidedIcon = femaleRatImage;
        }
        
        changeIcon(decidedIcon);
    }

    @Override
	/** 
	 * Change icon
	 * @param icon  the icon
	 */
    public void changeIcon(Image icon) {
        
        super.setIcon(icon);
    }
    
	/** 
	 * Is sterile
	 * @return boolean
	 */
    public boolean isSterile () {
        
        return this.sterile;
    }
    
	/** 
	 * Is adult
	 * @return boolean
	 */    
    public boolean isAdult () {
        
       return this.adult;
    }

	/** 
	 * Is pregnant
	 * @return boolean
	 */
    public boolean isPregnant() {

        return this.pregnant;
    }
    
	/** 
	 * Gets the gender
	 * @return the gender
	 */
    public String getGender () {
        
        return this.gender;
    }
    
	/** 
	 * Mate
	 * @param partner  the partner
	 */
    public void mate (PeacefulRat partner) {
        
        if (!this.isSterile() && !partner.isSterile() && this.isAdult() && partner.isAdult()
            && partner.getGender().equalsIgnoreCase("Female") && !partner.isPregnant()) {
            
            partner.bePregnant ();
        }
        else {
            
        }   
    }
    
	/** 
	 * Be pregnant
	 */
    private void bePregnant () {
        
        
        this.pregnant = true;
        decideIcon (adult, pregnant, gender);   
        
        Random random = new Random(); 
        this.numberOfBabies = random.nextInt((5 - 2) + 1) + 2;
        
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
			/**
			 * Run
			 */
            public void run() {
                
                giveBirth();
            }
        }, this.timeToGiveBirth);

    }
    
	/** 
	 * Give birth
	 */
    private void giveBirth (){
        
        this.pregnant = false;
        decideIcon (adult, pregnant, gender);   
        
        for (int i = 0; i == this.numberOfBabies; i++) {
            String newBornGender;
            
            Random random = new Random(); 
            int decision = random.nextInt(2);
            
            if (decision == 1) {
                
                newBornGender = "Male";
            }
            else {
                
                newBornGender = "Female";
            }
            
            GameObject newBorn = new PeacefulRat (super.getStandingOn(), this.isSterile(), false, false,newBornGender,
                    this.timeToGiveBirth, this.timeToDevelop, super.getSpeed(), super.getDirectionOfMovement());
            
            GameObject.getBoard().addObject(newBorn);
            
        }
        
    }
    
	/** 
	 * Sets the gender
	 * @param gender  the gender
	 */
    public void setGender (String gender) {
        
        this.gender = gender;
        
        decideIcon (this.adult, this.pregnant, this.gender);
    }
    
	/** 
	 * Sets the sterilisation
	 * @param sterile  the sterile
	 */
    public void setSterilisation (boolean sterile) {
        
        this.sterile = sterile;
    }
    
	/** 
	 * Sets the adult
	 * @param adult  the adult
	 */
    public void setAdult (boolean adult) {
        
        this.adult = adult;
        
       decideIcon (this.adult, this.pregnant, this.gender);
    }
    
	/** 
	 * Grow up
	 */
    private void growUp () {
        
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
			/**
			* Run
			*/
            public void run() {
                
                setAdult(true);
                decideIcon (adult, pregnant, gender);   
            }
        }, this.timeToDevelop);
    }
}
