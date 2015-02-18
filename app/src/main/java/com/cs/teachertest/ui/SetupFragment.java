package com.cs.teachertest.ui;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cs.teachertest.R;
import com.cs.teachertest.model.Classroom;
import com.cs.teachertest.model.Teacher;
import com.cs.teachertest.rest.IResponseCallback;
import com.cs.teachertest.rest.RestRequest;

import java.util.ArrayList;

public class SetupFragment extends Fragment {
    private ArrayList<Classroom> mClassrooms;
    private ImageView loaderImage;
    private Teacher mTeacher = new Teacher();
    private EditText firstNameET, middleNameET, lastNameET, mobileNumbET, emailET,
            dateBirthET, certificateET, employmentDateET, mobileNumb2ET, mobileNumb3ET,
            landNumbET, email2ET, email3ET, statusET, updatedByET, createdByET;
    private Spinner genderSpinner, classroomSpinner;
    private Context mContext;
    private Animation mRotateAnim;

    private View.OnClickListener mSubmitClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            validateFields();
        }
    };

    private View.OnClickListener mShowDatePicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerFragment newFragment = new DatePickerFragment();
            switch (v.getId()){
                case R.id.chooseBirthDate_ImageView_SetupFragment:
                    newFragment.setEditTextToDisplay(dateBirthET);
                    break;
                case R.id.chooseEmploymentDate_ImageView_SetupFragment:
                    newFragment.setEditTextToDisplay(employmentDateET);
                    break;
            }
            newFragment.show(getChildFragmentManager(), null);
        }
    };

    public boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setup, container, false);
        mContext = getActivity();
        initUI(rootView);
        getClassrooms();
        return rootView;
    }

    private void initUI(View root) {
        mRotateAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.progress_anim);
        loaderImage = (ImageView) root.findViewById(R.id.loader_ImageView_MainActivity);
        loaderImage.setVisibility(View.VISIBLE);
        loaderImage.setAnimation(mRotateAnim);

        root.findViewById(R.id.submit_Button_SetupFragment).setOnClickListener(mSubmitClick);
        firstNameET = (EditText) root.findViewById(R.id.firstName_EditText_SetupFragment);
        middleNameET = (EditText) root.findViewById(R.id.middleName_EditText_SetupFragment);
        lastNameET = (EditText) root.findViewById(R.id.lastName_EditText_SetupFragment);
        mobileNumbET = (EditText) root.findViewById(R.id.mobile_EditText_SetupFragment);
        emailET = (EditText) root.findViewById(R.id.email_EditText_SetupFragment);
        dateBirthET = (EditText) root.findViewById(R.id.birthDate_EditText_SetupFragment);
        certificateET = (EditText) root.findViewById(R.id.certificste_EditText_SetupFragment);
        employmentDateET = (EditText) root.findViewById(R.id.employmentDate_EditText_SetupFragment);
        mobileNumb2ET = (EditText) root.findViewById(R.id.mob2_EditText_SetupFragment);
        mobileNumb3ET = (EditText) root.findViewById(R.id.mob3_EditText_SetupFragment);
        landNumbET = (EditText) root.findViewById(R.id.landPhone_EditText_SetupFragment);
        email2ET = (EditText) root.findViewById(R.id.email2_EditText_SetupFragment);
        email3ET = (EditText) root.findViewById(R.id.email3_EditText_SetupFragment);
        statusET = (EditText) root.findViewById(R.id.status_EditText_SetupFragment);
        updatedByET = (EditText) root.findViewById(R.id.updatedBy_EditText_SetupFragment);
        createdByET = (EditText) root.findViewById(R.id.createdBy_EditText_SetupFragment);

        root.findViewById(R.id.chooseBirthDate_ImageView_SetupFragment).setOnClickListener(mShowDatePicker);
        root.findViewById(R.id.chooseEmploymentDate_ImageView_SetupFragment).setOnClickListener(mShowDatePicker);;

        genderSpinner = (Spinner) root.findViewById(R.id.gender_Spinner_SetupFragment);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                R.layout.spinner_item,
                mContext.getResources().getStringArray(R.array.gender));
        adapter.setDropDownViewResource(R.layout.spinner_item);
        genderSpinner.setAdapter(adapter);
        classroomSpinner = (Spinner) root.findViewById(R.id.classroom_Spinner_SetupFragment);
    }

    private void getClassrooms() {
        RestRequest.getClassrooms(getActivity(), new IResponseCallback() {
            @Override
            public void OnSuccess(Object object) {
                Log.d("cs_c", object.toString());
                loaderImage.clearAnimation();
                loaderImage.setVisibility(View.GONE);
                mClassrooms = (ArrayList<Classroom>) object;
                ArrayList<String> data = new ArrayList<String>();
                for(Classroom item: mClassrooms){
                    data.add(item.name);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                        R.layout.spinner_item,
                        data);
                adapter.setDropDownViewResource(R.layout.spinner_item);
                classroomSpinner.setAdapter(adapter);
            }

            @Override
            public void OnFailure(Object object) {
                if (object != null) {
                    Toast.makeText(getActivity(), object.toString(), Toast.LENGTH_SHORT).show();
                }
                loaderImage.clearAnimation();
                loaderImage.setVisibility(View.GONE);
            }
        });
    }

    private void validateFields() {

        if (firstNameET.getText().toString().trim().isEmpty()){
            Toast.makeText(mContext, "Please enter First Name", Toast.LENGTH_SHORT).show();
            return;
        }
        mTeacher.personDetails.firstName = firstNameET.getText().toString();

        if (middleNameET.getText().toString().trim().isEmpty()){
            Toast.makeText(mContext, "Please enter Middle Name", Toast.LENGTH_SHORT).show();
            return;
        }
        mTeacher.personDetails.middleName = middleNameET.getText().toString();

        if (lastNameET.getText().toString().trim().isEmpty()){
            Toast.makeText(mContext, "Please enter Last Name", Toast.LENGTH_SHORT).show();
            return;
        }
        mTeacher.personDetails.lastName = lastNameET.getText().toString();

        if (mobileNumbET.getText().toString().trim().isEmpty()){
            Toast.makeText(mContext, "Please enter Mobile number", Toast.LENGTH_SHORT).show();
            return;
        }
        mTeacher.personDetails.mobile_1 = mobileNumbET.getText().toString();

        if (emailET.getText().toString().trim().isEmpty()){
            Toast.makeText(mContext, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (! isValidEmail(emailET.getText())){
            Toast.makeText(mContext, "Please enter valid email", Toast.LENGTH_SHORT).show();
            return;
        }
        mTeacher.personDetails.email_1 = emailET.getText().toString();

        if (certificateET.getText().toString().trim().isEmpty()){
            Toast.makeText(mContext, "Please enter certificate", Toast.LENGTH_SHORT).show();
            return;
        }
        mTeacher.certificate = certificateET.getText().toString();

        if (dateBirthET.getText().toString().trim().isEmpty()){
            Toast.makeText(mContext, "Please enter Birth Date", Toast.LENGTH_SHORT).show();
            return;
        }
        mTeacher.personDetails.dateBirth = dateBirthET.getText().toString();

        if (employmentDateET.getText().toString().trim().isEmpty()){
            Toast.makeText(mContext, "Please enter Employment Date", Toast.LENGTH_SHORT).show();
            return;
        }
        mTeacher.employmentDate = employmentDateET.getText().toString();

        if (mobileNumb2ET.getText().toString().trim().isEmpty()){
            Toast.makeText(mContext, "Please enter Mobile Number 2", Toast.LENGTH_SHORT).show();
            return;
        }
        mTeacher.personDetails.mobile_2 = mobileNumb2ET.getText().toString();

        if (mobileNumb3ET.getText().toString().trim().isEmpty()){
            Toast.makeText(mContext, "Please enter Mobile Number 3", Toast.LENGTH_SHORT).show();
            return;
        }
        mTeacher.personDetails.mobile_3 = mobileNumb3ET.getText().toString();

        if (landNumbET.getText().toString().trim().isEmpty()){
            Toast.makeText(mContext, "Please enter Land Number", Toast.LENGTH_SHORT).show();
            return;
        }
        mTeacher.personDetails.landPhone = landNumbET.getText().toString();

        if (email2ET.getText().toString().trim().isEmpty()){
            Toast.makeText(mContext, "Please enter Email 2", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email2ET.getText())){
            Toast.makeText(mContext, "Please enter valid Email 2", Toast.LENGTH_SHORT).show();
            return;
        }
        mTeacher.personDetails.email_2 = email2ET.getText().toString();

        if (email3ET.getText().toString().trim().isEmpty()){
            Toast.makeText(mContext, "Please enter Email 3", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email3ET.getText())){
            Toast.makeText(mContext, "Please enter valid Email 3", Toast.LENGTH_SHORT).show();
            return;
        }
        mTeacher.personDetails.email_3 = email3ET.getText().toString();

        if (statusET.getText().toString().trim().isEmpty()){
            Toast.makeText(mContext, "Please enter Status", Toast.LENGTH_SHORT).show();
            return;
        }
        mTeacher.status = statusET.getText().toString();

        if (updatedByET.getText().toString().trim().isEmpty()){
            Toast.makeText(mContext, "Please enter Updated By", Toast.LENGTH_SHORT).show();
            return;
        }
        mTeacher.updatedBy = updatedByET.getText().toString();

        if (createdByET.getText().toString().trim().isEmpty()){
            Toast.makeText(mContext, "Please enter Created By", Toast.LENGTH_SHORT).show();
            return;
        }
        mTeacher.createdBy = createdByET.getText().toString();


        if (genderSpinner.getSelectedItem() != null){
            mTeacher.personDetails.gender = "gender " + genderSpinner.getSelectedItem().toString();
        }
        else{
            Toast.makeText(mContext, "Please choose Gender", Toast.LENGTH_SHORT).show();
            return;
        }

        if (classroomSpinner.getSelectedItem() != null){
            mTeacher.classroomId = new int[1];
            mTeacher.classroomId[0] = Integer
                    .parseInt(mClassrooms.get(classroomSpinner.getSelectedItemPosition()).identifier);
        }
        else{
            Toast.makeText(mContext, "Please choose Classroom", Toast.LENGTH_SHORT).show();
            return;
        }

        submitTeacher();
    }

    private void submitTeacher() {
        loaderImage.setVisibility(View.VISIBLE);
        loaderImage.setAnimation(mRotateAnim);
        RestRequest.postNewTeacher(mContext,
                mTeacher,
                new IResponseCallback() {
                    @Override
                    public void OnSuccess(Object object) {
                        Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                        loaderImage.clearAnimation();
                        loaderImage.setVisibility(View.GONE);
                    }

                    @Override
                    public void OnFailure(Object object) {
                        Toast.makeText(mContext, "Failure", Toast.LENGTH_SHORT).show();
                        loaderImage.clearAnimation();
                        loaderImage.setVisibility(View.GONE);
                    }
                });
    }

}
