package com.moesif.android;

import com.moesif.android.eventfieldproviders.DefaultNullValueProvider;

/*
 * MoesifAndroid
 *
 *
 */
public class EventConfig {

    //private backing field for event tags provider
    private static IEventValueProvider<String> eventTagsProvider;

    /**
     * getValue value for eventTagsProvider
     * @return  eventTagsProvider Your Tags
     */
    public static IEventValueProvider<String> getTagsProvider() {
        if(eventTagsProvider == null)
            return new DefaultNullValueProvider();

        return eventTagsProvider;
    }

    /**
     * Set value for apiVersion
     * @param eventTagsProvider Your tags provider
     */
    public static void setTagsProvider(IEventValueProvider<String> eventTagsProvider) {
        EventConfig.eventTagsProvider = eventTagsProvider;
    }
}
