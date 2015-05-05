package pl.rr.extradowcipy;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import pl.rr.extradowcipy.R;

/**
 * Created by Rafal on 2015-04-20.
 */
public class BaseFragment extends Fragment {
    protected View mContainer;
    @InjectView(R.id.progress_bar) protected
    ProgressBar mProgressBar;

    protected void showProgress(boolean showProgress) {
        mContainer.setVisibility(showProgress ? View.GONE : View.VISIBLE);
        mProgressBar.setVisibility(showProgress ? View.VISIBLE : View.GONE);
    }

    protected void injectBaseViews(View rootView) {
        ButterKnife.inject(this, rootView);
    }



}
