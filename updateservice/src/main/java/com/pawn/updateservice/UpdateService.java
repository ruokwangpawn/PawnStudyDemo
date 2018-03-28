package com.pawn.updateservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/*********************************************
 * @文件名称: UpdateService
 * @文件作者: Pawn
 * @文件描述:
 * @创建时间: 2018/3/27 10
 * @修改历史:
 *********************************************/

public class UpdateService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
