package com.example.popularmovies.Fragments.Adapters.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.popularmovies.R;
import com.example.popularmovies.ViewModels.MainActivity;

public class OffLineFragment extends Fragment implements View.OnClickListener {

    ImageButton refresh;
    ProgressBar progressBar;

    // This method will be invoked when the Fragment view object is created.
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.offline_fragment, container, false);

        refresh = retView.findViewById(R.id.refresh);
        progressBar = retView.findViewById(R.id.progressBar);
        refresh.setOnClickListener(this);
        return retView;
    }
    @Override
    public void onClick(View view) {
        Log.d("CLICKING REFRESH","REFRESHING");
        Intent tryConnection = new Intent(getActivity(), MainActivity.class);
        startActivity(tryConnection);
    }
}
