package com.tech.cloudnausor.ohmytennis.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;
import com.tech.cloudnausor.ohmytennis.R;
import com.tech.cloudnausor.ohmytennis.activity.homepage.NewHomePage;
import com.tech.cloudnausor.ohmytennis.apicall.ApiClient;
import com.tech.cloudnausor.ohmytennis.apicall.ApiInterface;
import com.tech.cloudnausor.ohmytennis.dto.ProfileEditDTO;
import com.tech.cloudnausor.ohmytennis.session.SessionManagement;
import com.tech.cloudnausor.ohmytennis.utils.CircleImageView;
import com.tech.cloudnausor.ohmytennis.utils.ImagePickerActivity;
import com.tech.cloudnausor.ohmytennis.utils.MyAutoCompleteTextView;
import com.tech.cloudnausor.ohmytennis.utils.SingleTonProcess;
import com.tech.cloudnausor.ohmytennis.utils.homemenu.Constant;
import com.tech.cloudnausor.ohmytennis.utils.homemenu.Menus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;
import static android.content.ContentValues.TAG;
import static android.content.res.ColorStateList.valueOf;

public class ProfileDetailsFragment extends Fragment {

    private boolean mSearchCheck;
    String imageString ="";
    ImageView profile;
    Uri picUri;
    AutoCompleteTextView level;
    ArrayAdapter<String> adapter;
    private ApiInterface apiRequest = ApiClient.getClient().create(ApiInterface.class);
    SessionManagement session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String userid_ = new String();
    String UserEmail = new String();
    Switch sparring,patner;
    TextInputEditText user_profile_first_name,user_profile_second_name, user_profile_email,postalcode,user_profile_mobile
            ,user_profile_description;
    TextInputLayout user_profile_first_name_error,user_profile_second_name_error, user_profile_email_error,postalcode_error,
            user_profile_mobile_error,level_error,user_profile_description_error;
    
    CircleImageView form_one_profile;
    Button user_profile_save_continue,modifier_one;

    SingleTonProcess singleTonProcess;

    public static final int REQUEST_IMAGE = 100;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;
    private final static int IMAGE_RESULT = 200;


    public  ProfileDetailsFragment newInstance(String text) {
        ProfileDetailsFragment mFragment = new ProfileDetailsFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString(Constant.TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View profileView = inflater.inflate(R.layout.user_profile_layout, container, false);
        sharedPreferences = getContext().getSharedPreferences("RegUser", 0);
        editor = sharedPreferences.edit();
        session = new SessionManagement(getContext());
        singleTonProcess = singleTonProcess.getInstance();

        if (sharedPreferences.contains("KEY_User_ID"))
        {
            userid_ = sharedPreferences.getString("KEY_User_ID", "");
        }
        if(sharedPreferences.contains("Email")){
            UserEmail = sharedPreferences.getString("Email", "");
            System.out.println("UserEmail--> " + UserEmail);
        }
        modifier_one = (Button)profileView.findViewById(R.id.modifier_one);
        form_one_profile =(CircleImageView)profileView.findViewById(R.id.form_one_profile);
        user_profile_first_name =(TextInputEditText)profileView.findViewById(R.id.user_profile_first_name);
                user_profile_second_name=(TextInputEditText)profileView.findViewById(R.id.user_profile_second_name);
        user_profile_email=(TextInputEditText)profileView.findViewById(R.id.user_profile_email);
                postalcode=(TextInputEditText)profileView.findViewById(R.id.postalcode);
        user_profile_mobile=(TextInputEditText)profileView.findViewById(R.id.user_profile_mobile);
                level=(MyAutoCompleteTextView)profileView.findViewById(R.id.level);
        user_profile_description=(TextInputEditText)profileView.findViewById(R.id.user_profile_description);
        sparring = (Switch) profileView.findViewById(R.id.sparring);
                patner = (Switch) profileView.findViewById(R.id.parnter);

        user_profile_first_name_error =(TextInputLayout)profileView.findViewById(R.id.user_profile_first_name_error);
        user_profile_second_name_error=(TextInputLayout)profileView.findViewById(R.id.user_profile_second_name_error);
        user_profile_email_error=(TextInputLayout)profileView.findViewById(R.id.user_profile_email_error);
        postalcode_error=(TextInputLayout)profileView.findViewById(R.id.postalcode_error);
        user_profile_mobile_error=(TextInputLayout)profileView.findViewById(R.id.user_profile_mobile_error);
        level_error=(TextInputLayout)profileView.findViewById(R.id.level_error);
        user_profile_description_error=(TextInputLayout)profileView.findViewById(R.id.user_profile_description_error);
        user_profile_save_continue = (Button)profileView.findViewById(R.id.user_profile_save_continue);

        ArrayList<String> level_name_list = new ArrayList<String>(Arrays.asList(new String[]{"Débutant", "Intermédiaire", "Avancée"}));

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, level_name_list);
        level.setAdapter(adapter);
        level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View arg0) {
                if(!level_name_list.isEmpty()){
                    level.showDropDown();}else {
                    Toast.makeText(getContext(),"No Data",Toast.LENGTH_LONG).show();
                }
            }
        });

        form_one_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onProfileImageClick();
//                startActivityForResult(onProfileImageClick(), IMAGE_RESULT);
            }
        });
        user_profile_save_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setusername();
            }
        });
        modifier_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTint();
            }
        });

        getusername();
        return profileView;
    }


    public void previewTint(){

//        form_one_profile.setBackgroundTintList(valueOf(getResources().getColor(R.color.button_cancel)));
        user_profile_first_name.setBackgroundTintList(valueOf(getResources().getColor(R.color.button_cancel)));
        user_profile_second_name.setBackgroundTintList(valueOf(getResources().getColor(R.color.button_cancel)));
        user_profile_email.setBackgroundTintList(valueOf(getResources().getColor(R.color.button_cancel)));
        postalcode.setBackgroundTintList(valueOf(getResources().getColor(R.color.button_cancel)));
        user_profile_mobile.setBackgroundTintList(valueOf(getResources().getColor(R.color.button_cancel)));
        level.setBackgroundTintList(valueOf(getResources().getColor(R.color.button_cancel)));
        user_profile_description.setBackgroundTintList(valueOf(getResources().getColor(R.color.button_cancel)));
        sparring.setBackgroundTintList(valueOf(getResources().getColor(R.color.button_cancel)));
        patner.setBackgroundTintList(valueOf(getResources().getColor(R.color.button_cancel)));

        form_one_profile.setEnabled(false);
        user_profile_first_name.setEnabled(
                false
        );
        user_profile_second_name.setEnabled(false);
        user_profile_email.setEnabled(false);
        postalcode.setEnabled(false);
        user_profile_mobile.setEnabled(false);
        level.setEnabled(false);
        user_profile_description.setEnabled(
                false
        );
        sparring.setEnabled(false);
        patner.setEnabled(false);

        modifier_one.setVisibility(View.VISIBLE);
        user_profile_save_continue.setVisibility(View.GONE);
    }

    public void EditTint(){

        form_one_profile.setEnabled(true);
        user_profile_first_name.setEnabled(true);
        user_profile_second_name.setEnabled(true);
        user_profile_email.setEnabled(false);
        postalcode.setEnabled(true);
        user_profile_mobile.setEnabled(true);
        level.setEnabled(true);
        user_profile_description.setEnabled(true);
        sparring.setEnabled(true);
        patner.setEnabled(true);
        modifier_one.setVisibility(View.GONE);
        user_profile_save_continue.setVisibility(View.VISIBLE);

//        form_one_profile.setBackgroundTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        user_profile_first_name.setBackgroundTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        user_profile_second_name.setBackgroundTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        user_profile_email.setBackgroundTintList(valueOf(getResources().getColor(R.color.button_cancel)));
        postalcode.setBackgroundTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        user_profile_mobile.setBackgroundTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        level.setBackgroundTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        user_profile_description.setBackgroundTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        sparring.setBackgroundTintList(valueOf(getResources().getColor(R.color.colorPrimary)));
        patner.setBackgroundTintList(valueOf(getResources().getColor(R.color.colorPrimary)));



    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

        SearchView searchView = (SearchView) (menu.findItem(Menus.SEARCH).getActionView());
        searchView.setQueryHint(this.getString(R.string.search));

        ((EditText)searchView.findViewById(androidx.appcompat.R.id.search_src_text))
                .setHintTextColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(OnQuerySearchView);
        menu.findItem(Menus.ADD).setVisible(true);
        menu.findItem(Menus.EDIT).setVisible(false);
        menu.findItem(Menus.UPDATE).setVisible(false);
        menu.findItem(Menus.SEARCH).setVisible(false);

        mSearchCheck = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {

            case Menus.ADD:

                break;


            case Menus.UPDATE:
                break;

            case Menus.SEARCH:
                mSearchCheck = true;
                break;
        }
        return true;
    }

    private SearchView.OnQueryTextListener OnQuerySearchView = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(String arg0) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean onQueryTextChange(String arg0) {
            // TODO Auto-generated method stub
            if (mSearchCheck){
                // implement your search here
            }
            return false;
        }
    };

    public void getusername(){
        ((NewHomePage)getContext()).showprocess();
        apiRequest.getuserbyid(UserEmail).enqueue(new Callback<ProfileEditDTO>() {
            @Override
            public void onResponse(Call<ProfileEditDTO> call, Response<ProfileEditDTO> response) {
                ProfileEditDTO profileEditDTO = response.body();
//
                if(profileEditDTO!= null){
                    if(profileEditDTO.getIsSuccess().equals("true")){
                        user_profile_first_name.setText(response.body().getProfileDataArray().getUser_list().get(0).getFirstName());
                user_profile_second_name.setText(response.body().getProfileDataArray().getUser_list().get(0).getLastName());
                user_profile_email.setText(response.body().getProfileDataArray().getUser_list().get(0).getEmail());
                postalcode.setText(response.body().getProfileDataArray().getUser_list().get(0).getPostalCode());
                        imageString = response.body().getProfileDataArray().getUser_list().get(0).getUser_Image();
                user_profile_mobile.setText(response.body().getProfileDataArray().getUser_list().get(0).getMobile());
                        ArrayList<String> elephantList = new ArrayList<String>();
                        if(response.body().getProfileDataArray().getUser_list().get(0).getUser_Team() != null && response.body().getProfileDataArray().getUser_list().get(0).getUser_Team() != ""){
                        elephantList = new ArrayList<>(Arrays.asList(response.body().getProfileDataArray().getUser_list().get(0).getUser_Team().split(",")));
                        }else {
                            elephantList.clear();
                        }

                        if(response.body().getProfileDataArray().getUser_list().get(0).getUser_Image() != null && !response.body().getProfileDataArray().getUser_list().get(0).getUser_Image().equals("") && !response.body().getProfileDataArray().getUser_list().get(0).getUser_Image().matches("http") && !response.body().getProfileDataArray().getUser_list().get(0).getUser_Image().contains("WWW.") && !response.body().getProfileDataArray().getUser_list().get(0).getUser_Image().contains("https") &&
                                !response.body().getProfileDataArray().getUser_list().get(0).getUser_Image().contains(".jpeg") && !response.body().getProfileDataArray().getUser_list().get(0).getUser_Image().contains(".png") && !response.body().getProfileDataArray().getUser_list().get(0).getUser_Image().contains("undefined") ){
                            String imageString = response.body().getProfileDataArray().getUser_list().get(0).getUser_Image();
                            String ss = response.body().getProfileDataArray().getUser_list().get(0).getUser_Image();
                            ss = ss.replace("data:image/png;base64,","");
                            ss = ss.replace("data:image/jpeg;base64,","");
                            byte[] imageAsBytes = Base64.decode(ss.getBytes(), Base64.DEFAULT);
//                        byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                            Drawable drawable = new BitmapDrawable(getContext().getResources(), decodedImage);
//                        Drawable drawable = (Drawable)new BitmapDrawable(decodedImage);
                            form_one_profile.setImageDrawable(drawable);
//                    Picasso.get().load("http://172.107.175.10:3000/upload/"+response.body().getProfileDataArray().getUser_list().get(0).getUser_Image()).fit().into(holder.Profile_Coach_image);
                        }

                        level.setText(response.body().getProfileDataArray().getUser_list().get(0).getUser_Level());
if(elephantList.size()!=0){
                if(elephantList.contains("Partenaire")){
                    patner.setChecked(true);
                }else {
                    patner.setChecked(false);
                }
                        if(elephantList.contains("Sparring")){
                            sparring.setChecked(true);
                        }else {
                            sparring.setChecked(false);
                        }
}else {
    patner.setChecked(false);
    sparring.setChecked(false);
}
//                level=(MyAutoCompleteTextView)profileView.findViewById(R.id.level);
                      user_profile_description.setText(response.body().getProfileDataArray().getUser_list().get(0).getAddress());
                        System.out.println(new Gson().toJson(response.body()));
//                        LocationBaseCoachData locationBaseCoachData = response.body().getData();

                        ((NewHomePage)getContext()).dismissprocess();
                    }else {
                        ((NewHomePage)getContext()).dismissprocess();

                    }
                }else {
                     ((NewHomePage)getContext()).dismissprocess();
                }
                previewTint();
            }

            @Override
            public void onFailure(Call<ProfileEditDTO> call, Throwable t) {
                previewTint();
                ((NewHomePage)getContext()).dismissprocess();
                Log.e("bala hi",t.toString());
            }
        });

    }

    public void setusername(){
        ((NewHomePage)getContext()).showprocess();
        ProfileEditDTO.profileInformation profileInformation = new ProfileEditDTO.profileInformation();
        ArrayList<String> userteam = new ArrayList<String>();
        if(patner.isChecked()){
            userteam.add("Partenaire");
        }
        if(sparring.isChecked()) {
            userteam.add("Sparring");
        }

        profileInformation.setFirstName(user_profile_first_name.getText().toString());
        profileInformation.setLastName(user_profile_second_name.getText().toString());
        profileInformation.setEmail(user_profile_email.getText().toString());
        profileInformation.setUser_Location(postalcode.getText().toString());
        profileInformation.setMobile(user_profile_mobile.getText().toString());
        profileInformation.setUser_Team(TextUtils.join(",", userteam));
        profileInformation.setAddress(user_profile_description.getText().toString());
        profileInformation.setUser_Image(imageString);
        profileInformation.setUser_Level(level.getText().toString());
  System.out.println(new Gson().toJson(profileInformation));

        apiRequest.setupdateprofile(profileInformation).enqueue(new Callback<ProfileEditDTO>() {
            @Override
            public void onResponse(Call<ProfileEditDTO> call, Response<ProfileEditDTO> response) {
                ProfileEditDTO profileEditDTO = response.body();
                System.out.println(new Gson().toJson(response.body()));
                if(profileEditDTO!= null){
                    if(profileEditDTO.getIsSuccess().equals("true")){
                        ((NewHomePage)getContext()).dismissprocess();
                        getusername();
                    }else {
                        ((NewHomePage)getContext()).dismissprocess();
                    }
                }else {
                    ((NewHomePage)getContext()).dismissprocess();
                }

            }

            @Override
            public void onFailure(Call<ProfileEditDTO> call, Throwable t) {
                ((NewHomePage)getContext()).dismissprocess();
                Log.e("bala hi",t.toString());
            }
        });

    }


    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (Objects.requireNonNull(getActivity()).checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }
    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    private void askPermissions() {

        permissions.add(CAMERA);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissions.add(READ_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
    }

//    private void initRetrofitClient() {
//        OkHttpClient client = new OkHttpClient.Builder().build();
//
//        apiService = new Retrofit.Builder().baseUrl("http://192.168.88.65:3000").client(client).build().create(ApiService.class);
//    }


    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);
        Picasso.get().load(url).fit().into(form_one_profile);
//        GlideApp.with(this).load(url)
//                .into(form_one_profile);
        form_one_profile.setColorFilter(ContextCompat.getColor(getContext(), android.R.color.transparent));
    }

    private void loadProfileDefault() {

        Picasso.get().load(R.drawable.baseline_account_circle_black_48).fit().into(form_one_profile);
//        GlideApp.with(this).load(R.drawable.baseline_account_circle_black_48)
//                .into(form_one_profile);
        form_one_profile.setColorFilter(ContextCompat.getColor(getContext(), R.color.profile_default_tint));
    }

    private void onProfileImageClick() {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(getContext(), new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, false);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
//        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
//        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
//        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(getContext(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    InputStream in = getContext().getContentResolver().openInputStream(uri);

                    byte[] bytes=getBytes(in);
                    Log.d("data", "onActivityResult: bytes size="+bytes.length);

                    Log.d("data", "onActivityResult: Base64string="+ Base64.encodeToString(bytes,Base64.DEFAULT));
                    imageString = Base64.encodeToString(bytes,Base64.DEFAULT);
//                    Bitmap bitmap =BitmapFactory.decodeFile(data.getData().toString());
                    // You can update this bitmap to your server
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    // loading form_one_profile image from local cache
                    loadProfile(uri.toString());
//                     InputStream imageStream = getActivity().getContentResolver().openInputStream(uri);
//
//                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//                    byte[] byteArray = byteArrayOutputStream .toByteArray();
//                    imageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    Bitmap bitmap64 = BitmapFactory.decodeStream(imageStream);
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                    byte[] imageBytes = baos.toByteArray();
//                    imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                ProfileDetailsFragment.this.openSettings();
            }
        });
        builder.setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("pic_uri", picUri);
//        outState.putParcelable("profileimage", (Parcelable) drawable);
    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        if (savedInstanceState != null) {
//            picUri = savedInstanceState.getParcelable("pic_uri");
////            Drawable drawable = savedInstanceState.getParcelable("profileimage");
////            form_one_profile.setImageDrawable(drawable);
//            //Restore the fragment's state here
//        }
//    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            picUri = savedInstanceState.getParcelable("pic_uri");
//            Drawable drawable = savedInstanceState.getParcelable("profileimage");
//            form_one_profile.setImageDrawable(drawable);
            //Restore the fragment's state here
        }
    }

}
