package pl.charmas.android.reactivelocation.observables.location;

import android.app.PendingIntent;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;

import pl.charmas.android.reactivelocation.observables.BaseLocationObservable;
import pl.charmas.android.reactivelocation.observables.ObservableContext;
import pl.charmas.android.reactivelocation.observables.StatusException;
import rx.Observable;
import rx.Observer;

public class RemoveLocationIntentUpdatesObservable extends BaseLocationObservable<Status> {
    private final PendingIntent intent;

    public static Observable<Status> createObservable(ObservableContext ctx, PendingIntent intent) {
        return Observable.create(new RemoveLocationIntentUpdatesObservable(ctx, intent));
    }

    private RemoveLocationIntentUpdatesObservable(ObservableContext ctx, PendingIntent intent) {
        super(ctx);
        this.intent = intent;
    }

    @Override
    protected void onGoogleApiClientReady(GoogleApiClient apiClient, final Observer<? super Status> observer) {
        LocationServices.FusedLocationApi.removeLocationUpdates(apiClient, intent)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            observer.onNext(status);
                            observer.onCompleted();
                        } else {
                            observer.onError(new StatusException(status));
                        }
                    }
                });
    }
}
