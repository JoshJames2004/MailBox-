/**
 * The MailBox Class Contains the main method and runs the menu loop and contains certain methods for utilizing the email and folder classes
 * 
 * @author Joshua James
 * ID Number: 115113767
 * StonyBrook Email: joshua.james@stonybrook.edu
 * 
 * */
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.IOException;
public class MailBox implements Serializable{

    private Folder inbox; //main folder in the email box
    private Folder trash; //contains deleted emails
    private ArrayList<Folder> folders = new ArrayList<Folder>(); //arraylist of new folders the user creates
    public static MailBox mailbox; //the mailbox everything is done in

    /**
     * default constructor
     */
    public MailBox(){
        inbox = new Folder("inbox"); 
        trash = new Folder("trash"); 
    }
    /**
     * main method
     * runs menu loop that user uses to interact with the program
     * 
     */
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Scanner sc = new Scanner(System.in);
        boolean cont = true;
        mailbox = new MailBox();
        try { //looks for.obj file that contains previous emails
            FileInputStream file = new FileInputStream("mailbox.obj");
            ObjectInputStream fin = new ObjectInputStream(file);
            mailbox = (MailBox)fin.readObject();
            file.close();
        }
        catch(IOException e){

        }
        catch(ClassNotFoundException e){

        }
        //main menu loop
        while(cont){
            System.out.println("\nMenu:");
            System.out.println("A - Add Folder");
            System.out.println("R - Remove Folder");
            System.out.println("C - Compose Email");
            System.out.println("F - View Folder");
            System.out.println("I - View Inbox");
            System.out.println("T - View Trash");
            System.out.println("E - Empty Trash");
            System.out.println("Quit the program"); 

            String input = s.nextLine();

            if(input.equalsIgnoreCase("A")){ //adds folder to folders arraylist
                System.out.println("Add a folder selected");
                System.out.println("Enter the name of the folder: ");
                String folderName = s.nextLine();
                mailbox.addFolder(new Folder(folderName));
            }
            else if(input.equalsIgnoreCase("R")){ //removes folder from folders arraylist
                System.out.println("Remove Folder selected");
                System.out.println("Enter the name of the folder to be deleted");
                String folderN = s.nextLine();
                mailbox.deleteFolder(folderN);
            }
            else if(input.equalsIgnoreCase("C")){ //gets user input to make email and adds it to inbox
                System.out.println("Compose an Email selected");
                System.out.println("Enter who you want to send the email to");
                String to = s.nextLine(); 
                System.out.println("Enter who you want to cc");
                String cc = s.nextLine();
                System.out.println("Enter who you want to bcc");
                String bcc = s.nextLine();
                System.out.println("Enter the subject of the email");
                String subj = s.nextLine();
                System.out.println("Enter the body of the email"); 
                String bod = s.nextLine();
                mailbox.getInbox().addEmail(new Email(to, cc, bcc, subj, bod));
            }
            else if (input.equalsIgnoreCase("F")){ //allows user to manipulate emails in folders 
                System.out.println("View Folder selected");
                mailbox.printFolders();
                System.out.println("Enter the name of the folder:");
                String fName = s.nextLine();
                if(mailbox.getFolder(fName) != null ){
                    boolean c = true;
                    while(c){ //folder sub menu loop
                        System.out.println("Emails:");
                        mailbox.getFolder(fName).printEmails();
                        System.out.println("---------------------------");
                        System.out.println("Folder SubMenu");
                        System.out.println("M - Move Email");
                        System.out.println("D - Delete Email");
                        System.out.println("V - View email Contents");
                        System.out.println("SA - Sort by Subject Ascending");
                        System.out.println("SD - Sort by Subject Descending");
                        System.out.println("DA - Sort by Date Ascending");
                        System.out.println("DD - Sort by Date Descending");
                        System.out.println("R - Return to Main Menu");
                        System.out.println("Please Select an option");
                        String in = s.nextLine();
                        if(in.equalsIgnoreCase("M")){ //allows user to change the folder that a email is in
                            System.out.println("Enter the index of the email you want to move");
                            int ind = s.nextInt();
                            mailbox.printFolders();
                            System.out.println("Select the folder to move the email to");
                            String folderNa = s.nextLine();
                            if(mailbox.getFolder(folderNa) != null){
                                mailbox.moveEmail(mailbox.getFolder(fName).getEmails().get(ind), mailbox.getFolder(folderNa));
                            }
                            else if(folderNa.equals("inbox")){
                                mailbox.moveEmail(mailbox.getFolder(fName).getEmails().get(ind), mailbox.getInbox());
                            }
                            else if(folderNa.equals("trash")){
                                mailbox.moveEmail(mailbox.getFolder(fName).getEmails().get(ind), mailbox.getTrash());
                            }
                            
                        }
                        else if(in.equalsIgnoreCase("D")){ //allows user to delete a email and move it to trash
                            System.out.println("Enter the index of the email you want to delete");
                            int ind = s.nextInt();
                            mailbox.deleteEmail(mailbox.getFolder(fName).getEmails().get(ind));
                            
                        }
                        else if(in.equalsIgnoreCase("V")){ //prints out all the information of a certain email
                            System.out.println("Enter the index of the email you want to see");
                            int ind = s.nextInt();
                            
                            System.out.println("To: " + mailbox.getFolder(fName).getEmails().get(ind).getTo());
                            System.out.println("CC: " + mailbox.getFolder(fName).getEmails().get(ind).getCC());
                            System.out.println("BCC: " + mailbox.getFolder(fName).getEmails().get(ind).getBCC());
                            System.out.println("Time: " + mailbox.getFolder(fName).getEmails().get(ind).getTimeStamp().getTime());
                            System.out.println("Subject: " + mailbox.getFolder(fName).getEmails().get(ind).getSub());
                            System.out.println("Body: " + mailbox.getFolder(fName).getEmails().get(ind).getBod());

                        }
                        else if(in.equalsIgnoreCase("SA")){ //prints out all the emails by reverse alphabetical order of subject
                            mailbox.getFolder(fName).sortBySubjectAscending();
                        }
                        else if(in.equalsIgnoreCase("SD")){ //prints out all the emails in alphabetical order of subject
                            mailbox.getFolder(fName).sortBySubjectDescending();
                        }
                        else if(in.equalsIgnoreCase("DA")){ //prints out all the emails from oldest at the top to newest at the bottom
                            mailbox.getFolder(fName).sortByDateAscending();
                        }
                        else if(in.equalsIgnoreCase("DD")){ //prints out all the emails from newest at the top to oldest at the bottom
                            mailbox.getFolder(fName).sortByDateDescending();
                        }
                        else if(in.equalsIgnoreCase("R")){ //quits the fodler sub menu
                            c = false;
                        }
                    }
                } 
            }
            else if(input.equalsIgnoreCase("I")){ //allows user to manipulate emails in the inbox folder using folder submenu - same as above 
                System.out.println("View Inbox Selected");
                boolean c = true;
                while(c){
                    System.out.println("Emails:");
                    mailbox.getInbox().printEmails();
                    System.out.println("---------------------------");
                    System.out.println("Folder SubMenu");
                    System.out.println("M - Move Email");
                    System.out.println("D - Delete Email");
                    System.out.println("V - View email Contents");
                    System.out.println("SA - Sort by Subject Ascending");
                    System.out.println("SD - Sort by Subject Descending");
                    System.out.println("DA - Sort by Date Ascending");
                    System.out.println("DD - Sort by Date Descending");
                    System.out.println("R - Return to Main Menu");
                    System.out.println("Please Select an option");
                    String in = s.nextLine();
                    if(in.equalsIgnoreCase("M")){
                        System.out.println("Enter the index of the email you want to move");
                        int ind = sc.nextInt();
                        mailbox.printFolders();
                        System.out.println("Select the folder to move the email to");
                        String folderNa = s.nextLine();
                        if(mailbox.getFolder(folderNa) != null){
                            mailbox.moveEmail(mailbox.getInbox().getEmails().get(ind), mailbox.getFolder(folderNa));
                        }
                        else if(folderNa.equals("inbox")){
                            mailbox.moveEmail(mailbox.getInbox().getEmails().get(ind), mailbox.getInbox());
                        }
                        else if(folderNa.equals("trash")){
                            mailbox.moveEmail(mailbox.getInbox().getEmails().get(ind), mailbox.getTrash());
                        }
                        
                    }
                    else if(in.equalsIgnoreCase("D")){
                        System.out.println("Enter the index of the email you want to delete");
                        int ind = sc.nextInt();
                        mailbox.deleteEmail(mailbox.getInbox().getEmails().get(ind));
                        
                    }
                    else if(in.equalsIgnoreCase("V")){
                        System.out.println("Enter the index of the email you want to see");
                        int ind = sc.nextInt();
                        
                        System.out.println("To: " + mailbox.getInbox().getEmails().get(ind).getTo());
                        System.out.println("CC: " + mailbox.getInbox().getEmails().get(ind).getCC());
                        System.out.println("BCC: " + mailbox.getInbox().getEmails().get(ind).getBCC());
                        System.out.println("Time: " + mailbox.getInbox().getEmails().get(ind).getTimeStamp().getTime());
                        System.out.println("Subject: " + mailbox.getInbox().getEmails().get(ind).getSub());
                        System.out.println("Body: " + mailbox.getInbox().getEmails().get(ind).getBod());
                        
                    }
                    else if(in.equalsIgnoreCase("SA")){
                        mailbox.getInbox().sortBySubjectAscending();
                    }
                    else if(in.equalsIgnoreCase("SD")){
                        mailbox.getInbox().sortBySubjectDescending();
                    }
                    else if(in.equalsIgnoreCase("DA")){
                        mailbox.getInbox().sortByDateAscending();
                    }
                    else if(in.equalsIgnoreCase("DD")){
                        mailbox.getInbox().sortByDateDescending();
                    }
                    else if(in.equalsIgnoreCase("R")){
                        c = false;
                    }
                }

            }
            else if(input.equalsIgnoreCase("T")){ //allows user to manipulate emails in the trash folder using folder sub menu - same as above
                System.out.println("View Trash Selected");
                boolean c = true;
                while(c){
                    System.out.println("Emails:");
                    mailbox.getTrash().printEmails();
                    System.out.println("---------------------------");
                    System.out.println("Folder SubMenu");
                    System.out.println("M - Move Email");
                    System.out.println("D - Delete Email");
                    System.out.println("V - View email Contents");
                    System.out.println("SA - Sort by Subject Ascending");
                    System.out.println("SD - Sort by Subject Descending");
                    System.out.println("DA - Sort by Date Ascending");
                    System.out.println("DD - Sort by Date Descending");
                    System.out.println("R - Return to Main Menu");
                    System.out.println("Please Select an option");
                    String in = s.nextLine();
                    if(in.equalsIgnoreCase("M")){
                        System.out.println("Enter the index of the email you want to move");
                        int ind = sc.nextInt();
                        mailbox.printFolders();
                        System.out.println("Select the folder to move the email to");
                        String folderNa = s.nextLine();
                        if(mailbox.getFolder(folderNa) != null){
                            mailbox.moveEmail(mailbox.getTrash().getEmails().get(ind), mailbox.getFolder(folderNa));
                        }
                        else if(folderNa.equals("inbox")){
                            mailbox.moveEmail(mailbox.getTrash().getEmails().get(ind), mailbox.getInbox());
                        }
                        else if(folderNa.equals("trash")){
                            mailbox.moveEmail(mailbox.getTrash().getEmails().get(ind), mailbox.getTrash());
                        }
                        
                    }
                    else if(in.equalsIgnoreCase("D")){
                        System.out.println("Enter the index of the email you want to delete");
                        int ind = sc.nextInt();
                        mailbox.deleteEmail(mailbox.getTrash().getEmails().get(ind));
                        
                    }
                    else if(in.equalsIgnoreCase("V")){
                        System.out.println("Enter the index of the email you want to see");
                        int ind = sc.nextInt();
                        
                        System.out.println("To: " + mailbox.getTrash().getEmails().get(ind).getTo());
                        System.out.println("CC: " + mailbox.getTrash().getEmails().get(ind).getCC());
                        System.out.println("BCC: " + mailbox.getTrash().getEmails().get(ind).getBCC());
                        System.out.println("Time: " + mailbox.getTrash().getEmails().get(ind).getTimeStamp().getTime());
                        System.out.println("Subject: " + mailbox.getTrash().getEmails().get(ind).getSub());
                        System.out.println("Body: " + mailbox.getTrash().getEmails().get(ind).getBod());
                        
                    }
                    else if(in.equalsIgnoreCase("SA")){
                        mailbox.getTrash().sortBySubjectAscending();
                    }
                    else if(in.equalsIgnoreCase("SD")){
                        mailbox.getTrash().sortBySubjectDescending();
                    }
                    else if(in.equalsIgnoreCase("DA")){
                        mailbox.getTrash().sortByDateAscending();
                    }
                    else if(in.equalsIgnoreCase("DD")){
                        mailbox.getTrash().sortByDateDescending();
                    }
                    else if(in.equalsIgnoreCase("R")){
                        c = false;
                    }
                }

            }
            else if(input.equalsIgnoreCase("E")){ //allows user to remove all emails in the trash folder 
                System.out.println("Empty Trash selected");
                mailbox.clearTrash();
            }
            else if(input.equalsIgnoreCase("Q")){ //quits the menu and the program - saves the mailbox object to a file 
                System.out.println("Quit selected");
                try {
                    FileOutputStream file = new FileOutputStream("mailbox.obj");
                    ObjectOutputStream fout = new ObjectOutputStream(file);
                    fout.writeObject(mailbox);
                    fout.close();
                }
                catch(IOException r){

                }
                cont = false;
                return;
            }

        }
        
    }
    /**
     * accessor for inbox folder 
     * @return
     * inbox folder object
     */
    public Folder getInbox(){
        return inbox; 
    }
    /**
     * accessor for trash folder
     * @return
     * trash folder object
     */
    public Folder getTrash(){
        return trash; 
    }
    /**
     * prints out all the Names of all the folders in the folders array list and the inbox and trash folders
     */
    public void printFolders(){
        System.out.println("Folders:");
        for(int i = 0; i < folders.size(); i++){
            System.out.println(folders.get(i).getName());
        }
        System.out.println("Inbox");
        System.out.println("Trash"); 
    }
    /**
     * adds a folder to the folders arraylist
     * @param folder
     * folder to be added 
     */
    public void addFolder(Folder folder){
        
        for(int i = 0; i < folders.size(); i++){ //checks if a folder with the same name already exists 
            if(folder.getName().equals(folders.get(i).getName())){
                System.out.println("folder with same name already exists");
                return;
            }
        }
        folders.add(folder); 
    }
    /**
     * deletes a folder from the folders array list
     * @param name
     * name of folder to be deleted
     */
    public void deleteFolder(String name){
        for(int i = 0; i < folders.size(); i++){
            if(name.equals(folders.get(i).getName())){
                folders.remove(i);
                System.out.println("Folder deleted"); 
                return;
            }
        }
    }
    /**
     * makes a new email object and adds it to the inbox folder
     */
    public void composeEmail(){
        Scanner s = new Scanner(System.in); 
        System.out.println("Who do you want to send it to");
        String t = s.nextLine();
        System.out.println("Who do you want to cc");
        String c = s.nextLine();
        System.out.println("Who do you want to bcc");
        String bc = s.nextLine();
        System.out.println("What's the subject");
        String su = s.nextLine();
        System.out.println("Enter the body of the email");
        String bo = s.nextLine(); 
        Email email = new Email(t, c, bc, su, bo); 
        inbox.addEmail(email);

    }
    /**
     * removes a email from the folder and adds it to the trash folder
     * @param email
     * email to be removed 
     */
    public void deleteEmail(Email email){
        boolean foundEmail = false;
        for(int i = 0; i < getInbox().getEmails().size(); i++){
            if(email == getInbox().getEmails().get(i)){
                
                trash.addEmail(inbox.removeEmail(i)); 
                System.out.println("Email removed from inbox");
                foundEmail = true;
                return; 
            }
        }
        for(int h = 0; h < getTrash().getEmails().size(); h++){
            if(email == getTrash().getEmails().get(h)){
                foundEmail = true;
                System.out.println("Email is already in trash");
                return;
            }
        }
        if(foundEmail == false){
            for(int j = 0; j < folders.size(); j++){
                for(int k = 0; k < folders.get(j).getEmails().size(); k++){
                    if(email == folders.get(j).getEmails().get(k)){
                        
                        getTrash().addEmail(folders.get(j).removeEmail(k));
                        System.out.println("Email removed from " + folders.get(j).getName()); 
                        foundEmail = true;
                        return;
                    }
                }
            }
        }
        System.out.println("Could not find Email"); 
        
    }
    /**
     * removes all emails in the trash folder arraylist
     */
    public void clearTrash(){ 
        getTrash().getEmails().clear();
        System.out.println("trash has been cleared"); 
    }
    /**
     * finds a folder based on name
     * @param name
     *  name of the folder to be found 
     * @return
     *  returns the folder or null if it couldn't be found
     */
    public Folder getFolder(String name){
        if(name.equals("inbox")){
            return inbox;
        }
        else if(name.equals("trash")){
            return trash;
        }
        else {
            for(int i = 0; i < folders.size(); i++){
                if(name.equals(folders.get(i).getName())){

                    return folders.get(i);
                    
                }
            }
        }
        System.out.println("Folder not found"); 
        return null;
    }
    /**
     * moves an email to the target folder 
     * @param email
     * email to be moved 
     * @param target
     * folder the email is moved to 
     */
    public void moveEmail(Email email, Folder target){
        
        if(getFolder(target.getName())!= null){ //if the target is in the folders arraylist
            for(int j = 0; j < folders.size(); j++){ //if the email is in a folder in the folders arraylist
                for(int k = 0; k < folders.get(j).getEmails().size(); k++){
                    if(email == folders.get(j).getEmails().get(k)){
                        
                        getFolder(target.getName()).addEmail(folders.get(j).removeEmail(k));
                        System.out.println("Email moved");
                        return;
                    }
                }
                
            }
            for(int i = 0; i < getInbox().getEmails().size(); i++){ //if the email is in inbox
                if(email == getInbox().getEmails().get(i)){
                    
                    getFolder(target.getName()).addEmail(getInbox().removeEmail(i));
                    System.out.println("Email moved");
                    return;
                }
            }
            for(int i = 0; i < getTrash().getEmails().size(); i++){ //if the email is in trash
                if(email == getTrash().getEmails().get(i)){
                    
                    getFolder(target.getName()).addEmail(getTrash().removeEmail(i));
                    System.out.println("Email moved");
                    return;
                }
            }
        }
        else if(target.getName().equals("inbox")){  //if the target folder is inbox 
            for(int j = 0; j < folders.size(); j++){ //if the email is in user created folder 
                for(int k = 0; k < folders.get(j).getEmails().size(); k++){
                    if(email == folders.get(j).getEmails().get(k)){
                        
                        getInbox().addEmail(folders.get(j).removeEmail(k));
                        System.out.println("Email moved");
                        return;
                    }
                }
                
            }
            for(int i = 0; i < getInbox().getEmails().size(); i++){  //if the email is in inbox 
                if(email == getInbox().getEmails().get(i)){
                    
                    
                    System.out.println("Email already in inbox");
                    return;
                }
            }
            for(int i = 0; i < getTrash().getEmails().size(); i++){ //if the email is in trash
                if(email == getTrash().getEmails().get(i)){
                    
                    getInbox().addEmail(getTrash().removeEmail(i));
                    System.out.println("Email moved");
                    return;
                }
            }
        }
        else if(target.getName().equals("trash")){  //if the target folder is trash
            for(int j = 0; j < folders.size(); j++){ //if the email is in a user created folder 
                for(int k = 0; k < folders.get(j).getEmails().size(); k++){
                    if(email == folders.get(j).getEmails().get(k)){
                        
                        getTrash().addEmail(folders.get(j).removeEmail(k));
                        System.out.println("Email moved");
                        return;
                    }
                }
                
            }
            for(int i = 0; i < getInbox().getEmails().size(); i++){ //if the email is in inbox 
                if(email == getInbox().getEmails().get(i)){
                    
                    getTrash().addEmail(getInbox().removeEmail(i));
                    System.out.println("Email moved");
                    return;
                }
            }
            for(int i = 0; i < getTrash().getEmails().size(); i++){ //if the email is in trash folder 
                if(email == getTrash().getEmails().get(i)){
                    
                    
                    System.out.println("Email already in trash");
                    return;
                }
            }
        }
        else{ //incase the target folder not found, moves to inbox 
            System.out.println("target Folder not found");
            for(int j = 0; j < folders.size(); j++){
                for(int k = 0; k < folders.get(j).getEmails().size(); k++){
                    if(email == folders.get(j).getEmails().get(k)){
                        
                        getInbox().addEmail(folders.get(j).removeEmail(k));
                        System.out.println("Email moved;");
                        return;
                    }
                }
                
            }
        }
    }
}
