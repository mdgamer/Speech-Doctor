package com.example.speechdoctor;

public class MedicineGetSet {
    private String Name,Timing,Email,Pat_Name;

    public MedicineGetSet(){

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTiming() {
        return Timing;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPat_Name() {
        return Pat_Name;
    }

    public void setPat_Name(String pat_Name) {
        Pat_Name = pat_Name;
    }

    public void setTiming(String timing) {
        Timing = timing;
    }
}
