package com.upax.zemytalents.ui.modules.discover.home.utils

import com.upax.zemytalents.R
import com.upax.zemytalents.domain.models.ZEMTTalentEnum
import com.upax.zemytalents.domain.models.ZEMTTalent
import com.upax.zemytalents.ui.shared.composables.talentsprofile.NO_TALENT

internal fun ZEMTTalent?.getIconFromId(): Int = getIconFromId(this?.id ?: NO_TALENT)

internal fun getIconFromId(talentId: Int): Int = when (talentId) {
    ZEMTTalentEnum.EXAMINADOR.talentId -> R.drawable.zemt_ic_talent_examinador
    ZEMTTalentEnum.CONCILIADOR.talentId -> R.drawable.zemt_ic_talent_conciliador
    ZEMTTalentEnum.AUTOSEGURIDAD.talentId -> R.drawable.zemt_ic_talent_autoseguridad
    ZEMTTalentEnum.ENCANTO.talentId -> R.drawable.zemt_ic_talent_encanto
    ZEMTTalentEnum.COMPETIDOR.talentId -> R.drawable.zemt_ic_talent_competidor
    ZEMTTalentEnum.COMUNICADOR.talentId -> R.drawable.zemt_ic_talent_comunicador
    ZEMTTalentEnum.SISTEMA.talentId -> R.drawable.zemt_ic_talent_sistematico
    ZEMTTalentEnum.ARTICULADOR.talentId -> R.drawable.zemt_ic_talent_articulador
    ZEMTTalentEnum.CONVICCION.talentId -> R.drawable.zemt_ic_talent_conviccion
    ZEMTTalentEnum.MENTOR.talentId -> R.drawable.zemt_ic_talent_mentor
    ZEMTTalentEnum.PERFECCIONISTA.talentId -> R.drawable.zemt_ic_talent_perfeccionista
    ZEMTTalentEnum.COMPRENSION.talentId -> R.drawable.zemt_ic_talent_comprension
    ZEMTTalentEnum.CONQUISTADOR.talentId -> R.drawable.zemt_ic_talent_conquistador
    ZEMTTalentEnum.CONCENTRACION.talentId -> R.drawable.zemt_ic_talent_concentracion
    ZEMTTalentEnum.ESTRATEGIA.talentId -> R.drawable.zemt_ic_talent_estrategia
    ZEMTTalentEnum.ACTUALIZADOR.talentId -> R.drawable.zemt_ic_talent_actualizador
    ZEMTTalentEnum.CALIDAD.talentId -> R.drawable.zemt_ic_talent_calidad
    ZEMTTalentEnum.RECONSTRUCTOR.talentId -> R.drawable.zemt_ic_talent_reconstructor
    ZEMTTalentEnum.GRANDEZA.talentId -> R.drawable.zemt_ic_talent_grandeza
    ZEMTTalentEnum.INCLUYENTE.talentId -> R.drawable.zemt_ic_talent_incluyente
    ZEMTTalentEnum.PERSONALIZADOR.talentId -> R.drawable.zemt_ic_talent_personalizador
    ZEMTTalentEnum.CONDUCTOR.talentId -> R.drawable.zemt_ic_talent_conductor
    ZEMTTalentEnum.VISIONARIO.talentId -> R.drawable.zemt_ic_talent_visionario
    ZEMTTalentEnum.ACTIVADOR.talentId -> R.drawable.zemt_ic_talent_activador
    ZEMTTalentEnum.CONFIDENTE.talentId -> R.drawable.zemt_ic_talent_confidente
    ZEMTTalentEnum.CUMPLIMIENTO.talentId -> R.drawable.zemt_ic_talent_cumplimiento
    ZEMTTalentEnum.INGENIOSO.talentId -> R.drawable.zemt_ic_talent_ingenioso
    ZEMTTalentEnum.INDAGADOR.talentId -> R.drawable.zemt_ic_talent_indagador
    ZEMTTalentEnum.CONCEPTUAL.talentId -> R.drawable.zemt_ic_talent_conceptual
    ZEMTTalentEnum.OPERADOR.talentId -> R.drawable.zemt_ic_talent_operador
    ZEMTTalentEnum.ENERGIZADOR.talentId -> R.drawable.zemt_ic_talent_energizador
    ZEMTTalentEnum.PRUDENTIAL.talentId -> R.drawable.zemt_ic_talent_prudencial
    ZEMTTalentEnum.EQUITATIVO.talentId -> R.drawable.zemt_ic_talent_equativo
    ZEMTTalentEnum.MOMENTO.talentId -> R.drawable.zemt_ic_talent_momento
    ZEMTTalentEnum.DETERMINACION.talentId -> R.drawable.zemt_ic_talent_determinacion
    else -> R.drawable.zemt_ic_talent_none
}