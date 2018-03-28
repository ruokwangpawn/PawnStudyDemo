package com.pawn.updateservice;

/*********************************************
 * @文件名称: UpdateDownloadListener
 * @文件作者: Pawn
 * @文件描述:
 * @创建时间: 2018/3/27 10
 * @修改历史:
 *********************************************/

public interface UpdateDownloadListener {

    void onStarted();

    void onFinished(int completeSize);

    void onFailure();

    void onProgressChanged(int progress);

}
