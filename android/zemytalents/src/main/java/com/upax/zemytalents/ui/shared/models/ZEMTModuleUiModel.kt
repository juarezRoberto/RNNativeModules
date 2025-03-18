package com.upax.zemytalents.ui.shared.models

import android.os.Parcelable
import com.upax.zemytalents.R
import com.upax.zemytalents.domain.models.modules.ZEMTModuleStage
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class ZEMTModuleUiModel(
    val name: String,
    val order: Int,
    val moduleId: Int,
    val surveyId: String,
    val moduleDesc: String,
    val isComplete: Boolean,
    val progress: Float,
    val multimedia: List<ZEMTModuleMultimediaUiModel>,
    val stage: ZEMTModuleStage,
) : Parcelable {
    
    fun getIcon(): Int {
        return when (stage) {
            ZEMTModuleStage.DISCOVER -> R.raw.zemt_telescope
            ZEMTModuleStage.CONFIRM -> R.raw.zemt_rocket
            ZEMTModuleStage.APPLY -> R.raw.zemt_degree
            else -> R.raw.zemt_degree
        }
    }
}
