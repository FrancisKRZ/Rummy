
// ADT: Stack
public interface Stack{

    // Reinitialize the stack
    public void clear();

    // Push "it" to the top of the stack
    public boolean addCard(Object it);

    // Remove and return element at the top of the stack
    public Card removeCard();

    // Return a copy of the top element
    public Card peek();

    // Return the number of elements in the stack
    public int length();

    // Return true if stack is empty
    public boolean isEmpty();
}