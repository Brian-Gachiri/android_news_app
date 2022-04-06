package com.brige.newsapp.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.brige.newsapp.R;
import com.brige.newsapp.databinding.FragmentNotificationsBinding;
import com.brige.newsapp.utils.PreferenceStorage;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private Context context;


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
            new PreferenceStorage(context).setAuthStatus(true);
            bottomSheetDialog.dismiss();
            showOrHideLayouts();
        });

        bottomSheetDialog.show();
    }

    private void showRegisterDialog() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_register_dialog);
    }

    private void showOrHideLayouts() {
        boolean isAuthenticated = new PreferenceStorage(context).isAuthenticated();
        if (isAuthenticated){
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
}