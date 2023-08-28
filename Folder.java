/**
 * The Folder class contains the methods used to manipulate the folders which have arraylists of emails
 * implements Serializable
 *  so the obje can be saved as a .obj file
 * @author Joshua James
 * ID Number: 115113767
 * StonyBrook Email: joshua.james@stonybrook.edu
 * 
 * */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Folder implements Serializable{
    private ArrayList<Email> emails = new ArrayList<Email>(); //list of emails
    private String name; //name of the folder
    private String currentSortingMethod; //current way of sorting used 
    /**
     * default constructor
     */
    public Folder(){
        name = null;
        currentSortingMethod = "date descending";
    }
    /**
     * constructor that gets input for name 
     * @param n
     * input for name
     */
    public Folder(String n){
        name = n;
        currentSortingMethod = "date descending";
    }
    /**
     * mutator for name
     * @param n
     * input for name 
     */
    public void setName(String n){
        name = n;
    }
    /**
     * accessor for name
     * @return
     * value of name
     */
    public String getName(){
        return name;
    }
    /**
     * mutator for currentSortingMethod
     * @param c
     * input for currentSortingMethod
     */
    public void setCurSortMethod(String c){
        currentSortingMethod = c;  
    }
    /**
     * accessor for currentSortingMethod
     * @return
     * value of currentSortingMethod
     */
    public String getCurSortMethod(){
        return currentSortingMethod;
    }
    /**
     * Prints all the emails in the folder to the console
     * prints the subject, person it was sent to, and the time it was created 
     */
    public void printEmails(){
        System.out.println("Folder: " + name); 
        for(int i = 0; i < emails.size(); i++){
            System.out.println(i + ". " + emails.get(i).getSub() + " | Sent to: " + emails.get(i).getTo() + " | Time: " + emails.get(i).getTimeStamp().getTime());
        }
    }
    /**
     * finds returns an email in a folder using its subject
     * @param sub
     *     subject of email to be found
     * @return
     *  the email or null if it was not found 
     */
    public Email getEmail(String sub){
        for(int i = 0; i < emails.size(); i++){
            if(sub.equals(emails.get(i).getSub())){
                return emails.get(i); 
            }
        }
        return null; 

    }
    /**
     * finds an email based on the time in milliseconds
     * @param time
     *  time in milliseconds of the email to be found
     * @return
     * the email or null if not found
     */
    public Email getEmail(long time){
        for(int i = 0; i < emails.size(); i++){
            if(time == emails.get(i).getTimeStamp().getTimeInMillis()){
                return emails.get(i);
            }
        }
        return null; 
    }
    /**
     * returns the arraylist of emails
     * @return
     * the arraylist of emails
     * */
    public ArrayList<Email> getEmails(){
        return emails;
    }
    
    /**
     * adds an email to the arraylist and then sorts the list based on the currentSorting method
     * @param email
     * email to be added
     */
    public void addEmail(Email email){
        
        this.emails.add(email);
        if(currentSortingMethod.equals("date descending")){
            sortByDateDescending();
        }
        else if(currentSortingMethod.equals("date ascending")){
            sortByDateAscending();
        }
        else if(currentSortingMethod.equals("subject descending")){
            sortBySubjectDescending();
        }
        else if(currentSortingMethod.equals("subject ascending")){
            sortBySubjectAscending();
        }
        else {
            System.out.println("Invalid current sorting method");
        }

    }
    /**
     * removes the email from the list at the given index
     * @param index
     * index of the email to be remvoed
     * @return
     * returns the email it removed
     */
    public Email removeEmail(int index){
        Email e = emails.get(index);
        emails.remove(index);
        return e;
    }
    /**
     * uses bubble sort to sort the email list in alphabetical order based on subject
     */
    public void sortBySubjectAscending(){
        String[] subject = new String[emails.size()]; //creates array
        for(int i = 0; i < subject.length; i++){
            subject[i] = emails.get(i).getSub(); //adds all subject strings to it
        }
        for(int i = 0; i <= subject.length - 2; i++){ //sorts the array
            for(int j = subject.length - 1; j > i; j--){
                if(subject[j].compareToIgnoreCase(subject[j -  1]) > 0 ){
                    String temp = subject[j];
                    subject[j] = subject[j-1];
                    subject[j-1] = temp;
                }
            }
        }
        
        for(int p = 0; p < subject.length; p++){ //adds the emails to the emails arraylist in sorted order
            emails.add(getEmail((subject[p]))); 
        }
        
        for(int m = 0; m < subject.length; m++){ //deletes the original emails that were unsorted
            emails.remove(0); 
            
        }

    }
    /**
     * uses bubble sort to sort the emails by subject in alphabetical order 
     */
    public void sortBySubjectDescending(){
        String[] subject = new String[emails.size()]; //uses array of the subjects and sorts them
        for(int i = 0; i < subject.length; i++){
            subject[i] = emails.get(i).getSub(); 
        }
        for(int i = 0; i <= subject.length - 2; i++){
            for(int j = subject.length - 1; j > i; j--){
                if(subject[j].compareToIgnoreCase(subject[j -  1]) < 0 ){
                    String temp = subject[j];
                    subject[j] = subject[j-1];
                    subject[j-1] = temp;
                }
            }
        }
        
        for(int p = 0; p < subject.length; p++){ //adds the emails in sorted order to the arraylist
            emails.add(getEmail((subject[p]))); 
        }
        
        for(int m = 0; m < subject.length; m++){ //removes the unsorted emails
            emails.remove(0); 
            
        }

    }
    /**
     * uses bubble sort to sort the emails by date created
     */
    public void sortByDateAscending(){
         GregorianCalendar[] dates = new GregorianCalendar[emails.size()]; //creates array with all the dates the email was created
        for(int i = 0; i < dates.length; i++){
            dates[i] = emails.get(i).getTimeStamp(); 
            
        }

        for(int i = 1; i <= dates.length - 1; i++){ //sorts the array
            GregorianCalendar item = dates[i];
            int j = i;
            while(j > 0 && dates[j-1].getTimeInMillis() < item.getTimeInMillis()){
                dates[j] = dates[j - 1];
                j--;
            }
            dates[j] = item;
        }

        for(int p = 0; p < dates.length; p++){ //finds each email using time and adds it to the arraylist in sorted order
            emails.add(getEmail((dates[p].getTimeInMillis()))); 
        }
        
        for(int m = 0; m < dates.length; m++){ //gets rid of the original unsorted emails
            emails.remove(0); 
            
        }
        
    }
    /**
     * uses bubble sort to sort the arraylist by date
     */
    public void sortByDateDescending(){
        GregorianCalendar[] dates = new GregorianCalendar[emails.size()]; //array of the dates
        for(int i = 0; i < dates.length; i++){
            dates[i] = emails.get(i).getTimeStamp(); 
            
        }

        for(int i = 1; i <= dates.length - 1; i++){ //sorts the array
            GregorianCalendar item = dates[i];
            int j = i;
            while(j > 0 && dates[j-1].getTimeInMillis() > item.getTimeInMillis()){
                dates[j] = dates[j - 1];
                j--;
            }
            dates[j] = item;
        }

        for(int p = 0; p < dates.length; p++){ //adds emails to arraylsit in sorted order 
            emails.add(getEmail((dates[p].getTimeInMillis()))); 
        }
        
        for(int m = 0; m < dates.length; m++){ //removes unsorted emails
            emails.remove(0); 
            
        }
        
    }


}
