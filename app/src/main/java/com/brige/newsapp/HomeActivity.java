package com.brige.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.brige.newsapp.utils.MyServices;
import com.brige.newsapp.utils.Notifications;
import com.brige.newsapp.utils.PreferenceStorage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.brige.newsapp.databinding.ActivityHomeBinding;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private NavController navController;
    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        navController =
                Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        new Notifications(getApplicationContext()).createNotificationChannel("Sync Chats", "Synching chats",
                Notifications.CHAT_SYNC_NOTIFICATION_ID);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.item_add){
            //Here we navigate to the VideoForm fragment
            //We use the navigate() method found within a NavController
            navController.navigate(R.id.videoFormFragment);
        }
        else if (id == R.id.item_logout){
            SweetAlertDialog confirm = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
            confirm.setTitle("Logout");
            confirm.setContentText("Are you sure you want to leave?");
            confirm.setConfirmText("Yes");
            confirm.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    new PreferenceStorage(HomeActivity.this).logout();
                    sweetAlertDialog.dismiss();
                    navController.popBackStack(R.id.navigation_home, true);
                    navController.navigate(R.id.navigation_home);
                }
            });
            confirm.setCancelText("Stay");
            confirm.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();
                    Toast.makeText(HomeActivity.this, "Thank you for staying", Toast.LENGTH_SHORT).show();
                }
            });
            confirm.show();


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {

        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}