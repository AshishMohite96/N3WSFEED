package com.ak.newsfeed.ui.home

import android.view.View
import com.ak.newsfeed.base.BaseViewHolder
import com.ak.newsfeed.data.remote.Article
import com.ak.newsfeed.databinding.ItemNewsImageBinding
import com.ak.newsfeed.databinding.ItemNewsTextBinding

class ImageViewHolder constructor(binding: ItemNewsImageBinding): BaseViewHolder(binding.root) {

    private val bindingImage: ItemNewsImageBinding

    init {
        bindingImage = binding
    }

    override fun bindData(item: Article) {
        TODO("Not yet implemented")
    }

    override fun getItemNewsTextBinding(): ItemNewsTextBinding {
        TODO("Not yet implemented")
    }

    override fun getItemNewsImageBinding(): ItemNewsImageBinding {
        TODO("Not yet implemented")
    }
}