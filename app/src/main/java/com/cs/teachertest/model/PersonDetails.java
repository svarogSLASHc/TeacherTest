package com.cs.teachertest.model;

import com.google.gson.annotations.SerializedName;

public class PersonDetails {

    @SerializedName("Frist_Name")
    public String firstName;

    @SerializedName("Middle_Name")
    public String middleName;

    @SerializedName("Last_Name")
    public String lastName;

    @SerializedName("Mobile_Phone_Number_1")
    public String mobile_1;

    @SerializedName("Mobile_Phone_Number_2")
    public String mobile_2;

    @SerializedName("Mobile_Phone_Number_3")
    public String mobile_3;

    @SerializedName("Land_Phone_Number")
    public String landPhone;

    @SerializedName("Email_Address_1")
    public String email_1;

    @SerializedName("Email_Address_2")
    public String email_2;

    @SerializedName("Email_Address_3")
    public String email_3;

    @SerializedName("Gender")
    public String gender;

    @SerializedName("Date_Of_Birth")
    public String dateBirth;
}
