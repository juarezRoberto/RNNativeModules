package com.upax.zemytalents.data.remote.mapper

import com.upax.zemytalents.data.remote.response.ZEMTAttachmentResponseMother
import com.upax.zemytalents.domain.models.modules.discover.ZEMTBreakAttachmentDiscover
import com.upax.zemytalents.domain.models.modules.discover.valueobjects.ZEMTBreakAttachmentDiscoverId
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ZEMTAttachmentDiscoverResponseToModelMapperTest {

    private val mapper = ZEMTAttachmentDiscoverResponseToModelMapper()

    @Test
    fun `mapper should return empty list when breaks attachments response is null`() {
        val attachments = mapper.invoke(null)

        assertTrue(attachments.isEmpty())
    }

    @Test
    fun `mapper should map breaks attachment correctly`() {
        val attachmentResponse1 = ZEMTAttachmentResponseMother.random()
        val attachmentResponse2 = ZEMTAttachmentResponseMother.random()
        val attachmentsResponse = listOf(attachmentResponse1, attachmentResponse2)

        val breakAttachment = mapper.invoke(attachmentsResponse)

        val breakAttachmentExpected = listOf(
            ZEMTBreakAttachmentDiscover(
                id = ZEMTBreakAttachmentDiscoverId(attachmentResponse1.id!!),
                url = attachmentResponse1.url!!,
                name = attachmentResponse1.name!!,
                description = attachmentResponse1.description!!,
                type = attachmentResponse1.type!!,
                order = attachmentResponse1.order!!
            ),
            ZEMTBreakAttachmentDiscover(
                id = ZEMTBreakAttachmentDiscoverId(attachmentResponse2.id!!),
                url = attachmentResponse2.url!!,
                name = attachmentResponse2.name!!,
                description = attachmentResponse2.description!!,
                type = attachmentResponse2.type!!,
                order = attachmentResponse2.order!!
            )
        )
        assertEquals(breakAttachmentExpected, breakAttachment)
    }

}