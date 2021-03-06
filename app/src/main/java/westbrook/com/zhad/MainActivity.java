package westbrook.com.zhad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    RecyclerView recyclerView;
    LinearLayoutManager mLinearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        mLinearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(new Adapter(this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //Returns the adapter position of the first visible(没有要求完全显示) view. //第一个View的位置
                int fPos = mLinearLayoutManager.findFirstVisibleItemPosition();
                //Returns the adapter position of the last fully(完全显示的) visible view.  //最后一个完全显示的View的位置
                int lPos = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                //为了确定是哪一个View才是滚动显示广告的View,他总是在可视区域里面才能被看到
                for (int i = fPos; i <= lPos; i++) {
                    View view = mLinearLayoutManager.findViewByPosition(i);
                    AdImageView adImageView = (AdImageView) view.findViewById(R.id.id_iv_ad);
                   //找到显示广告的View  里面的adImageView是显示的 ,然后再去设置数值
                    if (adImageView.getVisibility() == View.VISIBLE) {
                        //getHeight : Return the height of the parent RecyclerView RecyclerView的高度
                        //getTop:Top position of this view relative to its parent.
                        //Log.d(TAG, "onScrolled: "+mLinearLayoutManager.getHeight());
                        adImageView.setDy(mLinearLayoutManager.getHeight() - view.getTop());
                    }
                }
            }
        });
    }

}
