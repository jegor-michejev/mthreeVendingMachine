/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import a3.vendingmachine.InsufficientFundsException;
import a3.vendingmachine.ServiceLayer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Anne
 */
public class ServiceLayerTest {

    public ServiceLayerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // DISCLAIMER:
    // As insertedFunds is static, it is consistent between all instances of
    // Sevice layers. Due to this, the expected value should take into account
    // values already present in the Service layer's insertedFunds value
    /**
     * Test for ensuring the money inserted is represented as expected
     */
    @Test
    public void testInsertValue() {
        BigDecimal insert = new BigDecimal(1.05);
        ServiceLayer testLayer = new ServiceLayer();
        double testLeftOver = testLayer.getFunds().doubleValue();
        testLayer.insertFunds(insert);

        assertEquals(testLeftOver + 1.05, testLayer.getFunds().doubleValue(), 0.001);

    }

    /**
     * Test for ensuring that if a number is too specific, all 3rd+ digits after
     * the decimal point are ignored
     */
    @Test
    public void testInsertValueTooSpecific() {
        BigDecimal insert = new BigDecimal(1.0589647);
        ServiceLayer testLayer = new ServiceLayer();
        double testLeftOver = testLayer.getFunds().doubleValue();
        testLayer.insertFunds(insert);

        assertEquals(testLeftOver + 1.05, testLayer.getFunds().doubleValue(), 0.001);
    }

    /**
     * Check if the Service Layer can validate a simple transaction
     *
     * @throws InsufficientFundsException
     */
    @Test
    public void validateTransactionSuccess() throws InsufficientFundsException {
        BigDecimal insert = new BigDecimal(1.05);
        ServiceLayer testLayer = new ServiceLayer();
        testLayer.insertFunds(insert);
        boolean transactionStauts = testLayer.validateTransaction(new BigDecimal(0.01));

        assertTrue(transactionStauts);

    }// End of validateTransaction

    /**
     * Test to see if an unsuccessful transaction successfully throws an
     * exception
     */
    @Test
    public void validateTransactionFail() {
        ServiceLayer testLayer = new ServiceLayer();
        BigDecimal overpricedItem = testLayer.getFunds().add(BigDecimal.ONE);
        boolean transactionStauts = false;
        try {
            transactionStauts = testLayer.validateTransaction(overpricedItem);
        } catch (InsufficientFundsException e) {

        }
        assertFalse(transactionStauts);
    }
    
    
    /**
     * Test to see if the balance value is refreshed
     */
    @Test
    public void validateInsertedValueRefresh() throws InsufficientFundsException{
        ServiceLayer testLayer = new ServiceLayer();
        
        BigDecimal funds = new BigDecimal(1000.00);
        testLayer.insertFunds(funds);
        testLayer.calculateChange(BigDecimal.ZERO); 
        
        assertEquals(0, testLayer.getFunds().doubleValue(), 0.001);
    }
    
    
    

    /**
     * Test to see if the change is calculated correctly
     *
     * @throws a3.vendingmachine.InsufficientFundsException
     */
    @Test
    public void testCalculateChange() throws InsufficientFundsException {
        ServiceLayer testLayer = new ServiceLayer();
        testLayer.calculateChange(BigDecimal.ZERO); // Resets the value of coins inserted from other tests
        BigDecimal funds = new BigDecimal(2.00);
        testLayer.insertFunds(funds);

        int[] expectedChange = {0, 1, 1, 2, 0, 1, 2, 0}; // The coins expected from 1.99 change
        int[] actualChange = testLayer.calculateChange(new BigDecimal(0.01).setScale(2, RoundingMode.DOWN));


        assertArrayEquals(expectedChange, actualChange);

    }

}
