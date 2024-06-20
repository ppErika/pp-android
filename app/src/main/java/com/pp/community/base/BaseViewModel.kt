package com.pp.community.base

import android.util.Log
import androidx.lifecycle.ViewModel
import com.pp.domain.model.errormodel.CommonErrorResponseInfo
import com.pp.domain.model.errormodel.ErrorResponseInfo
import com.pp.domain.utils.ErrorType
import com.pp.domain.utils.RemoteError

abstract class BaseViewModel : ViewModel(), RemoteError {

    override fun onError(errorType: ErrorType) {
        Log.d("EJ_LOG","onError $errorType")
    }

    override fun onError(msg: String) {
        Log.d("EJ_LOG","onError_msg $msg")

    }

    override fun onApiError(errorInfo: ErrorResponseInfo) {
        Log.d("EJ_LOG","onApiError $errorInfo")

    }

    override fun onApiCommonError(errorInfo: CommonErrorResponseInfo) {
        Log.d("EJ_LOG","onApiCommonError $errorInfo")
    }
}