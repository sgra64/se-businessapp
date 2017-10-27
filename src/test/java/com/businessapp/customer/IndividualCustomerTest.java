package com.businessapp.customer;

import com.businessapp.logic.LogicFactory;
import com.businessapp.model.Customer;
import com.businessapp.model.IndividualCustomer;
import org.junit.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Date;

public class IndividualCustomerTest {

    private IndividualCustomer indicust;
    private final String empty = "";
    private String idGen;

    @Before
    public void setUp() {
        indicust = new IndividualCustomer();
        idGen = LogicFactory.getBusinessLogic().nextId(Customer.class);
    }

    @Test
    public void testSetGetID() {
        String idA = idGen;
        indicust.setId(idA);
        assertEquals(idA, indicust.getId());
        assertThat(idA == indicust.getId(), is(true));

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

     /*
     Was ist der Unterschied zwischen den assert‐Statements in folgendem Code:
     String s1 = "ABC";
     String s2 = new String( "ABC" );
     assertEquals( s1, s2 );
     assertThat( s1==s2, is( true ) );

     Antwort: assertEquals fragt ab, ob die Objekte gleich (von ihren Werten her) sind
     assertThat s1==s2 fragt ab, ob die Objekte die selben Objekte sind und nicht nur gleichartig mit gleichen Werten sind
     */
}