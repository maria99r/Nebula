package com.alanturing.nebula.model.activities

import java.util.Optional

data class  ParticipationResponse (
    val id: Int,
    val userId: Int,
    val activityId: Int,
    val activity: Optional<ActivityResponse>
)