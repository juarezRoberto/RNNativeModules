package com.upax.zemytalents.domain.repositories

import com.upax.zemytalents.domain.models.ZEMTCaption

internal interface ZEMTCaptionsFinder {

    fun findById(id: Int): ZEMTCaption?

}