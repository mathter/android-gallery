package biz.ostw.android.gallery.imageview;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.URL;

import biz.ostw.android.gallery.R;
import biz.ostw.android.gallery.media.Media;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ImageCollectionViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ImageCollectionViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageCollectionViewFragment extends Fragment {
    private static final String ARG_PARAM_URL = "param_url";

    private Uri urlParam;

    private RecyclerView collectionView;

    private OnFragmentInteractionListener mListener;

    private PagedListAdapter<Media, ImageItemViewHolder> adapter;

    public ImageCollectionViewFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param url URL of the image source.
     * @return A new instance of fragment ImageCollectionViewFragment.
     */
    public static ImageCollectionViewFragment newInstance(URL url) {
        ImageCollectionViewFragment fragment = new ImageCollectionViewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            urlParam = getArguments().getParcelable(ARG_PARAM_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_collection_view, container, false);
        RecyclerView.LayoutManager lLayout = new GridLayoutManager(this.getContext(), 2);
        this.collectionView = view.findViewById(R.id.fragment_image_collection_view_collectionView);
        this.collectionView.setLayoutManager(lLayout);

        this.collectionView.setHasFixedSize(true);

        MediaViewModel model = this.getViewModel();

        model.getData().observe(this, new Observer<PagedList<Media>>() {
            @Override
            public void onChanged(PagedList<Media> media) {
                ImageCollectionViewFragment.this.adapter.submitList(media);
            }
        });
        model.getUriParam().observe(this, new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                ImageCollectionViewFragment.this.urlParam = uri;
            }
        });

        this.adapter = new MediaRecycleViewAdapter(this.getContext());
        this.collectionView.setAdapter(this.adapter);

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private MediaViewModel getViewModel() {
        return ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new MediaViewModel(ImageCollectionViewFragment.this.getActivity());
            }
        }).get(MediaViewModel.class);
    }
}
