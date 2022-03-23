package com.brige.newsapp;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.brige.newsapp.databinding.FragmentHomeBinding;
import com.brige.newsapp.databinding.FragmentVideoFormBinding;
import com.brige.newsapp.models.Discover;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import io.objectbox.Box;


public class VideoFormFragment extends Fragment {

    /**
     * Binding is an easier way of accessing XML components in Java
     */
    FragmentVideoFormBinding binding;
    ActivityResultLauncher<Intent> launchFilePicker;
    String image_url = "Empty";
    Box<Discover> discoverBox = ObjectBox.get().boxFor(Discover.class);

    public VideoFormFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        launchFilePicker = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == Activity.RESULT_OK){

                            Intent data = result.getData();
                            //doSomeOperations
                            fillImageData(data);
                        }else{

                            //If the operation doesn't work we show a toast
                            try {
                                Toast.makeText(getActivity(), "Image picking cancelled.", Toast.LENGTH_SHORT).show();
                            }
                            catch (ActivityNotFoundException e){
                                e.printStackTrace();
                            }

                        }
                    }
                }
        );

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentVideoFormBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        verifyPermissions();

        binding.btnUploadDiscover.setOnClickListener(v -> {

            validateInputs();
        });

        binding.imgAddDiscover.setOnClickListener(v ->{

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            String[] mimeTypes = {"image/jpeg", "image/png", "image/svg"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

            launchFilePicker.launch(intent);

        });

        return root;
    }


    public void verifyPermissions(){

        String[] permissions= {
                Manifest.permission.READ_EXTERNAL_STORAGE
        };

        if (ContextCompat
                .checkSelfPermission(getActivity().getApplicationContext(), permissions[0])
                == PackageManager.PERMISSION_GRANTED){

            Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
        }
        else{
            ActivityCompat
                    .requestPermissions(getActivity(), permissions, 134);
        }
    }


    private void fillImageData(Intent data) {

        image_url = data.getData().toString();
        binding.imgAddDiscover.setImageURI(data.getData());
    }

    private void validateInputs() {
        String video_url = binding.inputVideoUrl.getText().toString().trim();
        if (image_url.contentEquals("Empty")){
            Toast.makeText(getActivity(), "Please select an Image", Toast.LENGTH_SHORT).show();
        }
        else if (video_url.isEmpty()){
            binding.inputVideoUrl.setError("Please enter a valid video url");
        }
        else{
            //save to objectbox
            Discover discover = new Discover();
            discover.setImage(image_url);
            discover.setVideo_url(video_url);
            discover.setIs_external_image(false);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            discover.setCreated_at(formatter.format(date));

            //save data
            discoverBox.put(discover);

            Toast.makeText(getActivity(), "Discover video saved successfully", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(binding.getRoot()).navigate(R.id.navigation_home);
        }
    }

}