package uk.ac.aber.dcs.cob16.cs21120.assignment1.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
//import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

// specify test classes
@Suite.SuiteClasses({
	TestStack.class,
	TestQueue.class,
	DoubleEliminationTest.class,
	SingleEliminationTest.class
	}
)

// the actual class is empty
public class AllTests {

}
