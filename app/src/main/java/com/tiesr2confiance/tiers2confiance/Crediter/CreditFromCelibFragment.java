package com.tiesr2confiance.tiers2confiance.Crediter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.tiesr2confiance.tiers2confiance.R;
import com.tiesr2confiance.tiers2confiance.databinding.FragmentCrediterBinding;
import com.tiesr2confiance.tiers2confiance.databinding.FragmentLierParrainFilleulBinding;

public class CreditFromCelibFragment extends Fragment {


    private FragmentCrediterBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_crediter, container, false);
        binding = FragmentCrediterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}
