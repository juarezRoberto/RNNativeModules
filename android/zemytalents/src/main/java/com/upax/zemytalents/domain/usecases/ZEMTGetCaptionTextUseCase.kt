package com.upax.zemytalents.domain.usecases

import com.upax.zemytalents.domain.models.ZEMTCaptionCatalog
import com.upax.zemytalents.domain.repositories.ZEMTCaptionsFinder

internal class ZEMTGetCaptionTextUseCase(private val captionsFinder: ZEMTCaptionsFinder) {

    operator fun invoke(item: ZEMTCaptionCatalog): String {
        return captionsFinder.findById(item.sectionId)?.value.orEmpty()
    }
}