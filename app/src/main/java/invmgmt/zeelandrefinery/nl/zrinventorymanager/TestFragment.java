package invmgmt.zeelandrefinery.nl.zrinventorymanager;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;

public class TestFragment extends Fragment {

    private ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about,
                container, false);
        if(getActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("About");
        }
        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
