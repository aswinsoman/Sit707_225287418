package sit707_week6;

/**
 * Part B — Two functions demonstrating:
 *  (a) a conditional loop with simple statements in the body
 *  (b) a conditional loop with a conditional statement in the body
 */
public class LoopUtils {

    /**
     * Part B (a) — Conditional loop with SIMPLE statements in the body.
     *
     * Computes the sum of all integers from 1 to n (inclusive).
     * The loop runs while the counter has not exceeded n.
     *
     * @param n upper bound (must be >= 0)
     * @return sum 1+2+...+n  (0 if n == 0)
     * @throws IllegalArgumentException if n is negative
     */
    public static int sumUpTo(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be non-negative");
        }
        int sum     = 0;
        int counter = 1;
        // Conditional loop — simple body: just add and increment
        while (counter <= n) {
            sum = sum + counter;   // simple statement
            counter++;             // simple statement
        }
        return sum;
    }

    /**
     * Part B (b) — Conditional loop with a CONDITIONAL STATEMENT in the body.
     *
     * Counts how many integers from 1 to n are even.
     * Inside the loop there is an if-statement (the conditional statement).
     *
     * @param n upper bound (must be >= 0)
     * @return count of even numbers in [1..n]
     * @throws IllegalArgumentException if n is negative
     */
    public static int countEvens(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be non-negative");
        }
        int count   = 0;
        int counter = 1;
        // Conditional loop — body contains a conditional statement (if)
        while (counter <= n) {
            if (counter % 2 == 0) {   // conditional statement inside loop
                count++;
            }
            counter++;
        }
        return count;
    }
}