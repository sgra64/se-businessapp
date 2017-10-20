package com.businessapp.customer;

import com.businessapp.logic.LogicFactory;
import com.businessapp.model.Customer;
import com.businessapp.model.IndividualCustomer;
import org.junit.*;

import java.util.Date;

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
        String id = LogicFactory.getBusinessLogic().nextId(Customer.class);
        indicust.setId(id);
        assertEquals(id, indicust.getId());

        indicust.setId(empty);
        assertEquals(empty, indicust.getId());

        indicust.setId(null);
        assertEquals(null, indicust.getId());
    }

    @Test
    public void testSetGetCreated() {
        Date created = new Date();
        indicust.setCreated(created);
        assertEquals(created, indicust.getCreated());

        //indicust.setCreated(empty);
        //assertEquals(empty, indicust.getCreated());

        indicust.setCreated(null);
        assertEquals(null, indicust.getCreated());
    }

    @Test
    public void testSetGetFirstName() {
        String first = "Peter";
        indicust.setFirstName(first);
        assertEquals(first, indicust.getFirstName());

        indicust.setFirstName(empty);
        assertEquals(empty, indicust.getFirstName());

        indicust.setFirstName(null);
        assertEquals(null, indicust.getFirstName());
    }

    @Test
    public void testSetGetName() {
        String last = "Peterson";
        indicust.setName(last);
        assertEquals(last, indicust.getName());

        indicust.setName(empty);
        assertEquals(empty, indicust.getName());

        indicust.setName(null);
        assertEquals(null, indicust.getName());
    }
}