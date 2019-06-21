package biz.ostw.android.gallery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.net.Uri;
import android.os.Bundle;

import biz.ostw.android.gallery.imageview.ImageCollectionViewFragment;

public class MainActivity extends FragmentActivity implements ImageCollectionViewFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
