package com.example.flickr_mvp.Views;

import android.app.Activity;
import android.content.Context;
import androidx.core.widget.ContentLoadingProgressBar;
import com.example.flickr_mvp.Activities.SearchActivity;
import com.example.flickr_mvp.Contracts.SearchContract;
import com.example.flickr_mvp.Objects.Response;
import com.example.flickr_mvp.R;
import com.example.flickr_mvp.Utils.MessageHandler;

public class SearchView implements SearchContract.View{

    private final Context context;
    private final ContentLoadingProgressBar pb;

    public SearchView(Context context) {
        pb = ((Activity)context).findViewById(R.id.progressbar);
        this.context = context;
    }

    @Override
    public void onStart() {
        pb.show();
    }

    @Override
    public void onComplete() {
        pb.hide();
    }

    @Override
    public void onSuccess(Response response) {
        if (response.getImagesResponse().getImages().size()==0) MessageHandler.showMessage(context, R.string.SearchRequest_no_data_loaded);
        else ((SearchActivity) context).applyChanges(response.getImagesResponse().getImages());
    }

    @Override
    public void onError(Throwable t) {
        pb.hide();
        MessageHandler.showMessage(context, R.string.SearchRequest_error);
    }
}
