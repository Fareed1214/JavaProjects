package com.example.demo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    LibraryServiceTest.class,
    LibraryRepositoryTest.class
})
public class LibraryApplicationTests {

}
