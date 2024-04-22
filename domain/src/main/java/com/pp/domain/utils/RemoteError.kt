package com.pp.domain.utils

import com.pp.domain.model.errormodel.CommonErrorResponseInfo
import com.pp.domain.model.errormodel.ErrorResponseInfo

interface RemoteError {
    fun onError(msg: String)
    fun onError(errorType: ErrorType)
    fun onApiError(errorInfo: ErrorResponseInfo)
    fun onApiCommonError(errorInfo: CommonErrorResponseInfo)
}