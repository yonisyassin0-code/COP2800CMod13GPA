// InvalidBirdDataException.java
// D. Singletary
// 3/29/25
// Custom exception for Palmer Penguin data

package edu.fscj.cop2800c.penguin;

public class InvalidBirdDataException extends Exception {
    public InvalidBirdDataException(String message) {
        super(message);
    }
}
