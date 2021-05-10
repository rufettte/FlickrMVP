package com.example.flickr_mvp.Views;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import androidx.core.widget.ContentLoadingProgressBar;
import com.example.flickr_mvp.Activities.SearchActivity;
import com.example.flickr_mvp.Contracts.SearchContract;
import com.example.flickr_mvp.Objects.Response;
import com.example.flickr_mvp.R;
import com.example.flickr_mvp.Utils.MessageHandler;

/* This class helps to visually inform the user about the search request.
*  For instance, before the request starts, progressbar is shown to the user
*  to inform that the process started; when the request operation is over,
*  no matter how it was over (successfully or with error) the progressbar
*  disappears.
*
*  Additionally, if the expected response is acquired, the search results
*  are shown to the user appropriately. If there is an error during the request
*  process, then the user is informed accordingly. */
public class SearchView implements SearchContract.View{

    private Context context;
    private ContentLoadingProgressBar pb;

    public SearchView(Context context) {
        this.context = context;
    }

    @Override
    public void onStart() {
        pb = (ContentLoadingProgressBar) ((Activity)context).findViewById(R.id.progressbar);
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void onComplete() {
        pb.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(Response response) {
        if (response.getImagesResponse().getImages().size()==0)
            MessageHandler.showMessage(context, R.string.SearchRequest_no_data_loaded);
        else
            ((SearchActivity) context).applyChanges(response.getImagesResponse().getImages());
    }

    @Override
    public void onError(Throwable t) {
        pb.setVisibility(View.GONE);
        MessageHandler.showMessage(context, R.string.SearchRequest_error);
    }
}
