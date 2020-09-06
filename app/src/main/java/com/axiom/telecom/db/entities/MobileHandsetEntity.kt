package com.axiom.telecom.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.axiom.telecom.db.entities.MobileHandsetEntity.Companion.TABLE_NAME
import com.axiom.telecom.utils.Constants
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = TABLE_NAME)
class MobileHandsetEntity {
    companion object {
        const val TABLE_NAME = "mobiles"
        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_BRAND = "brand"
        const val COLUMN_NAME_PHONE = "phone"
        const val COLUMN_NAME_PICTURE = "picture"
        const val COLUMN_NAME_ANNOUNCE_DATE = "announceDate"
        const val COLUMN_NAME_SIM = "sim"
        const val COLUMN_NAME_RESOLUTION = "resolution"
        const val COLUMN_NAME_AUDIO_JACK = "audioJack"
        const val COLUMN_NAME_GPS = "gps"
        const val COLUMN_NAME_BATTERY = "battery"
        const val COLUMN_NAME_PRICE_EUR = "priceEur"
    }

    @ColumnInfo(name = COLUMN_NAME_ID)
    @PrimaryKey
    @SerializedName("id")
    var id : Int? = null

    @ColumnInfo(name = COLUMN_NAME_BRAND)
    @SerializedName("brand")
    var brand : String? = null

    @ColumnInfo(name = COLUMN_NAME_PHONE)
    @SerializedName("phone")
    var phone : String? = null

    @ColumnInfo(name = COLUMN_NAME_PICTURE)
    @SerializedName("picture")
    var picture : String? = null

    @ColumnInfo(name = COLUMN_NAME_ANNOUNCE_DATE)
    @SerializedName("announceDate")
    var announceDate : String? = null
    /*get() {
        if (announceDate is Int) {
            return Constants.EMPTY + announceDate
        }
        return announceDate
    }*/

    @ColumnInfo(name = COLUMN_NAME_SIM)
    @SerializedName("sim")
    var sim : String? = null

    @ColumnInfo(name = COLUMN_NAME_RESOLUTION)
    @SerializedName("resolution")
    var resolution : String? = null

    @ColumnInfo(name = COLUMN_NAME_AUDIO_JACK)
    @SerializedName("audioJack")
    var audioJack : String? = null

    @ColumnInfo(name = COLUMN_NAME_GPS)
    @SerializedName("gps")
    var gps : String? = null

    @ColumnInfo(name = COLUMN_NAME_BATTERY)
    @SerializedName("battery")
    var battery : String? = null

    @ColumnInfo(name = COLUMN_NAME_PRICE_EUR)
    @SerializedName("priceEur")
    var priceEur : Int? = null

    @Ignore
    @Expose(serialize = false, deserialize = false)
    var isSelected = false

}