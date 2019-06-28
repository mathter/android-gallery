package biz.ostw.android.gallery;

import android.content.ComponentName;
import android.os.IBinder;

public class ServiceConnection<S> implements android.content.ServiceConnection {

    private S service;

    public S service() {
        return this.service;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        this.service = ((Binder<S>) service).getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    public interface Binder<S> extends IBinder {
        public S getService();
    }
}
