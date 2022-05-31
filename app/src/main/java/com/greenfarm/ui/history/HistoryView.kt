package com.greenfarm.ui.history

import com.greenfarm.data.remote.history.HistoryResult

interface HistoryView {
    fun onHistoryLoading()
    fun onHistorySuccess(res: ArrayList<HistoryResult>)
    fun onHistoryFailure(code: Int, message: String)
}