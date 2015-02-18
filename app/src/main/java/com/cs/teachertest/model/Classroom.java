package com.cs.teachertest.model;

import com.google.gson.annotations.SerializedName;

public class Classroom {

    @SerializedName("Classroom_Name")
    public String name;

    @SerializedName("Classroom_Identifier")
    public String identifier;
}
