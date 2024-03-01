package cs410.uno;

import java.util.Optional;

/* Represents the face of an Uno Card. The face can be either a digit or an instruction. Can return the digit and
   the instruction in an Option object. It is used by NormalCard.
   Example:
   Face = 9 or Face = "Skip"
 */
public class Face {
    // The digit or the instruction to be returned. One of them will be empty.
    private final Optional<Integer> digit;
    private final Optional<String> instruction;

    // The digit face.
    public Face(int digit) {
        this.digit = Optional.of(digit);
        this.instruction = Optional.empty();
    }

    // The instruction face.
    public Face(String instruction) {
        this.instruction = Optional.of(instruction);
        this.digit = Optional.empty();
    }

    // Returns an Optional object with the digit inside.
    public Optional<Integer> digit() {
        return this.digit;
    }

    // Returns an Optional object with the instruction inside.
    public Optional<String> instruction() {
        return this.instruction;
    }

    // Equals method used to check if two Face objects are equal.
    @Override
    public boolean equals(Object other) {
        // Object is compared with itself
        if (other == this) {
            return true;
        }
        // Check if the Object is not an instance of Face class.
        if (!(other instanceof Face)) {
            return false;
        }
        // Cast the object to Face type.
        Face c = (Face) other;
        // Check if the digits and the instructions are the same.
        if ((this.digit.equals(c.digit)) && (this.instruction.equals(c.instruction))) {
            return true;
        }
        return false;
    }


}
