/**
 * Testcases for the Pay Station system.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
package paystation.domain;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class PayStationImplTest {

    PayStation ps;

    @Before
    public void setup() {
        ps = new PayStationImpl();
        ps.initMap();
    }

    /**
     * Entering 5 cents should make the display report 2 minutes parking time.
     */
    @Test
    public void shouldDisplay2MinFor5Cents()
            throws IllegalCoinException {
        ps.addPayment(5);
        assertEquals("Should display 2 min for 5 cents",
                2, ps.readDisplay());
    }

    /**
     * Entering 25 cents should make the display report 10 minutes parking time.
     */
    @Test
    public void shouldDisplay10MinFor25Cents() throws IllegalCoinException {
        ps.addPayment(25);
        assertEquals("Should display 10 min for 25 cents",
                10, ps.readDisplay());
    }

    /**
     * Verify that illegal coin values are rejected.
     */
    @Test(expected = IllegalCoinException.class)
    public void shouldRejectIllegalCoin() throws IllegalCoinException {
        ps.addPayment(17);
    }

    /**
     * Entering 10 and 25 cents should be valid and return 14 minutes parking
     */
    @Test
    public void shouldDisplay14MinFor10And25Cents()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(25);
        assertEquals("Should display 14 min for 10+25 cents",
                14, ps.readDisplay());
    }

    /**
     * Buy should return a valid receipt of the proper amount of parking time
     */
    @Test
    public void shouldReturnCorrectReceiptWhenBuy()
            throws IllegalCoinException {
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        Receipt receipt;
        receipt = ps.buy();
        assertNotNull("Receipt reference cannot be null",
                receipt);
        assertEquals("Receipt value must be 16 min.",
                16, receipt.value());
    }

    /**
     * Buy for 100 cents and verify the receipt
     */
    @Test
    public void shouldReturnReceiptWhenBuy100c()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(10);
        ps.addPayment(25);
        ps.addPayment(25);

        Receipt receipt;
        receipt = ps.buy();
        assertEquals(40, receipt.value());
    }

    /**
     * Verify that the pay station is cleared after a buy scenario
     */
    @Test
    public void shouldClearAfterBuy()
            throws IllegalCoinException {
        ps.addPayment(25);
        ps.buy(); // I do not care about the result
        // verify that the display reads 0
        assertEquals("Display should have been cleared",
                0, ps.readDisplay());
        // verify that a following buy scenario behaves properly
        ps.addPayment(10);
        ps.addPayment(25);
        assertEquals("Next add payment should display correct time",
                14, ps.readDisplay());
        Receipt r = ps.buy();
        assertEquals("Next buy should return valid receipt",
                14, r.value());
        assertEquals("Again, display should be cleared",
                0, ps.readDisplay());
    }

    /**
     * Verify that cancel clears the pay station
     *
    @Test
    public void shouldClearAfterCancel()
            throws IllegalCoinException {
        ps.addPayment(10);
        ps.cancel();
        assertEquals("Cancel should clear display",
                0, ps.readDisplay());
        ps.addPayment(25);
        assertEquals("Insert after cancel should work",
                10, ps.readDisplay());
    }
    * /
    
    /**
     * Verify that empty returns the amount inserted so far
     */
    @Test
    public void shouldReturnAmount() throws Exception{
        ps.addPayment(5);
        assertEquals("Empty should return amount inserted", 5, ps.empty());
    }
    
    /**
     * Verify that cancel does not effect empty
     */
    @Test
    public void shouldNotChangeEmpty() throws Exception{
        ps.addPayment(5);
        ps.cancel();
        assertEquals("Cancel should not change return value of empty", 5, ps.empty());
    }
    
    /**
     * Verify empty resets the total to 0
     */
    @Test
    public void shouldResetTotal() throws Exception{
        ps.addPayment(5);
        ps.empty();
        assertEquals("Empty should reset the total to 0", 0, ps.empty());
    }
    
    /**
     * (*THIS TEST IS OBSOLETE*)
     * Test that cancel returns a map containing one coin entered
     *
    @Test
    public void shouldReturnOneCoin() throws Exception{
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        Map<Integer, Integer> test = new HashMap<Integer, Integer>();
        test.put(5, 1);
        assertEquals("Cancel should return a map with one coin used", test, ps.cancel());
    }
    */
    
    /**
     * (*THIS TEST IS OBSOLETE*)
     * Test that cancel can return a map of a mix of coins used
     *
    public void shouldReturnMix() throws Exception{
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        Map<Integer, Integer> test = new HashMap<Integer, Integer>();
        test.put(5, 1);
        test.put(10, 1);
        test.put(25, 1);
        assertEquals("Cancel should return a map with one coin used", test, ps.cancel());
    }
    */
    
    /**
     * Test that cancel does not return a map containing a key for a coin not used
     */
    public void shouldNotReturnUnused() throws Exception{
        ps.addPayment(5);
        ps.addPayment(10);
        Map<Integer, Integer> test = new HashMap<Integer, Integer>();
        test.put(5, 1);
        test.put(10, 1);
        test.put(25, 0);
        assertEquals("Cancel should return a map with one coin used", test, ps.cancel());        
    }
    
    /**
     * Verify map is cleared after cancel
     */
    @Test
    public void shouldClearMapCancel() throws Exception{
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        Map<Integer, Integer> test = new HashMap<Integer, Integer>();
        test.put(5,0);
        test.put(10,0);
        test.put(25,0);
        assertEquals("Cancel should reset the map", test, ps.cancel());
    }
    
    /**
     * Verify that buy clears the map
     */
    public void shouldClearMapBuy() throws Exception{
        ps.addPayment(5);
        ps.addPayment(10);
        ps.addPayment(25);
        Map<Integer, Integer> test = new HashMap<Integer, Integer>();
        test.put(5,0);
        test.put(10,0);
        test.put(25,0);
        ps.buy();
        assertEquals("Buy should clear the map", test, ps.getCoins());
    }
}
