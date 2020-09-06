package com.axiom.telecom.model

class FilterTypeBean {
    companion object {
        const val TYPE_BRAND = 1;
        const val TYPE_PHONE = 2;
        const val TYPE_PRICE = 3;
        const val TYPE_SIM = 4;
        const val TYPE_GPS = 5;
        const val TYPE_AUDIO_JACK = 6;

        const val TYPE_NAME_BRAND = "Brand";
        const val TYPE_NAME_PHONE = "Phone";
        const val TYPE_NAME_PRICE = "Price";
        const val TYPE_NAME_SIM = "Sim";
        const val TYPE_NAME_GPS = "GPS";
        const val TYPE_NAME_AUDIO_JACK = "Audio Jack";
    }

    var type: Int? = null

    var typeName: String? = null

    var entityList = ArrayList<FilterTypeContentBean>()

    var isSelected = false

}