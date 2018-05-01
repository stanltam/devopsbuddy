package com.devopsbuddy.web.domain.frontend;

import java.io.Serializable;

public class FeedbackPojo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;
    private String firstName;
    private String lastName;
    private String feedBack;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FeedbackPojo{");
        sb.append("email='").append(email).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", feedBack='").append(feedBack).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
