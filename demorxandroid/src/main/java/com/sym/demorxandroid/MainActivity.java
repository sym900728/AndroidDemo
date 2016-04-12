package com.sym.demorxandroid;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.schedulers.HandlerScheduler;
import rx.functions.Func0;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "RxAndroidSamples";

    private Handler backgroundHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BackgroundThread backgroundThread = new BackgroundThread();
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());

        findViewById(R.id.button_run_scheduler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRunSchedulerExampleButtonClicked();
            }
        });

    }

    void onRunSchedulerExampleButtonClicked() {
        sampleObservable()
                // Run on a background thread
                .subscribeOn(HandlerScheduler.from(backgroundHandler))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError()", e);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, "onNext(" + s + ")");
                    }
                });

    }

    static Observable<String> sampleObservable() {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                /*try {
                    // Do some long running operation
                    Thread.sleep(TimeUnit.SECONDS.toMillis(5));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                return Observable.just("one", "two", "three", "four", "five");
            }
        });
    }

    static class BackgroundThread extends HandlerThread {
        BackgroundThread() {
            super("SchedulerSample-BackgroundThread", android.os.Process.THREAD_PRIORITY_BACKGROUND);
        }
    }
}
