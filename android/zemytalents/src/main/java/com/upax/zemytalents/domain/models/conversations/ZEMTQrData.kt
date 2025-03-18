package com.upax.zemytalents.domain.models.conversations

import android.os.Parcelable
import com.upax.zemytalents.common.ZEMTQrFunctions.parseDateForService
import com.upax.zemytalents.common.ZEMTQrFunctions.toLocalDateTime
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
internal data class ZEMTQrData(
    val collaboratorId: String,
    val employeeNumber: String,
    val createdAt: String = LocalDateTime.now().parseDateForService(),
    val companyId: String
) : Parcelable {

    val isDateValid: Boolean get() = createdAt.toLocalDateTime().dayOfMonth == LocalDateTime.now().dayOfMonth

    fun getDataToGenerateQr(): String {
        return "($collaboratorId$DELIMITER$employeeNumber$DELIMITER$createdAt$DELIMITER$companyId)"
    }

    companion object {
        private const val DELIMITER = "||"
        private const val INDEX_COLLABORATOR_ID = 0
        private const val INDEX_EMPLOYEE_NUMBER = 1
        private const val INDEX_CREATED_AT = 2
        private const val INDEX_COMPANY_ID = 3

        fun parseDataFromQr(data: String): ZEMTQrData? = try {
            val trimmedInput = data.trim('(', ')')
            val parts = trimmedInput.split(DELIMITER)

            val collaboratorId = parts[INDEX_COLLABORATOR_ID]
            val employeeNumber = parts[INDEX_EMPLOYEE_NUMBER]
            val createdAt = parts[INDEX_CREATED_AT]
            val companyId = parts[INDEX_COMPANY_ID]
            ZEMTQrData(
                collaboratorId = collaboratorId,
                employeeNumber = employeeNumber,
                createdAt = createdAt,
                companyId = companyId
            )
        } catch (e: Exception) {
            null
        }
    }

}
