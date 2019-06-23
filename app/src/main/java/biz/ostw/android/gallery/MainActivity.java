package biz.ostw.android.gallery;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import biz.ostw.android.gallery.imageview.ImageCollectionViewFragment;
import biz.ostw.android.gallery.media.MediaDatabase;
import biz.ostw.android.gallery.media.ScanService;

public class MainActivity extends FragmentActivity implements ImageCollectionViewFragment.OnFragmentInteractionListener {

    private static final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 100;

    private ImageView imageView;

    private MyViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.imageView = this.findViewById(R.id.imageView);
        this.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaDatabase mdb = MediaDatabase.getInstance(MainActivity.this);
                mdb.get(Uri.parse("media:/"));
            }
        });
        MediaDatabase.deleteDatabase(this);

        this.checkPermissionAndStartScanService();
    }

    private void checkPermissionAndStartScanService() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_READ_EXTERNAL_STORAGE);
            } else {
                Toast.makeText(this, R.string.permission_required_read_external_storage, Toast.LENGTH_LONG).show();
            }
        } else {
            this.startScanService();
        }
    }

    private void startScanService() {
        Intent intent = new Intent(this, ScanService.class);
        intent.setAction(ScanService.ACTION_UPDATE);
        this.startService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_PERMISSION_READ_EXTERNAL_STORAGE:
                if (resultCode == RESULT_OK) {
                    this.startScanService();
                }
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static class MyLiveData extends LiveData<String> {

    }

    public static class MyViewModel extends ViewModel {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
