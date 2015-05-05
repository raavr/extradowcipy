package pl.rr.extradowcipy;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.etsy.android.grid.StaggeredGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.rr.extradowcipy.model.Category;
import pl.rr.extradowcipy.ui.CategoriesRecyclerAdapter;
import pl.rr.extradowcipy.ui.RecyclerItemClickListener;
import pl.rr.extradowcipy.ui.SpacesItemDecoration;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link pl.rr.extradowcipy.CategoriesFragment.OnCategoriesFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesFragment extends BaseFragment implements RecyclerView.OnClickListener {


    private final String CATEGORY_LIST = "category_list";
    private OnCategoriesFragmentInteractionListener mListener;
    private CategoriesRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @InjectView(R.id.recyclerVIewCategories) RecyclerView mRecyclerView;

    private ArrayList<Category> mCategories;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment CategoriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriesFragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();
        return fragment;
    }

    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hasOptionsMenu();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_categories, container, false);
        ButterKnife.inject(this, rootView);
        mContainer = mRecyclerView;
        injectBaseViews(rootView);

        if(savedInstanceState == null)
            mCategories = new ArrayList<>();
        else
            mCategories = savedInstanceState.getParcelableArrayList(CATEGORY_LIST);

        mRecyclerView.setHasFixedSize(true);
        mAdapter = new CategoriesRecyclerAdapter(getActivity(), mCategories);
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(5));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
            new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //mAdapter.removeCategory(position);
                onCategoryClick(position);
                //mAdapter.expandColapse(position);
            }
        }));

        if(savedInstanceState == null && mCategories.size() == 0) {
            showProgress(true);
            loadCategories();
        }

        return rootView;
    }

    private void loadCategories() {
        if(mListener != null)
            mListener.getCategoriesFromDB();
    }

    public void onSuccessCallback(List<Category> loadedCategroies) {

        mCategories.addAll(loadedCategroies);
        mAdapter.notifyDataSetChanged();
        showProgress(false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnCategoriesFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(CATEGORY_LIST, mCategories);
        super.onSaveInstanceState(outState);
    }

   private void onCategoryClick(int position) {
        if(mListener != null)
            mListener.onCategoryClick(mCategories.get(position).getCategory());
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnCategoriesFragmentInteractionListener {
        // TODO: Update argument type and name
        public void getCategoriesFromDB();
        public void onCategoryClick(String categoryName);
    }

}
