package com.brige.newsapp.ui.notifications;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.brige.newsapp.R;
import com.brige.newsapp.databinding.FragmentNotificationsBinding;
import com.brige.newsapp.networking.ChatServiceGenerator;
import com.brige.newsapp.networking.pojos.RegisterRequest;
import com.brige.newsapp.networking.pojos.UserResponse;
import com.brige.newsapp.utils.PreferenceStorage;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private Context context;
    private SweetAlertDialog pDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = requireActivity();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitle("Loading...");

        showOrHideLayouts();
        binding.btnLogin.setOnClickListener(v->{
            showLoginDialog();
        });

        binding.btnRegister.setOnClickListener(v -> {
            showRegisterDialog();
        });

        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showLoginDialog() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_login_dialog);

        Button btnLogin = bottomSheetDialog.findViewById(R.id.btn_dialog_login);
        Button switchRegister = bottomSheetDialog.findViewById(R.id.btn_dialog_switch_register);
        TextInputEditText inputPass, inputUsername;
        inputPass = bottomSheetDialog.findViewById(R.id.input_password);
        inputUsername = bottomSheetDialog.findViewById(R.id.input_username);


         btnLogin.setOnClickListener(v ->{

             loginUser(
                     inputUsername.getText().toString().trim(),
                     inputPass.getText().toString().trim()
             );
        });
         switchRegister.setOnClickListener(v ->{
             bottomSheetDialog.dismiss();
             showRegisterDialog();
         });

        bottomSheetDialog.show();
    }

    private void showRegisterDialog() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_register_dialog);

        TextInputEditText inputRegisterUsername, inputEmail, inputNumber, inputPassword, inputConfirmPassword;
        inputEmail = bottomSheetDialog.findViewById(R.id.input_email);
        inputConfirmPassword = bottomSheetDialog.findViewById(R.id.input_confirm_password);
        inputRegisterUsername = bottomSheetDialog.findViewById(R.id.input_register_username);
        inputNumber = bottomSheetDialog.findViewById(R.id.input_number);
        inputPassword = bottomSheetDialog.findViewById(R.id.input_register_password);

        Button btnRegister = bottomSheetDialog.findViewById(R.id.btn_dialog_register);
        Button switchLogin = bottomSheetDialog.findViewById(R.id.btn_dialog_switch_login);

        switchLogin.setOnClickListener(v ->{
            bottomSheetDialog.dismiss();
            showLoginDialog();
        });

        btnRegister.setOnClickListener(v ->{

            RegisterRequest registerRequest = new RegisterRequest(
                    inputNumber.getText().toString().trim(),
                    inputRegisterUsername.getText().toString().trim(),
                    inputEmail.getText().toString().trim(),
                    inputPassword.getText().toString().trim()
            );

            registerUser(registerRequest);
        });


        bottomSheetDialog.show();
    }

    private void showOrHideLayouts() {
        boolean isAuthenticated = new PreferenceStorage(context).isAuthenticated();
        if (!isAuthenticated){
            binding.btnRegister.setVisibility(View.GONE);
            binding.btnLogin.setVisibility(View.GONE);
            binding.imageView2.setVisibility(View.GONE);
            binding.textView8.setVisibility(View.GONE);
            binding.recyclerMessage.setVisibility(View.VISIBLE);
        }
        else{
            binding.btnRegister.setVisibility(View.VISIBLE);
            binding.btnLogin.setVisibility(View.VISIBLE);
            binding.imageView2.setVisibility(View.VISIBLE);
            binding.textView8.setVisibility(View.VISIBLE);
            binding.recyclerMessage.setVisibility(View.GONE);
        }
    }

    public void registerUser(RegisterRequest registerRequest){
        pDialog.setContentText("Registering User");
        pDialog.show();

        Call<UserResponse> call = ChatServiceGenerator.getInstance()
                .getApiConnector().register(registerRequest);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                pDialog.dismiss();
                if (response.code()== 200 && response.body()!= null){

                    new PreferenceStorage(context).setAuthStatus(true);
                    new PreferenceStorage(context).setUserData(response.body());

                    SweetAlertDialog successDialog = new SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE);
                    successDialog.setTitle("Welcome "+ response.body().getUsername());
                    successDialog.setContentText("Registration Successful");
                    successDialog.show();
                }
                else if (response.code() == 500){
                    SweetAlertDialog errorDialog =
                            new SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE);
                    errorDialog.setTitle("Oops ");
                    errorDialog.setContentText(response.message());
                    errorDialog.show();
                }
                else{
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                Log.d("TEST::", "onResponse: "+response.message());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                Log.d("TEST::", "onFailure: "+t.getMessage());
            }
        });
    }

    public void loginUser(String username, String password){
        pDialog.setContentText("Logging in  User");
        pDialog.show();

        Call<UserResponse> call = ChatServiceGenerator.getInstance()
                .getApiConnector().login(username, password);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                pDialog.dismiss();
                if (response.code()== 200 && response.body()!= null){

                    new PreferenceStorage(context).setAuthStatus(true);
                    new PreferenceStorage(context).setUserData(response.body());

                    SweetAlertDialog successDialog = new SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE);
                    successDialog.setTitle("Welcome "+ response.body().getUsername());
                    successDialog.setContentText("login Successful");
                    successDialog.show();
                }
                else if (response.code() == 500){
                    SweetAlertDialog errorDialog =
                            new SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE);
                    errorDialog.setTitle("Oops ");
                    errorDialog.setContentText(response.message());
                    errorDialog.show();
                }
                else{
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                Log.d("TEST::", "onResponse: "+response.message());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                Log.d("TEST::", "onFailure: "+t.getMessage());
            }
        });
    }
}