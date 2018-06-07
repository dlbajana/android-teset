package ph.newsim.mobile.newsim.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ph.newsim.mobile.newsim.R;
import ph.newsim.mobile.newsim.adapter.PlaceListAdapter;
import ph.newsim.mobile.newsim.database.BranchesDataSource;
import ph.newsim.mobile.newsim.model.Branch;

public class PlacesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        // Toolbar Initialization
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert toolbar != null;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacesActivity.this.finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // DataSet Initialization
        final BranchesDataSource branchesDataSource = new BranchesDataSource();

        // PlaceListAdapter Initialization
        PlaceListAdapter placeListAdapter = new PlaceListAdapter(this, branchesDataSource.getBranches());
        placeListAdapter.setOnButtonClickListener(new PlaceListAdapter.OnButtonClickListener() {

            @Override
            public void onDirectionsClick(Branch branch) {

                Uri gmmIntentUri = Uri.parse(branch.getlocationURI());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }

            @Override
            public void onReviewClick(Branch branch) {

            }
        });

        // RecyclerView Initialization
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_places);
        assert recyclerView != null;
        recyclerView.setAdapter(placeListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}
