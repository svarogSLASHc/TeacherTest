package com.cs.teachertest.rest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.cs.teachertest.AppConstant;
import com.cs.teachertest.model.Classroom;
import com.cs.teachertest.model.Teacher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class RestRequest {
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static Gson mGson = new Gson();

    private static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

    public static void getClassrooms(Context context, final IResponseCallback callback) {
        if (!isNetworkConnected(context)) {
            callback.OnFailure("No internet connection!");
        }
        client.setBasicAuth(AppConstant.USER_NAME, AppConstant.USER_PASSWORD);
        client.get(AppConstant.BASE_URL + AppConstant.GET_CLASSROOM,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        super.onSuccess(statusCode, headers, response);
                        Type collectionType = new TypeToken<ArrayList<Classroom>>() {
                        }.getType();
                        ArrayList<Integer> classrooms = mGson.fromJson(response.toString(), collectionType);
                        callback.OnSuccess(classrooms);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        callback.OnFailure(null);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        callback.OnFailure(null);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        callback.OnFailure(null);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        super.onSuccess(statusCode, headers, responseString);
                    }
                });

    }

    public static void postNewTeacher(Context context, Teacher teacher, final IResponseCallback callback) {
        if (!isNetworkConnected(context)) {
            callback.OnFailure("No internet connection!");
        }
        try {
            client.addHeader("Content-Type", AppConstant.CONTENT_TYPE);
            client.addHeader("x-location", AppConstant.X_LOCATION);
            client.addHeader("x-device-info", AppConstant.X_DEVICE_INFO);
            StringEntity entity = new StringEntity(mGson.toJson(teacher), "UTF-8");
            client.post(context,
                    AppConstant.BASE_URL + AppConstant.POST_TEACHER,
                    entity,
                    "application/json",
                    new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            callback.OnSuccess("Success");
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            super.onSuccess(statusCode, headers, response);
                            callback.OnSuccess("Success");
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            callback.OnFailure(null);

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            callback.OnFailure(null);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            callback.OnFailure(null);
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            super.onSuccess(statusCode, headers, responseString);
                            callback.OnSuccess("Success");
                        }
                    });

        } catch (UnsupportedEncodingException e) {
            callback.OnFailure(e.getMessage());
        }
    }
}
