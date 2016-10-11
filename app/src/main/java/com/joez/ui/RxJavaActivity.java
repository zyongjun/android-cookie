package com.joez.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.joez.presenter.RxJavaPresenter;
import com.joez.view.IRxJavaView;
import com.windhike.recyclerutils.RecyclerAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RxJavaActivity extends BaseCookieActicity implements IRxJavaView {
    @Bind(R.id.tv_result)
    TextView tvResult;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_observer)
    TextView tvObserver;
    @Bind(R.id.tv_copy)
    TextView tvCopy;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.rv_rx)
    RecyclerView rvRx;
    private RxJavaPresenter mPresenter;

    @Override
    protected void onCookieCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_rx_java);
        mPresenter = new RxJavaPresenter();
        mPresenter.attach(this);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rvRx.setLayoutManager(new LinearLayoutManager(this));
        rvRx.setHasFixedSize(true);
        rvRx.setAdapter(new RecyclerAdapter(mPresenter));
    }

    @OnClick(R.id.tv_copy)
    public void onClick(View view) {
        if (view.getId() == R.id.tv_copy) {
            mPresenter.testSimpleRxCopy();
        }
    }
    @Override
    protected void onDestroy() {
        mPresenter.detach();
        super.onDestroy();
    }

    @Override
    public void updateResultDisplay(String message) {
        tvResult.setText(message);
    }

    @Override
    public void notifyDataChanged() {
        rvRx.getAdapter().notifyDataSetChanged();
    }
}
