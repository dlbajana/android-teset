package ph.newsim.mobile.newsim.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.adapter.PostDetailAdapter;
import ph.newsim.mobile.newsim.model.Post;

public class PostActivity extends AppCompatActivity {

    private Post mPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        // Post Object Initialization
        mPost = getIntent().getParcelableExtra(Post.KEY);

        // Toolbar Initialization
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert toolbar != null;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PostActivity.this.finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // PostDetailAdapter Initialization
        PostDetailAdapter postDetailAdapter = new PostDetailAdapter(this, mPost);

        // RecyclerView Initialization
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_post);
        assert recyclerView != null;
        recyclerView.setAdapter(postDetailAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
