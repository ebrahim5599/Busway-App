package com.sta.buswayapp.model;

public class GuestData {

    String name, phoneNumber, company, projectName, position;

    public GuestData(String guestName, String guestPhoneNumber, String guestCompanyName, String guestCompanyProjectName, String guestPosition) {
        this.name = guestName;
        this.phoneNumber = guestPhoneNumber;
        this.company = guestCompanyName;
        this.projectName = guestCompanyProjectName;
        this.position = guestPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
