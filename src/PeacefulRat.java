
import javafx.scene.image.Image;


/**
 *
 * @author fahds
 */
public class PeacefulRat extends Rat{
    
    private boolean sterile;
    private boolean adult;
    private boolean pregnant;
    private String gender;
    private int timeToGiveBirth;
    private int timeToDevelop;
    
    public PeacefulRat (Tile standingOn,  boolean sterile, boolean adult, boolean pregnant, String gender,
                        int timeToGiveBirth, int timeToDevelop, int speed, Direction directionOfMovement) {
        super(standingOn, null, speed,  directionOfMovement);
        
        Image icon = decideIcon(adult, pregnant, gender);
        this.changeIcon(icon);
        
        this.sterile  = sterile;
        this.adult = adult;
        this.pregnant = pregnant;
        this.gender = gender;
        this.timeToDevelop = timeToDevelop;
        this.timeToGiveBirth = timeToGiveBirth;    
    }
    
    private Image decideIcon (boolean adult, boolean pregnant, String gender) {
        
        Image decidedIcon = null;
        
        if (!adult) {
            
           // decidedIcon = babyRat.png;
        } 
        else if (gender.equalsIgnoreCase("Male") ){
            
            // decidedIcon = AdultMaleRat.png
        }
        else if (pregnant) {
            
            //decidedIcon = pregnantFemaleRat.png;  
        }
        else {
            
            //decidedIcon = femaleRat.png
        }
        
        return decidedIcon;
    }

    @Override
    public void changeIcon(Image icon) {
        
    }
    
    public Boolean isSterile () {
        
        
        return this.sterile;
    }
    
    public Boolean isAdult () {
        
        
       return this.adult;
    }

    public Boolean isPregnant() {

        return this.pregnant;
    }
    
    public String getGender () {
        
        return this.gender;
    }
     
}
