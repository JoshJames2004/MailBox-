/**
 * The Email class contains the parts of an email object that is the main data being manipulated in the program
 * implements Serializable
 *  so the obje can be saved as a .obj file
 * @author Joshua James
 * ID Number: 115113767
 * StonyBrook Email: joshua.james@stonybrook.edu
 * 
 * */

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Email implements Serializable{

    private String to; //address to send the email to
    private String cc; //address of people to cc the email to
    private String bcc; //address of people to bcc the email to
    private String subject; //subject of the email
    private String body; //body of the email
    private GregorianCalendar timeStamp = new GregorianCalendar(); //keeps track of when the email was made 
    /**
     * default constructor for email
     */
    public Email (){
        to = null;
        cc = null;
        bcc = null;
        subject = null;
        body = null;
        timeStamp.getTime();   
    }
    /**
     * gets user input cosntructor for email
     * @param t
     *  to
     * @param c
     *  cc
     * @param bc
     *  bcc
     * @param s
     *  subject
     * @param bo
     *  body 
     */
    public Email(String t, String c, String bc, String s, String bo){
        to = t;
        cc = c;
        bcc = bc;
        subject = s;
        body = bo;
        timeStamp.getTime(); 
    }
    /**
     * mutator for to
     * @param t
     *  input for to
     */
    public void setTo(String t){
        to = t;
    }
    /**
     * accessor for to
     * @returns 
     *  to value
     */
    public String getTo(){
        return to;
    }
    /**
     * mutator for cc
     * @param c
     *  input for cc
     */
    public void setCC(String c){
        cc = c;
    }
    /**
     * accessor for cc
     * @returns 
     *  value of cc
     */
    public String getCC(){
        return cc;
    }
    /**
     * mutator for bcc
     * @param b
     *  input for bcc
     */
    public void setBCC(String b){
        bcc = b;
    }
    /**
     * accessor for bcc
     * @returns 
     *  value of bcc 
     */
    public String getBCC(){
        return bcc;
    }
    /**
     * mutator for subject
     * @param s
     *  input for subject
     */
    public void setSub(String s){
        subject = s;
    }
    /**
     * accessor for subject
     * @returns 
     *  subject value
     */
    public String getSub(){
        return subject;
    }
    /**
     * mutator for body
     * @param bo
     *  input for bo
     */
    public void setBod(String bo){
        body = bo;
    }
    /**
     * accessor for body
     * @returns 
     *  body value
     */
    public String getBod(){
        return body;
    }
    /**
     * mutator for timeStamp
     * @param ts
     *  input for timeStamp
     */
    public void setTimeStamp(GregorianCalendar ts){
        timeStamp = ts;
    }
    /**
     * accessor for timeStamp
     * @returns 
     *  timeStamp value
     */
    public GregorianCalendar getTimeStamp(){
        return timeStamp;
    }
}
