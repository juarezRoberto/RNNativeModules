package com.upax.zemytalents.data.local.icons

import androidx.annotation.DrawableRes
import com.upax.zemytalents.R

enum class ZEMTIconParser(val iconName: String, @DrawableRes val iconId: Int) {
    FIJAR_OBJECTIVOS("MT-FIJAR-OBJECTIVOS", R.drawable.zemt_ic_conversation_goals),
    CONEXION_RAPIDA("MT-CONEXION-RAPIDA", R.drawable.zemt_ic_conversation_connection),
    CHEQUEO("MT-CHEQUEO", R.drawable.zemt_ic_conversation_check_up),
    DESEMPENO("MT-PERFORMANCE", R.drawable.zemt_ic_conversation_performance),
    DESARROLLO("MT-DESARROLLO", R.drawable.zemt_ic_conversation_development),
    NONE("", -1);

    companion object {

        fun parseIcon(icon: Int, index: Int = 0): Int {
            return if (icon == -1) when (index) {
                0 -> ZEMTIconParser.FIJAR_OBJECTIVOS.iconId
                1 -> ZEMTIconParser.CONEXION_RAPIDA.iconId
                2 -> ZEMTIconParser.CHEQUEO.iconId
                3 -> ZEMTIconParser.DESEMPENO.iconId
                else -> ZEMTIconParser.DESARROLLO.iconId
            } else icon
        }
    }
}