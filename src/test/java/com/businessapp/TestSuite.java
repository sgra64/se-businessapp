package com.businessapp;

import com.businessapp.model.IndividualCustomer;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses({
        IndividualCustomer.class,
        AppTest.class
})

public class TestSuite {
}