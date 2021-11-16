
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.image.Image;
import tile.Direction;
import tile.Tile;


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
    private  Timer timer;
    private int numberOfBabies;
    
    public PeacefulRat (Tile standingOn, boolean sterile, boolean adult, boolean pregnant, String gender,
                        int timeToGiveBirth, int timeToDevelop, int speed, Direction directionOfMovement) {
        super(standingOn, null, speed,  directionOfMovement);
        
        this.changeIcon(decideIcon(adult, pregnant, gender));
        
        this.sterile  = sterile;
        this.adult = adult;
        this.pregnant = pregnant;
        this.gender = gender;
        this.timeToDevelop = timeToDevelop;
        this.timeToGiveBirth = timeToGiveBirth; 
        
        if (!this.adult) {
            
            this.growUp();
        }
        
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
        
        super.setIcon(icon);
    }
    
    public boolean isSterile () {
        
        return this.sterile;
    }
    
    public boolean isAdult () {
        
       return this.adult;
    }

    public boolean isPregnant() {

        return this.pregnant;
    }
    
    public String getGender () {
        
        return this.gender;
    }
    
    public void mate (Board board, PeacefulRat partner) {
        
        if (!this.isSterile() && !partner.isSterile() && this.isAdult() && partner.isAdult()
            && partner.getGender().equalsIgnoreCase("Female") && !partner.isPregnant()) {
            
            partner.bePregnant (board);
        }
        else {
            
        }   
    }
    
    private void bePregnant (Board board) {
        
        //this.changeIcon(pregnantFemaleRat.png);
        this.pregnant = true;
        
        Random random = new Random(); 
        this.numberOfBabies = random.nextInt((5 - 2) + 1) + 2;
        
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                
                giveBirth(board);
            }
        }, this.timeToGiveBirth);

    }
    
    private void giveBirth (Board board){
        
        //this.changeIcon(femaleRat.png);
        this.pregnant = false;
        
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
            
            Object newBorn = new PeacefulRat (super.getStandingOn(), this.isSterile(), false, false,newBornGender, 
                    this.timeToGiveBirth, this.timeToDevelop, super.getSpeed(), super.getDirectionOfMovement());
            
            board.addObject(newBorn);
            
        }
        
    }
    
    public void setGender (String gender) {
        
        this.gender = gender;
        
        this.changeIcon(decideIcon (this.adult, this.pregnant, this.gender));
    }
    
    public void setSterilisation (boolean sterile) {
        
        this.sterile = sterile;
    }
    
    public void setAdult (boolean adult) {
        
        this.adult = adult;
        
       this.changeIcon(decideIcon (this.adult, this.pregnant, this.gender));
    }
    
    private void growUp () {
        
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                
                setAdult(true);
                changeIcon(decideIcon (adult, pregnant, gender));   
            }
        }, this.timeToDevelop);
    }
}
