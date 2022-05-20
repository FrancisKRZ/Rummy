<!--
    Francisco J. Melendez Laureano
    CCOM-4029 : Java Project 2 (Rummy Card Game)
    Professor: Patricia Ordonez
-->



Documenting All Sources, Helped Received (if any) and Design Choices.

The project is organized in two separate directories, Interfaces and Sources...

    How to compile & run:
        javac -d . Proj2.java
        java Proj2

    or simply:   javac -d . Proj2.java  &&  java Proj2


How it works:
User will be asked to provide the number of virtual player(s)
It shall run the Table with the appropiate amount of virtualized players.
The Virtual Players follow a basic algorithm:
* Pick up a card from either deck or stack
* Lay down a card in either the deck or stack

If the deck or hand is empty, it shall evaluate the hands,
winner is chosen by player who's hand is lesser in rank value than the opponent's.



    Guides used:

    - Java Util Package Documentation:
    https://docs.oracle.com/javase/8/docs/api/java/util/package-summary.html

    - MyStack ADT Based on:
    OpenDSA Data Structures and Algorithms Modules Collection
    http://104.131.54.192:8080/Books/Everything/html/index.html#


    - Default List Model Guide:
    https://docs.oracle.com/en/java/javase/13/docs/api/java.desktop/javax/swing/DefaultListModel.html


    - JList from Javax Swing
    https://docs.oracle.com/en/java/javase/13/docs/api/java.desktop/javax/swing/class-use/JList.html



Design Choices:

- Behavioral / Observer:
Call to Main class creates Table, which calls upon the other Classes,
utilizes Java Utility ADTs (List) and its methods. Reflects changes cross object-wise.


    Main Class
        -> Table Class
            -> Deck , Hand , Set ( Set utilizes MyStack, 
                                Deck & Hand & Set utilizes Java ADTs
                                and javax swing and other utilities )



Help Provided to Team:
    - Helped on advising how to and what to implement certain methods to the team.
    - Provided a pseudocode / blueprint on how to manage the sorting(s) to the team.
    - Helped Nicole on how to implement Stack / MyStack to work with other classes.
    - Provided tip(s) to Orlando how to organize the sources & interfaces.

Help Provided by Team:
    - Nicole helped me understand how the buttons work / interact.
    - Nicole provided tips on polishing the Table code and interactions with virtual players.
    - Nicole gave me moral support by being cheerful.