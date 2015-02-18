package com.cs.teachertest.rest;

public interface IResponseCallback {
    public void OnSuccess(Object object);
    public void OnFailure(Object object);
}
