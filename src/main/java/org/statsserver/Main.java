package org.statsserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        /*
        ! what about system-no (Maps)?
            how will these inner values (which can be lists, maps?, string, numbers, dates etc.) be stored? i.e. system-no?
            > In InnerValuesMap, in which each value is treated as a new keyData object. inner values like lists or maps however are not allowed.
            what if the object(map -> list) contains a list with strings, which needs to be updated with every record?
            > not allowed (KISS = KEEP IT SUPER SIMPLE)
        ! What about lists with objects (i.e. response-speed)?
            will (the contents of) these objects be stored in innerValuesList?
            > should also be stored in innerValuesMap, each individual key-value that is. Inner lists or maps cannot exist in this structure (a.k.a. not allowed)

         ! What about lists within object-lists!? [map->list, map, map]
            > NOT ALLOWED
         ! What about lists within object-objects? { {myListProp: [],} {...}}
            > object->object is not allowed
         ! What about lists within lists? [[a,b,c],[d,e,f]]
            > NOT ALLOWED
         ! What about lists within objects? {myListProp: []}
            > NOT ALLOWED

            !!! Check if not-allowed rules above are actually enforced with a clear error message
         */

        System.out.println("Note: Main application class (in this case called Main) should always be at parentfolder of other java files (because Springs starts looking for Java annotations from this folder)");

        SpringApplication.run(Main.class, args);
    }

}