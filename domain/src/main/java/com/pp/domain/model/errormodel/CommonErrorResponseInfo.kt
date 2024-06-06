package com.pp.domain.model.errormodel

data class CommonErrorResponseInfo(
    var type: String?,
    var title: String?,
    var status: String?,
    var detail: String?,
    var instance: String?
)