package com.sta.buswayapp.model;

public class GuestData {

    String guestName, guestPhoneNumber, guestCompanyName, guestCompanyProjectName, guestPosition;

    public GuestData(String guestName, String guestPhoneNumber, String guestCompanyName, String guestCompanyProjectName, String guestPosition) {
        this.guestName = guestName;
        this.guestPhoneNumber = guestPhoneNumber;
        this.guestCompanyName = guestCompanyName;
        this.guestCompanyProjectName = guestCompanyProjectName;
        this.guestPosition = guestPosition;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestPhoneNumber() {
        return guestPhoneNumber;
    }

    public void setGuestPhoneNumber(String guestPhoneNumber) {
        this.guestPhoneNumber = guestPhoneNumber;
    }

    public String getGuestCompanyName() {
        return guestCompanyName;
    }

    public void setGuestCompanyName(String guestCompanyName) {
        this.guestCompanyName = guestCompanyName;
    }

    public String getGuestCompanyProjectName() {
        return guestCompanyProjectName;
    }

    public void setGuestCompanyProjectName(String guestCompanyProjectName) {
        this.guestCompanyProjectName = guestCompanyProjectName;
    }

    public String getGuestPosition() {
        return guestPosition;
    }

    public void setGuestPosition(String guestPosition) {
        this.guestPosition = guestPosition;
    }
}
