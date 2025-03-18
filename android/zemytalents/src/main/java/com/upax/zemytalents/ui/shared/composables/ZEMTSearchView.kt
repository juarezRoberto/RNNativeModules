package com.upax.zemytalents.ui.shared.composables

import android.widget.ImageView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.upax.zcdesignsystem.widget.searchview.ZCDSSearchView
import com.upax.zemytalents.R
import com.upax.zcdesignsystem.R as RDS

@Composable
internal fun ZEMTSearchView(
    isEnabled: Boolean = true,
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit = { }
) {
    AndroidView(
        modifier = modifier,
        factory = { context -> ZCDSSearchView(context) },
        update = { view ->
            view.isEnabled = isEnabled
            view.requestSearchFocus()
            view.setHint(ContextCompat.getString(view.context, R.string.zemt_search))
            val startButton = view.findViewById<ImageView>(RDS.id.zcds_back_button)
            view.setOnFocusChangeListener { _, hasFocus ->
                startButton.isVisible = !hasFocus
            }
            view.setTextChangedListener(onTextChange)
            view.enableAutoQueryClear(false)
            view.setInputType(ZCDSSearchView.ZCDSInputType.TEXT)
        }
    )
}