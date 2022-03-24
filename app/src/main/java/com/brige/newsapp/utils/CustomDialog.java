package com.brige.newsapp.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.TextView;

import com.brige.newsapp.R;
import com.brige.newsapp.databinding.DiscoverOptionsBinding;
import com.brige.newsapp.models.Discover;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class CustomDialog {

    BottomSheetDialog discoverSheet;
    Context context;
    DiscoverOptionsBinding discoverBinding;

    public CustomDialog(Context context) {
        this.context = context;
    }

    public void showDiscoverDialog(Discover discover) {

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
        });

        btnDelete.setOnClickListener(v ->{

            discoverSheet.dismiss(); //We use the dismiss() method to programmatically remove the dialog sheet from view
        });


        //This is necessary to launch the bottom dialog sheet
        discoverSheet.show();


    }
}
