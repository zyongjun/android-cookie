package com.joez.view;

import com.joez.base.IBaseView;

/**
 * Created by Administrator on 2016/8/12 0012.
 */
public interface IRxJavaView extends IBaseView{
    public void updateResultDisplay(String message);
    void notifyDataChanged();
}
