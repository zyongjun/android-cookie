package com.joez.view.holder;

import android.view.View;
import android.widget.TextView;
import com.joez.base.BindRecyclerHolder;
import com.joez.presenter.RxJavaPresenter;
import com.joez.ui.R;
import com.windhike.recyclerutils.HolderLayout;
import butterknife.Bind;

/**
 * Created by Administrator on 2016/10/11 0011.
 */
@HolderLayout(R.layout.holder_app)
public class AppHolder extends BindRecyclerHolder<RxJavaPresenter>{
    @Bind(R.id.tv_app)
    TextView tvApp;
    public AppHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindView(int position, RxJavaPresenter presenter) {
        super.bindView(position, presenter);
        tvApp.setText(presenter.getAppList().get(position).getName());
    }
}
