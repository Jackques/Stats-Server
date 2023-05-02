package org.statsserver.domain;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetvsListTest {

    @Test
    public void givenList_whenDuplicates_thenAllowed(){
        List<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        integerList.add(3);
        integerList.add(4);
        integerList.add(4);
        assertEquals(integerList.size(), 4);
    }

    @Test
    public void givenSet_whenDuplicates_thenNotAllowed(){
        Set<Integer> integerSet = new HashSet<>();
        integerSet.add(2);
        integerSet.add(3);
        integerSet.add(4);
        integerSet.add(4);
        assertEquals(integerSet.size(), 3);
    }

    @Test
    public void givenSet_whenOrdering_thenMayBeAllowed(){
        Set<Integer> set1 = new LinkedHashSet<>();
        set1.add(2);
        set1.add(3);
        set1.add(4);
        Set<Integer> set2 = new LinkedHashSet<>();
        set2.add(2);
        set2.add(3);
        set2.add(4);
        assertArrayEquals(set1.toArray(), set2.toArray());
    }

    @Test
    public void canIAccessListWithinForeachOnThatList(){
        List<String> mylist = new ArrayList<>();
        mylist.add("one");
        mylist.add("two");
        mylist.add("three");

        Throwable raisedException = catchThrowable(() ->
                mylist.forEach((mylistItem)->{
                    System.out.println("myListItem is: "+mylistItem);
                    System.out.println(mylist); // but yes i can access mylist from within this foreach!
                    if(mylistItem.equals("two")){
                        mylist.add("four"); // answer: nope.. throw currentModificationException!
                    }
                })
        );

        // Assert
        assertThat(raisedException).isInstanceOf(ConcurrentModificationException.class);
    }
}
