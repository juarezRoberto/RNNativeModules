package com.upax.zemytalents.domain.models.conversations

import com.upax.zemytalents.common.ZEMTQrFunctions.parseDateForService
import com.upax.zemytalents.utils.ZEMTRandomValuesUtil
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class ZEMTQrDataTest {

    @Test
    fun `getDataToGenerateQr should return data in correct order and correct format`() {
        val collaboratorId = ZEMTRandomValuesUtil.getString()
        val employeeNumber = ZEMTRandomValuesUtil.getString()
        val createdAt = LocalDateTime.now().parseDateForService()
        val companyId = ZEMTRandomValuesUtil.getString()
        val qrData = ZEMTQrData(
            collaboratorId = collaboratorId,
            employeeNumber = employeeNumber,
            createdAt = createdAt,
            companyId = companyId
        )
        val dataQrExpected = "($collaboratorId||$employeeNumber||$createdAt||$companyId)"

        assertEquals(dataQrExpected, qrData.getDataToGenerateQr())
    }

    @Test
    fun `parseDataFromQr should be able to parse string into an instance of ZEMTQrData`() {
        val collaboratorId = ZEMTRandomValuesUtil.getString()
        val employeeNumber = ZEMTRandomValuesUtil.getString()
        val createdAt = LocalDateTime.now().parseDateForService()
        val companyId = ZEMTRandomValuesUtil.getString()

        val qrData = ZEMTQrData.parseDataFromQr(
            "($collaboratorId||$employeeNumber||$createdAt||$companyId)"
        )

        val qrDataExpected = ZEMTQrData(
            collaboratorId = collaboratorId,
            employeeNumber = employeeNumber,
            createdAt = createdAt,
            companyId = companyId
        )
        assertEquals(qrDataExpected, qrData)
    }

}