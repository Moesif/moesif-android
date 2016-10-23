package com.moesif.android.okhttp2.models;

import com.moesif.android.EventConfig;
import com.moesif.android.api.models.EventModel;
import com.moesif.android.api.models.EventRequestModel;
import com.moesif.android.api.models.EventResponseModel;
import com.moesif.android.common.MoesifLog;
import com.moesif.android.persistence.UserSessionStore;

/*
 * MoesifAndroid
 *
 *
 */
public class OkHttp2EventMapper extends EventModel {

    private static final String LOGTAG = OkHttp2EventMapper.class.getCanonicalName();

    public static EventModel createOkHttp2Event(EventRequestModel loggedRequest,
                                                EventResponseModel loggedResponse) throws java.io.IOException {

        EventModel eventModel = new EventModel();
        eventModel.setRequest(loggedRequest);
        eventModel.setResponse(loggedResponse);
        eventModel.setTags(EventConfig.getTagsProvider().getValue(loggedRequest, loggedResponse));
        try {
            eventModel.setUserId(UserSessionStore.getInstance().getUserIdentity());
            eventModel.setSessionToken(UserSessionStore.getInstance().getSessionToken());
        }
        catch (Exception e) {
            MoesifLog.getLogger().e(LOGTAG, "Error creating Event Model", e);
        }
        return eventModel;
    }
}
