package com.businessapp.customer;

import com.businessapp.model.IndividualCustomer;
import org.junit.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class IndividualCustomerTest {

    private IndividualCustomer indicust;
    final String empty = "";

    @Before
    public void setUp() {
        indicust = new IndividualCustomer();
    }

    @Test
    public void testSetGetID() {
        //
    }

    @Test
    public void testSetGetCreated() {
        //
    }

    @Test
    public void testSetGetFirstName() {
        String first = "Peter";
        indicust.setFirstName(first);
        //assertTrue(indicust.getFirstName().equals(first));
        assertEquals(first, indicust.getFirstName());

        indicust.setFirstName(null);
        assertEquals(null, indicust.getFirstName());
    }

    @Test
    public void testSetGetName() {
        String last = "Peterson";
        indicust.setName(last);
        //assertTrue(indicust.getName().equals(last));
        assertEquals(last, indicust.getName());

        indicust.setName(null);
        assertEquals(null, indicust.getName());
    }
}