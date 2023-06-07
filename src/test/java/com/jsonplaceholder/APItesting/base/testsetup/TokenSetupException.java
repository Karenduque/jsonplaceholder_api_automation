package com.jsonplaceholder.APItesting.base.testsetup;

public class TokenSetupException  extends Exception {
    public TokenSetupException() {
        super("Unable to setup a token before executing the test.");
    }
}
