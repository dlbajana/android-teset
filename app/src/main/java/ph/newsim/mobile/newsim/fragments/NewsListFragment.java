package ph.newsim.mobile.newsim.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.adapter.NewsListAdapter;
import ph.newsim.mobile.newsim.database.LocalDataSource;
import ph.newsim.mobile.newsim.model.Post;
import ph.newsim.mobile.newsim.ui.PostActivity;
import ph.newsim.mobile.newsim.util.SRequest;

public class NewsListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, NewsListAdapter.OnPostClickListener{

    private Context mContext;
    private LocalDataSource mLocalDataSource;
    private List<Post> mNewsDataSet;

    private RecyclerView mRecyclerView;
    private NewsListAdapter mNewsListAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private final int visibleThreshold = 3;
    private int lastVisibleItem, totalItemCount;
    private boolean isLoading;

    // Constructor

    public NewsListFragment() {

    }

    public static NewsListFragment newInstance(){
        return new NewsListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_list, container, false);

        mLocalDataSource = new LocalDataSource(mContext);
        mNewsDataSet = mLocalDataSource.retrievePost();

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout_news);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.black);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mNewsListAdapter = new NewsListAdapter(mContext, mNewsDataSet);
        mNewsListAdapter.setOnPostClickListener(this);

        mLinearLayoutManager = new LinearLayoutManager(mContext);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_news);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mNewsListAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = mLinearLayoutManager.getItemCount();
                lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();

                if (dy > 0){
                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        loadMore(mNewsDataSet.get(lastVisibleItem + visibleThreshold - 1).getId());
                        isLoading = true;
                    }
                }
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onRefresh() {
        SRequest request = new SRequest();
        int position = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastPostId;
        if (position != -1){
            lastPostId = mNewsDataSet.get(position).getId();
        }else{
            lastPostId = 0;
        }

        request.pullPost(lastPostId, "UP", new SRequest.RequestCallback() {
            @Override
            public void onSuccessful(JSONObject jsonData) {
                try {
                    mSwipeRefreshLayout.setRefreshing(false);
                    int insertedPostCount = storeNewsData(jsonData);
                    if (insertedPostCount != 0){
                        mNewsDataSet = mLocalDataSource.retrievePost();
                        mNewsListAdapter.setDataSet(mNewsDataSet);
                        mNewsListAdapter.notifyItemRangeInserted(0, insertedPostCount);
                        mRecyclerView.smoothScrollToPosition(0);
                    }

                } catch (JSONException e) {
                    Log.i("NewsListFragment", e.getMessage());
                }
            }

            @Override
            public void onFailed() {
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Exception e) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void loadMore(int lastId) {
        mNewsDataSet.add(null);
        mNewsListAdapter.notifyItemInserted(mNewsDataSet.size() - 1);

        SRequest request = new SRequest();
        request.pullPost(lastId, "DOWN", new SRequest.RequestCallback() {
            @Override
            public void onSuccessful(JSONObject jsonData) {
                try{
                    storeNewsData(jsonData);
                    mNewsDataSet.remove(mNewsDataSet.size() - 1);
                    mNewsListAdapter.notifyItemRemoved(mNewsDataSet.size() - 1);
                    mNewsDataSet = mLocalDataSource.retrievePost();
                    mNewsListAdapter.setDataSet(mNewsDataSet);
                    mNewsListAdapter.notifyDataSetChanged();
                    isLoading = false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailed() {
                mNewsDataSet.remove(mNewsDataSet.size() - 1);
                mNewsListAdapter.notifyItemRemoved(mNewsDataSet.size() - 1);
                Post post = new Post();
                post.setViewType(Post.VIEW_TYPE_LIMIT);
                mNewsDataSet.add(post);
                mNewsListAdapter.notifyItemInserted(mNewsDataSet.size());
                mNewsListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onPostClick(Post post) {
        startPostActivity(post);
    }

    private void startPostActivity(Post post){
        Intent intent = new Intent(getActivity(), PostActivity.class);
        intent.putExtra(Post.KEY, post);
        startActivity(intent);

    }

    public int storeNewsData(JSONObject jsonData) throws JSONException {
        // Storing Post Table
        JSONArray jsonArrayNews = jsonData.getJSONArray("data");
        for(int i = 0; i < jsonArrayNews.length(); i++){
            JSONObject jsonPost = jsonArrayNews.getJSONObject(i);
            JSONArray jsonArrayImagesURI = jsonPost.getJSONArray("post_images");

            String[] postImagesURI = new String[jsonArrayImagesURI.length()];
            String[] postImagesDescription = new String[jsonArrayImagesURI.length()];

            for(int j = 0; j < jsonArrayImagesURI.length(); j++){
                JSONObject jsonImage = jsonArrayImagesURI.getJSONObject(j);
                postImagesURI[j] = jsonImage.getString("uri");
                postImagesDescription[j] = jsonImage.getString("description");
            }

            Post post = new Post(jsonPost.getInt("id"), jsonPost.getString("branch"), jsonPost.getString("title"), jsonPost.getString("description"),
                    jsonPost.getString("created_at"), postImagesURI, postImagesDescription);

            mLocalDataSource.store(post);

        }
        return jsonArrayNews.length();
    }

    public void scrollToTop(){
        mRecyclerView.smoothScrollToPosition(0);
    }
}
