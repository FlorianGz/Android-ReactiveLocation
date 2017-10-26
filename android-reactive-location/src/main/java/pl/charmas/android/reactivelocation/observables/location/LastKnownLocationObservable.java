package pl.charmas.android.reactivelocation.observables.location;

import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import pl.charmas.android.reactivelocation.observables.BaseLocationObservable;
import pl.charmas.android.reactivelocation.observables.ObservableContext;
import rx.Observable;
import rx.Observer;

public class LastKnownLocationObservable extends BaseLocationObservable<Location> {

    public static Observable<Location> createObservable(ObservableContext ctx) {
        return Observable.create(new LastKnownLocationObservable(ctx));
    }

    private LastKnownLocationObservable(ObservableContext ctx) {
        super(ctx);
    }

    @Override
    protected void onGoogleApiClientReady(GoogleApiClient apiClient, Observer<? super Location> observer) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(apiClient);
        observer.onNext(location);
        observer.onCompleted();
    }
}
