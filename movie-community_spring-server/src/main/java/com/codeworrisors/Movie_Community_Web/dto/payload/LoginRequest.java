package com.codeworrisors.Movie_Community_Web.dto.payload;


/**
 * Created by rajeevkumarsingh on 02/08/17.
 */
public class LoginRequest {
    private String memberName;

    private String password;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
