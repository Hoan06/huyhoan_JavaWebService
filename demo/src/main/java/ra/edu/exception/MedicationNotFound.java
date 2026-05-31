package ra.edu.exception;

public class MedicationNotFound extends RuntimeException{
    public MedicationNotFound(String message){
        super(message);
    }
}
