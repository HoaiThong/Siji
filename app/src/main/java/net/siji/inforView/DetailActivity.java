package net.siji.inforView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.siji.R;
import net.siji.model.Comic;
import net.siji.readView.ViewerActivity;
import net.siji.splashScreenView.SplashScreenActivity;

public class DetailActivity extends AppCompatActivity {
    private Comic comic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        comic = (Comic) getIntent().getSerializableExtra("comic");
        TextView tv_summary = findViewById(R.id.tv_summary);
        ImageView imageView = findViewById(R.id.img_icon_comic);
        tv_summary.setText(comic.getSummary());

        Glide.with(this)
                .load(comic.getIconUrl())
                .into(imageView);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Bundle data = new Bundle();
            data.putSerializable("comic", comic);
            Intent intent = new Intent(this, InforActivity.class);
            intent.putExtras(data);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
