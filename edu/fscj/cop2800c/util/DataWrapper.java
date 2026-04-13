// DataWrapper.java
// D. Singletary
// 3/14/25
// Generic data wrapper class
 
package edu.fscj.cop2800c.util;

import java.util.List;

public class DataWrapper<T> {
    private T value;

    public DataWrapper(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

//     @Override
//     public String toString() {
//         return "DataWrapper{" +
//                 "value=" + value +
//                 '}';
//     }
    
    public static <T> void displayList(List<DataWrapper<T>> list) {
        for (DataWrapper<T> element : list) {
           // getValue drills down to the object's toString
            System.out.println(element.getValue()); 
        }
    }
}
