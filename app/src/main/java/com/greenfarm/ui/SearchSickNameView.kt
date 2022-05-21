package com.greenfarm.ui

import com.greenfarm.data.entities.SearchSickNameResult


interface SearchSickNameView {
    fun onSearchSickNameLoading()
    fun onSearchSickNameSuccess(searchSickNameResult: SearchSickNameResult)
    fun onSearchSickNameFailure(code: Int, message: String)
}