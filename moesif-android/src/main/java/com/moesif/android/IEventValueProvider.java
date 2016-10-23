package com.moesif.android;

import com.moesif.android.api.models.EventRequestModel;
import com.moesif.android.api.models.EventResponseModel;

/*
 * MoesifAndroid
 *
 *
 */
public interface IEventValueProvider<T> {

    T getValue(EventRequestModel request, EventResponseModel response);
}
