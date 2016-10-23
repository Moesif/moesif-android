package com.moesif.android.eventfieldproviders;

import com.moesif.android.IEventValueProvider;
import com.moesif.android.api.models.EventRequestModel;
import com.moesif.android.api.models.EventResponseModel;

/*
 * MoesifAndroid
 *
 *
 */
public class DefaultNullValueProvider implements IEventValueProvider<String> {
    public String getValue(EventRequestModel request, EventResponseModel response) {
        return null;
    }
}
