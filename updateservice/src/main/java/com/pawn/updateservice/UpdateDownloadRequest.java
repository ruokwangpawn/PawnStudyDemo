package com.pawn.updateservice;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

/*********************************************
 * @文件名称: UpdateDownloadRequest
 * @文件作者: Pawn
 * @文件描述:
 * @创建时间: 2018/3/27 10
 * @修改历史:
 *********************************************/

public class UpdateDownloadRequest implements Runnable {

    private String downloadUrl;
    private String localFilePath;
    private UpdateDownloadListener downloadListener;
    private boolean isDownloading = false;

    private DownloadResponseHandler downloadHandler;
    private long currentLength;

    public UpdateDownloadRequest(String downloadUrl, String localFilePath, UpdateDownloadListener downloadListener) {
        this.downloadUrl = downloadUrl;
        this.localFilePath = localFilePath;
        this.downloadListener = downloadListener;
        // 构造方法完成后将标志位置为true
        this.isDownloading = true;

        downloadHandler = new DownloadResponseHandler();
    }

    @Override
    public void run() {
        try {
            makeRequest();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void makeRequest() throws IOException, InterruptedException {
        if (!Thread.currentThread().isInterrupted()) {
            URL url = new URL(downloadUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.connect();   // 会阻塞我们当前的线程

            currentLength = connection.getContentLength();
            if (!Thread.currentThread().isInterrupted()) {
                downloadHandler.sendResponseMessage(connection.getInputStream());
            }
        }
    }

    private String getTwoPointFloatStr(float value) {
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(value);
    }

    public enum FailureCode {
        UnkownHost,
        Socket,
        SocketTimeout,
        ConnectTimeout,
        IO,
        HttpResponse,
        JSON,
        Interrupted,
    }

    public class DownloadResponseHandler {

        private static final int SUCCESS_MESSAGE = 0;
        private static final int FAILURE_MESSAGE = 1;
        private static final int START_MESSAGE = 2;
        private static final int FINISH_MESSAGE = 3;
        private static final int NETWORK_OFF = 4;
        private static final int PROGRESS_CHANGED = 5;

        private int mCompleteSize = 0;
        private int progress = 0;

        private Handler mHandler;

        public DownloadResponseHandler() {
            mHandler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    handleSelfMessage(msg);
                }
            };
        }

        private void sendFinishMessage() {
            sendMessage(obtainMessage(FINISH_MESSAGE, null));
        }

        private void sendProgessChangedMessage(int progress) {
            sendMessage(obtainMessage(PROGRESS_CHANGED, new Object[]{progress}));
        }

        private void sendFailureMessage(FailureCode failureCode) {
            sendMessage(obtainMessage(FAILURE_MESSAGE, new Object[]{failureCode}));
        }

        private void sendMessage(Message msg) {
            if (mHandler != null) {
                mHandler.sendMessage(msg);
            } else {
                handleSelfMessage(msg);
            }
        }

        private Message obtainMessage(int responseMessage, Object response) {
            Message msg = null;
            if (mHandler != null) {
                msg = mHandler.obtainMessage(responseMessage, response);
            } else {
                msg = Message.obtain();
                msg.what = responseMessage;
                msg.obj = response;
            }
            return msg;
        }

        private void handleSelfMessage(Message msg) {
            Object[] response = null;
            switch (msg.what) {
                case FAILURE_MESSAGE:
                    response = (Object[]) msg.obj;
                    handlerFailureMessage((FailureCode) response[0]);
                    break;
                case PROGRESS_CHANGED:
                    response = (Object[]) msg.obj;
                    handlerProgressChangedMessage((Integer) response[0]);
                    break;
                case FINISH_MESSAGE:
                    onFinish();
                    break;
            }
        }

        private void handlerProgressChangedMessage(int progress) {
            downloadListener.onProgressChanged(progress);
        }

        private void handlerFailureMessage(FailureCode failureCode) {
            onFailure(failureCode);
        }

        private void onFinish() {
            downloadListener.onFinished(mCompleteSize);
        }

        private void onFailure(FailureCode failureCode) {
            downloadListener.onFailure();
        }

        /**
         * 文件下载方法，会发送各种类型的事件
         *
         * @param is
         */
        void sendResponseMessage(InputStream is) {
            RandomAccessFile randomAccessFile = null;
            mCompleteSize = 0;
            try {
                byte[] buffer = new byte[1024];
                int length = -1;
                int limit = 0;
                randomAccessFile = new RandomAccessFile(localFilePath, "rwd");
                while ((length = is.read(buffer)) != -1) {
                    if (isDownloading) {
                        randomAccessFile.write(buffer, 0, length);
                        mCompleteSize += length;
                        if (mCompleteSize < currentLength) {
                            progress = (int) Float.parseFloat(getTwoPointFloatStr(mCompleteSize / currentLength));
                            if (limit / 30 == 0 || progress <= 100) {
                                sendProgessChangedMessage(progress);
                            }
                            limit++;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                sendFailureMessage(FailureCode.IO);

            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (randomAccessFile != null) {
                        randomAccessFile.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}