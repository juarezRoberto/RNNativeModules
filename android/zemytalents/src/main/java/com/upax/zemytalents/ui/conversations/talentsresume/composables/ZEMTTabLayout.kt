package com.upax.zemytalents.ui.conversations.talentsresume.composables

import android.content.res.ColorStateList
import android.widget.LinearLayout
import androidx.appcompat.view.ContextThemeWrapper
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.material.tabs.TabLayout
import com.upax.zccommon.extensions.EMPTY
import com.upax.zcdesignsystem.expose.ZCDSColorUtils

@Composable
internal fun ZEMTTabLayout(
    tabList: List<String>,
    modifier: Modifier = Modifier,
    onTabSelected: (String) -> Unit = {},
    selectedTab: String = String.EMPTY
) {
    AndroidView(
        factory = { context ->
            val materialContext =
                ContextThemeWrapper(
                    context,
                    com.upax.zcdesignsystem.R.style.Theme_ZCDSAppTheme
                )
            TabLayout(materialContext).apply {
                val udnColor = ZCDSColorUtils.getPrimaryColor()
                this.tabTextColors = ColorStateList.valueOf(udnColor)
                this.setSelectedTabIndicatorColor(udnColor)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                tabList.forEach { tab ->
                    addTab(newTab().apply {
                        text = tab
                        if (tab == selectedTab) select()
                    })
                }
            }
        }, modifier = modifier.fillMaxWidth(),
        update = { tabLayout ->
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    onTabSelected(tab.text.toString())
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }
    )
}

@Preview
@Composable
private fun ZEMTTabLayoutPreview() {
    ZEMTTabLayout(tabList = listOf("Dominantes", "No dominantes"))
}