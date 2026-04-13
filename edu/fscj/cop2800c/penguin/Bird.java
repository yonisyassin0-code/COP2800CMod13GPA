// Bird.java
// D. Singletary
// 3/15/25
// Represents a bird base class

package edu.fscj.cop2800c.penguin;

// Bird class implements the Comparable<Bird> interface
public class Bird implements Comparable<Bird> {
    // private fields
    private int sampleNum;
    private double culmenLength;
    private double culmenDepth;
    private double bodyMass;
    private String sex;

    // Constructor
    public Bird(int sampleNum, double culmenLength, 
            double culmenDepth, double bodyMass, String sex)
            throws InvalidBirdDataException {
        // validate data
        if (sampleNum < 0 || 
                culmenLength < 0 || culmenDepth < 0 || 
                bodyMass < 0 || sex == null || sex.isEmpty()) {
            throw new InvalidBirdDataException(
                    "Invalid Bird data encountered.");
        }
        this.sampleNum = sampleNum;
        this.culmenLength = culmenLength;
        this.culmenDepth = culmenDepth;
        this.bodyMass = bodyMass;
        this.sex = sex;
    }

    // Getter methods
    public int getSampleNum() {
        return sampleNum;
    }

    public double getCulmenLength() {
        return culmenLength;
    }

    public double getCulmenDepth() {
        return culmenDepth;
    }

    public double getBodyMass() {
        return bodyMass;
    }

    public String getSex() {
        return sex;
    }

    // base class toString (override)
    @Override
    public String toString() {
        String retStr = 
           "sampleNum=" + sampleNum +
           ", culmenLength=" + culmenLength +
           ", culmenDepth=" + culmenDepth +
           ", bodyMass=" + bodyMass +
           ", sex='" + sex;
        return retStr;
    }
    
    // base class compareTo (override)
    @Override
    public int compareTo(Bird other) {
        int result = Integer.compare(this.sampleNum, other.sampleNum);
        if (result != 0) return result;

        result = Double.compare(this.culmenLength, other.culmenLength);
        if (result != 0) return result;

        result = Double.compare(this.culmenDepth, other.culmenDepth);
        if (result != 0) return result;

        result = Double.compare(this.bodyMass, other.bodyMass);
        if (result != 0) return result;

        return this.sex.compareTo(other.sex);
    }
}