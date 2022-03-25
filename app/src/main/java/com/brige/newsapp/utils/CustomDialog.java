package com.brige.newsapp.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.brige.newsapp.ObjectBox;
import com.brige.newsapp.R;
import com.brige.newsapp.adapters.DiscoverAdapter;
import com.brige.newsapp.databinding.DiscoverOptionsBinding;
import com.brige.newsapp.models.Discover;
import com.brige.newsapp.ui.home.HomeFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import io.objectbox.Box;

public class CustomDialog {

    BottomSheetDialog discoverSheet;
    Context context;
    DiscoverOptionsBinding discoverBinding;
    View view;
    Box<Discover> discoverBox = ObjectBox.get().boxFor(Discover.class);

    public CustomDialog(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    public void showDiscoverDialog(Discover discover, DiscoverAdapter discoverAdapter) {

        discoverSheet = new BottomSheetDialog(context);
        discoverSheet.setContentView(R.layout.discover_options);

        Button btnUpdate, btnDelete, btnPlay;
        btnPlay = discoverSheet.findViewById(R.id.btn_dialog_play);
        btnDelete = discoverSheet.findViewById(R.id.btn_dialog_delete);
        btnUpdate = discoverSheet.findViewById(R.id.btn_dialog_update);
        TextView txtTitle = discoverSheet.findViewById(R.id.txt_video_title);

        txtTitle.setText(discover.getVideo_url());

        btnPlay.setOnClickListener(v ->{
            String url = discover.getVideo_url();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            context.startActivity(i);
        });

        btnUpdate.setOnClickListener(v ->{
            //Go to VideoFormFragment and Update data

            Bundle bundle = new Bundle();
            bundle.putBoolean("UPDATE", true);
            bundle.putLong("DISCOVER", discover.getId());

            Navigation.findNavController(view).navigate(R.id.videoFormFragment, bundle);

            discoverSheet.dismiss();
        });

        btnDelete.setOnClickListener(v ->{
            discoverSheet.dismiss(); //We use the dismiss() method to programmatically remove the dialog sheet from view
            Snackbar.make(view, "Are you sure you want to delete this discover?", Snackbar.LENGTH_LONG)
                    .setAction("Confirm", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            discoverBox.remove(discover.getId());
                           discoverAdapter.deleteDiscover(discoverBox.getAll());
                        }
                    }).show();
        });


        //This is necessary to launch the bottom dialog sheet
        discoverSheet.show();


    }
}
