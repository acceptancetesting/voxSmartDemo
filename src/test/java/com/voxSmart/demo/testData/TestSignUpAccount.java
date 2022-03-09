package com.voxSmart.demo.testData;

import lombok.Getter;

@Getter
public class TestSignUpAccount {
    private String firstName;
    private String lastName;
    private String emailId;
    private String phoneNumber;

    public TestSignUpAccount(String firstName,String lastName,String emailId,String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
    }

    public TestSignUpAccount getTestSignUpAccount() {
        return this;
    }

}
