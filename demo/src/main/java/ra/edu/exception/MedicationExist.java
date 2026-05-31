package ra.edu.exception;

public class MedicationExist extends RuntimeException{
    public MedicationExist(String message){
        super(message);
    }
}
