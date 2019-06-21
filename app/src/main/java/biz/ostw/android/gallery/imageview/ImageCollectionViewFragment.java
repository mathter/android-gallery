package biz.ostw.android.gallery.imageview;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.URL;

import biz.ostw.android.gallery.R;


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

    private String urlParam;

    private RecyclerView collectionView;

    private OnFragmentInteractionListener mListener;

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
            urlParam = getArguments().getString(ARG_PARAM_URL);
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
        this.collectionView.setAdapter(new TestRecyclerViewAdapter());

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
