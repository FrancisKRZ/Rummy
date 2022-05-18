// import Interfaces.*;
// import java.util.*;

// Based on this resource:
// 104.131.54.192:8080/

public class MyStack implements Stack{
    
    private Card stackArray[];
    private static final int DEFAULT_SIZE = 54;
    private int maxSize;
    private int top;        // First free position at top


    // Constructors

    // Create a Stack given a custom size
    MyStack(int size){
        maxSize = size;
        top = 0;
        stackArray = new Card[size];
    }

    // Create a Stack with DEFAULT_SIZE 54
    MyStack(){
        this(DEFAULT_SIZE);
    }


    // May be of use, converts obj to MyStack
    public void convertToStack(Object obj){
        MyStack s = (MyStack) obj;
        obj = s;
    }

    // Sets the top [] index to 0
    public void clear(){
        top = 0;
    }

    // Adds Card object to array
    public boolean addCard(Object it){
        
        if (top >= maxSize){
            return false;
        }

        stackArray[top++] = (Card) it;
        return true;
    }

    // This removes the card last insorted from the stack
    public Card removeCard(){

        if (top == 0){
            return null;
        }
        return stackArray[--top];
    }

    // This 'previews' the card at the top without removing
    public Card peek(){
        
        if (top == 0){
            return null;
        }
        return stackArray[top-1];
    }

    // This returns the length of the stack (how many cards)
    public int length(){
        return top;
    }

    // This tells us if the stack is empty (no cards)
    public boolean isEmpty(){
        return (top == 0);
    }



    // This is a simple helper function
    // used whilst creating the Stack
    // Not to be used outside testing
    // public static void printAll(Collection c){

    //     Iterator i = c.iterator();
    //     while (i.hasNext()){
    //         System.out.println(i.next());
    //     }

    // }

}