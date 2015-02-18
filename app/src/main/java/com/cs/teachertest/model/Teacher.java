package com.cs.teachertest.model;

import com.google.gson.annotations.SerializedName;

public class Teacher {
    @SerializedName("Person_Details")
    public PersonDetails personDetails = new PersonDetails();

    @SerializedName("Certificate")
    public String certificate;

    @SerializedName("Employment_Date")
    public String employmentDate;

    @SerializedName("Status")
    public String status;

    @SerializedName("Updated_By")
    public String updatedBy;

    @SerializedName("Created_By")
    public String createdBy;

    @SerializedName("Classroom_Identifier")
    public int[] classroomId;
}
