package com.upax.zemytalents.domain.repositories

import com.upax.zemytalents.domain.models.ZEMTCaption

internal interface ZEMTCaptionsStorage {

    fun save(captions: List<ZEMTCaption>)

}