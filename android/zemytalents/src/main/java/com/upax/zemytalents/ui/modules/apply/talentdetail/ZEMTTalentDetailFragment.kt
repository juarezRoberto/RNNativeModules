package com.upax.zemytalents.ui.modules.apply.talentdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.upax.zemytalents.domain.models.ZEMTAttachmentType
import com.upax.zemytalents.domain.models.ZEMTTalent
import com.upax.zemytalents.ui.modules.discover.home.utils.getIconFromId

internal class ZEMTTalentDetailFragment : BottomSheetDialogFragment() {

    override fun getTheme() = com.upax.zcdesignsystem.R.style.Theme_ZCDSBaseBottomSheetModal

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val talent = requireArguments().getParcelable<ZEMTTalent>(ARG_TALENT)

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                ZEMTTalentDetailScreen(
                    title = talent?.name.orEmpty(),
                    titleDrawableIcon = talent.getIconFromId(),
                    description = talent?.description.orEmpty(),
                    lottieUrl = talent?.attachment?.find { it.type == ZEMTAttachmentType.LOTTIE }?.url.orEmpty(),
                    onClose = {
                        dismiss()
                    }
                )
            }
        }
    }

    companion object {
        val TAG = ZEMTTalentDetailFragment::class.simpleName
        const val ARG_TALENT = "ARG_TALENT"

        fun newInstance(talent: ZEMTTalent): ZEMTTalentDetailFragment {
            return ZEMTTalentDetailFragment().apply {
                arguments = bundleOf(ARG_TALENT to talent)
            }
        }
    }
}