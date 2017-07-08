package com.example.project;

import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.WebDriver;

public class EndOfTests {

    private WebDriver wd;

    public EndOfTests(WebDriver wd) {
        this.wd = wd;
    }

    @AfterAll
    public void testsDone() {
        wd.quit();
    }

}
